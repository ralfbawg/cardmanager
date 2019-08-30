package com.ralf.cardmanager.spider.task.divvypay.task;

import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulService {
    @Autowired
    TblCardInfoService cardInfoService;

    //更新卡的allcocation
    @Scheduled(cron = "* * * 1 * *")
    public void updateCardAllocationId() {

    }

    //更新卡的Recurring
    @Scheduled(cron = "* * * 28 * *")
    public void updateCardLimitRecurring() {

    }

    //自动解冻卡
    @Scheduled(cron = "* * * 28 * *")
    public void UnfreezedCard() {

    }

    @Scheduled(cron = "* 20 * * * *")
    public void KeepSession(){

    }
}
