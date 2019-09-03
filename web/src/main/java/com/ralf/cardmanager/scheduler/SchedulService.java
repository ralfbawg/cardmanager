package com.ralf.cardmanager.scheduler;

import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.spider.task.divvypay.operation.CreateCardByBudget;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SchedulService {
    @Autowired
    TblCardInfoService cardInfoService;

    //更新卡的allcocation
    @Scheduled(cron = "* * * 1 * *")
    public void updateCardAllocationId() {

    }

    //创建卡
    @Scheduled(fixedDelay = 1 * 30 * 1000)
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
            SpringUtils.getBean(CreateCardByBudget.class).init(String.valueOf(t.getCardAmount()), t.getCardName());
        });
    }

    //自动解冻卡
    @Scheduled(cron = "* * * 28 * *")
    public void UnfreezedCard() {

    }

    @Scheduled(cron = "* 20 * * * *")
    public void KeepSession() {

    }
}
