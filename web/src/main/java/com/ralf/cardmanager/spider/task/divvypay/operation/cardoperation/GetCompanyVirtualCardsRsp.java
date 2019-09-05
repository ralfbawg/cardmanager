package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetCompanyVirtualCardsRsp extends BaseDivvyOpertionResp {
    private Long cardTotalCount;
    private boolean hasNextPage;
    private String endCursor;
    private List<VitrualCard> list;
}
