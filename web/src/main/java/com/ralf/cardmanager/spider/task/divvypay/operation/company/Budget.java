package com.ralf.cardmanager.spider.task.divvypay.operation.company;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Budget {
    private String id;
    private String name;
    private String type;
    private Long balance;
    private Long totalAvailableToSpend;
    private Long currentGoal;

}
