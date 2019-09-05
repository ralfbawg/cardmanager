package com.ralf.cardmanager.spider.task.divvypay.operation.company;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetCompanyBudgetsRsp extends BaseDivvyOpertionResp {
    private boolean hasNextPage;
    private String endCursor;
    private List<Budget> list;
}
