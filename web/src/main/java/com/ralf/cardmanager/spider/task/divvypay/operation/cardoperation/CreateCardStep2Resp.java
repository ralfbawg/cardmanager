package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCardStep2Resp extends BaseDivvyOpertionResp {
    private String cardToken;
    private String cardType;
    private String expirationDate;
    private String brand;
    private String cardStatus;
    private String activationStatus;
    private String userAllocation;
    private CreateCardStep3GetPanUrlRsp step3GetPanUrlRsp;
}
