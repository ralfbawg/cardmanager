package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCardByBudgetResponse extends BaseDivvyOpertionResp {
    private String cardId;
    private String budgetId;
    private CreateCardStep2Resp step2Resp;
}
