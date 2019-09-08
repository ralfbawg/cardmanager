package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetVirtualCardEditRsp extends BaseDivvyOpertionResp {
    private String allocationId;
    private Long amount;
    private Long initAmount;
    private Long clearedAmount;

}
