package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCardinfoRsp extends BaseDivvyOpertionResp {
    private String cvv;
    private String exp;
    private String cardNo;
}
