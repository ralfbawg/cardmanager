package com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @program: cardmanager
 * @description: 按卡号详细
 * @author: Ralf Chen
 * @create: 2019-10-24 11:44
 **/
@Data
@AllArgsConstructor
public class GetCardTransactionsByCardIdRspDetail {
    private String transactionId;
    private String type;
    private String status;
    private String clearMerchantName;
    private Long amount;
    private Date clearDate;
    private Date occurredDate;
    private String declineReason;
    private String cardId;
    private Long fee;
    private String merchantLogo;
    private String merchantName;
}

