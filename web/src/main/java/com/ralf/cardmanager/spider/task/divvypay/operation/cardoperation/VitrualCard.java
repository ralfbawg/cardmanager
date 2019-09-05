package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VitrualCard {
    private String id;
    private String activationStatus;
    private String expirationDate;
    private boolean frozen;
    private String cardToken;
    private String cardType;
    private String name;
    private boolean blocked;
}
