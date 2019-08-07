package com.ralf.cardmanager.spider.task.divvypay.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulService {

    //更新卡的allcocation
    @Scheduled(cron = "* * * 1 * *")
    public void updateCardAllocationId() {

    }

    //更新卡的allcocation
    @Scheduled(cron = "* * * 28 * *")
    public void updateCardLimitRecurring() {

    }


}
