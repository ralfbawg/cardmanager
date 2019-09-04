package com.ralf.cardmanager.scheduler;

import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.CreateCardByBudget;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.UnfreezedCard;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCardTransactionsByCompanyId;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCompanyTransactionsTotalCount;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.val;
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
    TblBizParamService tblBizParamService;

    @Autowired
    GetCardTransactionsByCompanyId getCardTransactionsByCompanyId;

    @Autowired
    GetCompanyTransactionsTotalCount getCompanyTransactionsTotalCount;

    @Autowired
    UnfreezedCard unfreezedCard;

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
                val rsp = SpringUtils.getBean(CreateCardByBudget.class).init(String.valueOf(t.getCardAmount()), t.getCardName()).execute();
                t.setBudgetId(rsp.getBudgetId());
                t.setCardId(rsp.getCardId());
                t.setCardStatus(rsp.getStep2Resp().getCardStatus());
                t.setCardToken(rsp.getStep2Resp().getCardToken());
                t.setCardBrand(rsp.getStep2Resp().getBrand());
                t.setCardNo(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getCardNo());
                t.setCvv(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getCvv());
                t.setExp(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getExp());
                t.setExpiredDate(rsp.getStep2Resp().getExpirationDate());
                t.setUserAllocationId(rsp.getStep2Resp().getUserAllocation());
                t.setCardType(rsp.getStep2Resp().getCardType());
                t.setIsNewRecord(false);
                cardInfoService.save(t);
            } catch (IOException e) {
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
        val ids = list.stream().forEach(t -> {
            try {
                unfreezedCard(t.getCardId(), 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void unfreezedCard(String cardId, int retry) throws IOException {
        val rsp = unfreezedCard.init(cardId).execute();
        if (rsp.isFrozen() && retry < 5) {
            retry++;
            unfreezedCard(cardId, retry++);
        }
    }


    @Scheduled(cron = "* 20 * * * *")
    public void KeepSession() {

    }

    @Scheduled(cron = "* 20 * * * *")
    public void GetCardTransactions() throws IOException {
        if (LastTransactionTotal <= 0) {
            val param = new TblBizParam();
            param.setKey("TransactionsTotal");
            val list = tblBizParamService.findList(param);
            if (list != null && list.size() == 1) {
                LastTransactionTotal = Long.valueOf(list.get(0).getValue());
            }
        }
        val rsp = getCardTransactionsByCompanyId.init("", null, null, null).execute();
        rsp.getList().stream().forEach(t -> {

        });

    }


}
