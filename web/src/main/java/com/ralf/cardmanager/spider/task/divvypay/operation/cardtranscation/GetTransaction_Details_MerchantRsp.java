package com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GetTransaction_Details_MerchantRsp extends BaseDivvyOpertionResp {
    private String transactionId;
    private String type;
    private String status;
    private String merchantName;
    private Long amount;
    private Date clearDate;
    private Date occurredDate;
}
