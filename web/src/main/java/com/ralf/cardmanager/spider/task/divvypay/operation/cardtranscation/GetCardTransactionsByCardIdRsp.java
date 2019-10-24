package com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetCardTransactionsByCardIdRsp extends BaseDivvyOpertionResp {
    private Long totalCount;
    private boolean hasNextPage;
    private String endCursor;//解码后
    private List<GetCardTransactionsByCardIdRspDetail> list;
}
