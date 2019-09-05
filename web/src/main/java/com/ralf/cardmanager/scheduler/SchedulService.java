package com.ralf.cardmanager.scheduler;

import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.cardtransaction.entity.TblCardTransaction;
import com.ralf.cardmanager.cardtransaction.service.TblCardTransactionService;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.CreateCardByBudget;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.GetCompanyVirtualCards;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.UnfreezedCard;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCardTransactionsByCompanyId;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCompanyTransactionsTotalCount;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class SchedulService {
    private Long LastTransactionTotal = 0l;

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
                cardInfoService.save(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //自动解冻卡
    @Scheduled(cron = "0/30 * * * * *")
    public void UnfreezedCard() {
        val cardQuery = new TblCardInfo();
        cardQuery.setCardStatus("freezed");
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


    @Scheduled(cron = "* 20 * * * *")
    public void KeepSession() {

    }

    /**
     * 获取流水
     *
     * @throws IOException
     */
    @Scheduled(cron = "* 20 * * * *")
    public void GetCardTransactions() throws Exception {
        if (LastTransactionTotal <= 0) {
            val param = new TblBizParam();
            param.setKey("TransactionsTotal");
            val list = tblBizParamService.findList(param);
            if (list != null && list.size() == 1) {
                LastTransactionTotal = Long.valueOf(list.get(0).getValue());
            }
        }
        val transactionTotal = getCompanyTransactionsTotalCount.init().execute();
        if (transactionTotal.getTotalCount() != LastTransactionTotal) {
            val size = transactionTotal.getTotalCount() - LastTransactionTotal;
//            if (size > 20) {
//                for (int i = 0; i < size / 20; i++) {
//                    val rsp = getCardTransactionsByCompanyId.init("", null, null, size, i*20l).execute();
//                    rsp.getList()
//                    val transaction = new TblCardTransaction();
//                    transaction.setIsNewRecord(true);
//                    transaction.setSpTransactionId();
//
//
//                }
//            }
            val rsp = getCardTransactionsByCompanyId.init("", null, null, size, 0l).execute();
            rsp.getList().forEach(t -> {
                val transaction = new TblCardTransaction();
                transaction.setIsNewRecord(true);
                transaction.setSpTransactionId(t.getTransactionId());
                transaction.setAmount(t.getAmount());
                transaction.setCardId(t.getCardId());
                transaction.setDate(new DateTime(t.getOccurredDate()).toString("yyyy-MM-dd HH:mm:ss"));
                transaction.setLastVendor(t.getMerchantName());
                transaction.setTransactionStatus(t.getStatus());
                transactionService.save(transaction);
            });

        }

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

}
