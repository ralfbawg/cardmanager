/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardinfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 银行卡信息Entity
 * @author ralfchen
 * @version 2019-09-03
 */
@Table(name="tbl_card_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="card_no", attrName="cardNo", label="银行卡号"),
		@Column(name="budget_id", attrName="budgetId", label="帐户id"),
		@Column(name="card_name", attrName="cardName", label="卡名", queryType=QueryType.LIKE),
		@Column(name="card_limit", attrName="cardLimit", label="限额度", isQuery=false),
		@Column(name="nickname", attrName="nickname", label="卡昵称", isQuery=false),
		@Column(name="card_type", attrName="cardType", label="卡类型", isQuery=false),
		@Column(name="card_amount", attrName="cardAmount", label="现有额度", isQuery=false),
		@Column(name="last_charge_on", attrName="lastChargeOn", label="上次充值时间", isQuery=false),
		@Column(name="card_spend_amount", attrName="cardSpendAmount", label="已使用额度", isQuery=false),
		@Column(name="card_owner", attrName="cardOwner", label="持卡人"),
		@Column(name="card_billing_address", attrName="cardBillingAddress", label="账单地址", isQuery=false),
		@Column(name="cvv", attrName="cvv", label="cvv", isQuery=false),
		@Column(name="exp", attrName="exp", label="exp", isQuery=false),
		@Column(name="card_category", attrName="cardCategory", label="card_category"),
		@Column(name="card_token", attrName="cardToken", label="卡token", isQuery=false),
		@Column(name="card_id", attrName="cardId", label="card_id"),
		@Column(name="expired_date", attrName="expiredDate", label="卡面过期时间"),
		@Column(name="card_brand", attrName="cardBrand", label="卡品牌"),
		@Column(name="user_allocation_id", attrName="userAllocationId", label="用户操作码", isQuery=false),
		@Column(name="card_status", attrName="cardStatus", label="卡状态"),
	}, orderBy="a.id DESC"
)
public class TblCardInfo extends DataEntity<TblCardInfo> {
	
	private static final long serialVersionUID = 1L;
	private String cardNo;		// 银行卡号
	private String budgetId;		// 帐户id
	private String cardName;		// 卡名
	private Long cardLimit;		// 限额度
	private String nickname;		// 卡昵称
	private String cardType;		// 卡类型
	private Long cardAmount;		// 现有额度
	private Date lastChargeOn;		// 上次充值时间
	private Long cardSpendAmount;		// 已使用额度
	private String cardOwner;		// 持卡人
	private String cardBillingAddress;		// 账单地址
	private String cvv;		// cvv
	private String exp;		// exp
	private String cardCategory;		// card_category
	private String cardToken;		// 卡token
	private String cardId;		// card_id
	private String expiredDate;		// 卡面过期时间
	private String cardBrand;		// 卡品牌
	private String userAllocationId;		// 用户操作码
	private String cardStatus;		// 卡状态
	
	public TblCardInfo() {
		this(null);
	}

	public TblCardInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=32, message="银行卡号长度不能超过 32 个字符")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	@Length(min=0, max=64, message="帐户id长度不能超过 64 个字符")
	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}
	
	@Length(min=0, max=128, message="卡名长度不能超过 128 个字符")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public Long getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(Long cardLimit) {
		this.cardLimit = cardLimit;
	}
	
	@Length(min=0, max=32, message="卡昵称长度不能超过 32 个字符")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=32, message="卡类型长度不能超过 32 个字符")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public Long getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(Long cardAmount) {
		this.cardAmount = cardAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastChargeOn() {
		return lastChargeOn;
	}

	public void setLastChargeOn(Date lastChargeOn) {
		this.lastChargeOn = lastChargeOn;
	}
	
	public Long getCardSpendAmount() {
		return cardSpendAmount;
	}

	public void setCardSpendAmount(Long cardSpendAmount) {
		this.cardSpendAmount = cardSpendAmount;
	}
	
	@Length(min=0, max=128, message="持卡人长度不能超过 128 个字符")
	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}
	
	@Length(min=0, max=255, message="账单地址长度不能超过 255 个字符")
	public String getCardBillingAddress() {
		return cardBillingAddress;
	}

	public void setCardBillingAddress(String cardBillingAddress) {
		this.cardBillingAddress = cardBillingAddress;
	}
	
	@Length(min=0, max=8, message="cvv长度不能超过 8 个字符")
	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	@Length(min=0, max=16, message="exp长度不能超过 16 个字符")
	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}
	
	@Length(min=0, max=128, message="card_category长度不能超过 128 个字符")
	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}
	
	@Length(min=0, max=128, message="卡token长度不能超过 128 个字符")
	public String getCardToken() {
		return cardToken;
	}

	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}
	
	@Length(min=0, max=64, message="card_id长度不能超过 64 个字符")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@Length(min=0, max=32, message="卡面过期时间长度不能超过 32 个字符")
	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	@Length(min=0, max=32, message="卡品牌长度不能超过 32 个字符")
	public String getCardBrand() {
		return cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}
	
	@Length(min=0, max=128, message="用户操作码长度不能超过 128 个字符")
	public String getUserAllocationId() {
		return userAllocationId;
	}

	public void setUserAllocationId(String userAllocationId) {
		this.userAllocationId = userAllocationId;
	}
	
	@Length(min=0, max=128, message="卡状态长度不能超过 128 个字符")
	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	
}