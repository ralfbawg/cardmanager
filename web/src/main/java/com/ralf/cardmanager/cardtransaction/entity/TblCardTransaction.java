/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardtransaction.entity;

import com.jeesite.modules.sys.utils.ValidCodeUtils;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 交易流水Entity
 *
 * @author ralfchen
 * @version 2019-09-07
 */
@Table(name = "tbl_card_transaction", alias = "a", columns = {
        @Column(name = "id", attrName = "id", label = "id", isPK = true),
        @Column(name = "card_id", attrName = "cardId", label = "卡id"),
        @Column(name = "sp_transaction_id", attrName = "spTransactionId", label = "银行流水id"),
        @Column(name = "card_no", attrName = "cardNo", label = "卡号" ,queryType = QueryType.LIKE),
        @Column(name = "card_owner", attrName = "cardOwner", label = "持卡用户"),
        @Column(name = "cleared_merchant", attrName = "clearedMerchant", label = "清算商户"),
        @Column(name = "amount", attrName = "amount", label = "消费金额"),
        @Column(name = "merchant_logo", attrName = "merchantLogo", label = "商户"),
        @Column(name = "transaction_status", attrName = "transactionStatus", label = "交易状态"),
        @Column(name = "occurred_date", attrName = "occurredDate", label = "发生日期"),
        @Column(name = "cleared_date", attrName = "clearedDate", label = "清算日期"),
        @Column(name = "label", attrName = "label", label = "标签", queryType = QueryType.LIKE),
        @Column(name = "note", attrName = "note", label = "注释"),
        @Column(name = "transaction_type", attrName = "transactionType", label = "流水类型"),
        @Column(name = "fee", attrName = "fee", label = "税费"),
        @Column(name = "sp_type", attrName = "spType", label = "发卡商"),
        @Column(name = "proc_status", attrName = "procStatus", label = "处理状态"),
        @Column(name = "decline_reason", attrName = "declineReason", label = "发卡商"),
}, orderBy = "a.id DESC"
)
public class TblCardTransaction extends DataEntity<TblCardTransaction> {

    private static final long serialVersionUID = 1L;
    private String cardId;        // 卡id
    private String spTransactionId;        // 银行流水id
    private String cardNo;        // 卡号
    private String cardOwner;        // 持卡用户
    private String clearedMerchant;        // 清算商户
    private Long amount;        // 消费金额
    private String merchantLogo;        // 商户
    private String transactionStatus;        // 交易状态
    private String occurredDate;        // 发生日期
    private String clearedDate;        // 清算日期
    private String label;        // 标签
    private String note;        // 注释
    private String transactionType;        // 流水类型
    private Long fee;        // 税费
    private String spType;        // 发卡商
    private String procStatus;        // 处理状态
    private String declineReason;        // 处理状态

    public TblCardTransaction() {
        this(null);
    }

    public TblCardTransaction(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "卡id长度不能超过 64 个字符")
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Length(min = 0, max = 256, message = "银行流水id长度不能超过 256 个字符")
    public String getSpTransactionId() {
        return spTransactionId;
    }

    public void setSpTransactionId(String spTransactionId) {
        this.spTransactionId = spTransactionId;
    }

    @Length(min = 0, max = 32, message = "卡号长度不能超过 32 个字符")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Length(min = 0, max = 64, message = "持卡用户长度不能超过 64 个字符")
    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    @Length(min = 0, max = 128, message = "清算商户长度不能超过 128 个字符")
    public String getClearedMerchant() {
        return clearedMerchant;
    }

    public void setClearedMerchant(String clearedMerchant) {
        this.clearedMerchant = clearedMerchant;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    @Length(min = 0, max = 255, message = "交易状态长度不能超过 255 个字符")
    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Length(min = 0, max = 32, message = "发生日期长度不能超过 32 个字符")
    public String getOccurredDate() {
        return occurredDate;
    }

    public void setOccurredDate(String occurredDate) {
        this.occurredDate = occurredDate;
    }

    @Length(min = 0, max = 32, message = "清算日期长度不能超过 32 个字符")
    public String getClearedDate() {
        return clearedDate;
    }

    public void setClearedDate(String clearedDate) {
        this.clearedDate = clearedDate;
    }

    @Length(min = 0, max = 32, message = "标签长度不能超过 32 个字符")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Length(min = 0, max = 32, message = "注释长度不能超过 32 个字符")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Length(min = 0, max = 32, message = "流水类型长度不能超过 32 个字符")
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    @Length(min = 0, max = 128, message = "发卡商长度不能超过 128 个字符")
    public String getSpType() {
        return spType;
    }

    public void setSpType(String spType) {
        this.spType = spType;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    public String getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(String declineReason) {
        this.declineReason = declineReason;
    }

    public void setTransactionStatus_in(String[] status) {
        this.sqlMap.getWhere().and("transaction_status", QueryType.IN, status);
    }
}