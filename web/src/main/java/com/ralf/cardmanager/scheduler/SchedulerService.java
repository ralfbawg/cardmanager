package com.ralf.cardmanager.scheduler;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.cardtransaction.entity.TblCardTransaction;
import com.ralf.cardmanager.cardtransaction.service.TblCardTransactionService;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.*;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCardTransactionsByCompanyId;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCardTransactionsByCompanyIdRsp;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCompanyTransactionsTotalCount;
import com.ralf.cardmanager.system.CommonService;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ralf.cardmanager.system.CommonService.SITE_SWITCH_CACHE;
import static com.ralf.cardmanager.system.CommonService.SITE_SWITCH_CACHE_KEY;

@Service
@Slf4j
public class SchedulerService {
    public static final Long pageSize = 20l;

    private Long LastTransactionTotal = 0l;

    public static final int getCardDetailCount = 100;

    @Autowired
    TblCardInfoService cardInfoService;

    @Autowired
    TblCardTransactionService transactionService;

    @Autowired
    TblBizParamService tblBizParamService;

    @Autowired
    GetCardTransactionsByCompanyId getCardTransactionsByCompanyId;

    @Autowired
    GetCompanyTransactionsTotalCount getCompanyTransactionsTotalCount;

    @Autowired
    UnfreezedCard unfreezedCard;
    @Autowired
    GetCompanyVirtualCards getCompanyVirtualCards;
    @Autowired
    CommonService commonService;
    @Autowired
    GetVirtualCardDetailsInfo getVirtualCardDetailsInfo;
    @Autowired
    GetVirtualCardEditInfo getVirtualCardEditInfo;

    //更新卡的allcocation
    @Scheduled(cron = "* * * 1 * *")
    public void updateCardAllocationId() {

    }

    //创建卡
    @Scheduled(fixedRate = 1 * 30 * 1000)
    public void createCard() {
        val cardQuery = new TblCardInfo();
        cardQuery.setCardStatus("tobecreate");
        val list = cardInfoService.findList(cardQuery);
        val ids = list.stream().map(t -> {
            t.setCardStatus("creating");
            return t.getId();
        }).collect(Collectors.toList()).toArray(new String[0]);
        cardQuery.setCardStatus("creating");
        cardQuery.setId_in(ids);
        cardInfoService.update(cardQuery);
        list.stream().forEach(t -> {
            try {
                String timestamp = String.valueOf(System.currentTimeMillis());
                val rsp = SpringUtils.getBean(CreateCardByBudget.class).init(String.valueOf(t.getCardAmount()), String.valueOf(System.currentTimeMillis())).execute();//实际卡名用时间戳，好排序
                t.setBudgetId(rsp.getBudgetId());
                t.setCardId(rsp.getCardId());
                t.setCardStatus(rsp.getStep2Resp().getCardStatus());
                t.setCardToken(rsp.getStep2Resp().getCardToken());
                t.setCardBrand(rsp.getStep2Resp().getBrand());
                t.setCardNo(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getCardNo());
                t.setCvv(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getCvv());
                t.setExp(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getExp());
                t.setCardSpendAmount(0l);
                t.setExpiredDate(rsp.getStep2Resp().getExpirationDate());
                t.setUserAllocationId(rsp.getStep2Resp().getUserAllocation());
                t.setCardType(rsp.getStep2Resp().getCardType());
                t.setNickname(timestamp);
                t.setIsNewRecord(false);
                t.setUpdateDate(new Date());
                cardInfoService.save(t);
                commonService.updateCardCache(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //自动激活卡
    @Scheduled(cron = "0/30 * * * * *")
    public void UnfreezedCard() {
        val cardQuery = new TblCardInfo();
        cardQuery.setCardStatus("frozen");
        val list = cardInfoService.findList(cardQuery);
        list.forEach(t -> {
            try {
                unfreezedCard(t.getCardId(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void unfreezedCard(String cardId, int retry) throws Exception {
        val rsp = unfreezedCard.init(cardId).execute();
        if (rsp.isFrozen() && retry < 5) {
            retry++;
            unfreezedCard(cardId, retry++);
        }
    }

    //自动更新余额与状态
    @Scheduled(cron = "0/5 * * * * *")
    public void updateCardAmount() {
        val cardQuery = new TblCardInfo();
        cardQuery.setUpdateDate_lte(new DateTime().minusMinutes(30).toDate());
        cardQuery.setPageSize(getCardDetailCount);
        var page = cardInfoService.findPage(cardQuery);
        var list = page.getList();
        if (list == null || list.size() <= 0) {
            cardQuery.setUpdateDate_lte(new DateTime().minusMinutes(20).toDate());
            list = cardInfoService.findPage(cardQuery).getList();
        }
        if (list.size() > 0) {
            list.forEach(t -> {
                try {
                    val rsp = getVirtualCardEditInfo.init(t.getCardId()).execute();
                    val rsp2 = getVirtualCardDetailsInfo.init(t.getCardId()).execute();
                    t.setCardAmount(rsp.getAmount());
                    t.setCardSpendAmount(rsp.getClearedAmount());
                    t.setUserAllocationId(rsp.getAllocationId());
                    t.setUpdateDate(new Date());
                    t.setCardStatus(rsp2.isFrozen() ? (rsp2.isDeleted() ? "deleted" : "frozen") : "actived");
                    cardInfoService.update(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if (page.getCount() / 30 > 3) {
            updateCardAmount();
        }

    }


    @Scheduled(cron = "* 20 * * * *")
    public void KeepSession() {

    }

    /**
     * 获取流水
     *
     * @throws IOException
     */
//    @Scheduled(cron = "0 0/1 * * * *")//每分钟一次
    @Scheduled(cron = "0/30 * * * * *")//每分钟一次
    public void GetCardTransactions() throws Exception {
        val param = new TblBizParam();
        param.setKey("TransactionsTotal");
        val list = tblBizParamService.findList(param);
        if (list != null && list.size() == 1) {
            LastTransactionTotal = Long.valueOf(list.get(0).getValue());
        }
        val transactionTotal = getCompanyTransactionsTotalCount.init().execute();
        if (transactionTotal.getTotalCount() != LastTransactionTotal) {
            val size = transactionTotal.getTotalCount() - LastTransactionTotal;
            if (size > pageSize) {
                for (int i = 0; i < (size % pageSize > 0 ? 1 : 0) + size / pageSize; i++) {
                    val rsp = getCardTransactionsByCompanyId.init("", null, null, pageSize, i * pageSize).execute();
                    saveTransaction(rsp);
                }
            } else {
                val rsp = getCardTransactionsByCompanyId.init("", null, null, pageSize, 0l).execute();
                saveTransaction(rsp);
            }
            if (list != null && list.size() == 1) {
                val tmp = list.get(0);
                tmp.setValue(String.valueOf(transactionTotal.getTotalCount()));
                tblBizParamService.update(tmp);
            }
        }

    }

    private void saveTransaction(GetCardTransactionsByCompanyIdRsp rsp) {
        rsp.getList().forEach(t -> {
            val transaction = new TblCardTransaction();
            transaction.setIsNewRecord(true);
            transaction.setSpTransactionId(t.getTransactionId());
            transaction.setStatus(t.getType());
            transaction.setAmount(t.getAmount());
            transaction.setCardId(t.getCardId());
            transaction.setClearedDate(new DateTime(t.getClearDate()).toString("yyyy-MM-dd HH:mm:ss"));
            transaction.setOccurredDate(new DateTime(t.getOccurredDate()).toString("yyyy-MM-dd HH:mm:ss"));
            transaction.setClearedMerchant(t.getMerchantName());
            transaction.setMerchantLogo(t.getMerchantLogo());
            transaction.setTransactionStatus(t.getStatus());
            if (((TblCardInfo) CacheUtils.get(CommonService.CARD_CACHE, t.getCardId())) == null) {
                log.error("没有相关卡与id{}", t.getCardId());
            } else {
                transaction.setCardNo(((TblCardInfo) CacheUtils.get(CommonService.CARD_CACHE, t.getCardId())).getCardNo());
                transaction.setCardOwner(((TblCardInfo) CacheUtils.get(CommonService.CARD_CACHE, t.getCardId())).getCardOwner());
            }
            transaction.setClearedMerchant(t.getClearMerchantName());
            try {
                transactionService.save(transaction);
            } catch (Exception e) {
                log.error("save transaction error", e);
            }

        });
    }


    @Scheduled(cron = "* 20 * * * *")
    public void GetAllCards() throws Exception {
        val query = new TblCardInfo();
        query.setOrderBy("nickname desc");
        query.setPageSize(1);
        val lastList = cardInfoService.findPage(query);
        val count = cardInfoService.findCount(new TblCardInfo());
        val rsp = getCompanyVirtualCards.init("10", "0").execute();
//        if (rsp.getCardTotalCount() != count) {
//            for (int i = 0; i < rsp.getList().size(); i++) {
//                rsp.getList().get(i).getName()==
//            }
//            );
//        }
    }

    @Scheduled(fixedDelay = 30 * 60 * 1000)//30分钟
    public void updateCardCache() {
        commonService.updateCardCache();
    }

    @Scheduled(fixedDelay = 60 * 1000)//30分钟
    public void updateSiteConfigCache() {
        val param = new TblBizParam();
        param.setKey("SiteSwitch");
        val list = SpringUtils.getBean(TblBizParamService.class).findList(param);
        if (list != null && list.size() > 0) {
            val value = list.get(0).getValue();
            CacheUtils.put(SITE_SWITCH_CACHE,SITE_SWITCH_CACHE_KEY,value);
        }
    }

}
