package com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCompanyTransactionsTotalCountRsp extends BaseDivvyOpertionResp {
    private Long totalCount;
}
