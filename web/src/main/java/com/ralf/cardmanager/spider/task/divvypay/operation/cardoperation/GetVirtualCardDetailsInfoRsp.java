package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetVirtualCardDetailsInfoRsp extends BaseDivvyOpertionResp {
    private boolean frozen;
    private boolean deleted;
}
