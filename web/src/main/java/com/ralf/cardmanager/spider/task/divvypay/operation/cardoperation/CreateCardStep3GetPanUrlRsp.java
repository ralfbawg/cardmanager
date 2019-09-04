package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCardStep3GetPanUrlRsp extends BaseDivvyOpertionResp {
    private GetCardinfoRsp cardinfoRsp;
    private String panUrl;

}
