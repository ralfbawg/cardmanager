/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardtransaction.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tbl_card_transactionEntity
 * @author ralfchen
 * @version 2019-09-02
 */
@Table(name="tbl_card_transaction", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="card_id", attrName="cardId", label="卡id", isQuery=false),
		@Column(name="sp_transaction_id", attrName="spTransactionId", label="银行流水id"),
		@Column(name="card_num", attrName="cardNum", label="卡号"),
		@Column(name="last_vendor", attrName="lastVendor", label="结算商户"),
		@Column(name="date", attrName="date", label="清算日期"),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
		@Column(name="amount", attrName="amount", label="消费金额"),
		@Column(name="transaction_status", attrName="transactionStatus", label="交易状态"),
		@Column(name="note", attrName="note", label="注释"),
		@Column(name="label", attrName="label", label="标签"),
	}, orderBy="a.id DESC"
)
public class TblCardTransaction extends DataEntity<TblCardTransaction> {
	
	private static final long serialVersionUID = 1L;
	private String cardId;		// 卡id
	private String spTransactionId;		// 银行流水id
	private String cardNum;		// 卡号
	private String lastVendor;		// 结算商户
	private String date;		// 清算日期
	private Long amount;		// 消费金额
	private String transactionStatus;		// 交易状态
	private String note;		// 注释
	private String label;		// 标签
	
	public TblCardTransaction() {
		this(null);
	}

	public TblCardTransaction(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="卡id长度不能超过 64 个字符")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@Length(min=0, max=256, message="银行流水id长度不能超过 256 个字符")
	public String getSpTransactionId() {
		return spTransactionId;
	}

	public void setSpTransactionId(String spTransactionId) {
		this.spTransactionId = spTransactionId;
	}
	
	@Length(min=0, max=16, message="卡号长度不能超过 16 个字符")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	@Length(min=0, max=128, message="结算商户长度不能超过 128 个字符")
	public String getLastVendor() {
		return lastVendor;
	}

	public void setLastVendor(String lastVendor) {
		this.lastVendor = lastVendor;
	}
	
	@Length(min=0, max=16, message="清算日期长度不能超过 16 个字符")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=255, message="交易状态长度不能超过 255 个字符")
	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	@Length(min=0, max=32, message="注释长度不能超过 32 个字符")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Length(min=0, max=32, message="标签长度不能超过 32 个字符")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}