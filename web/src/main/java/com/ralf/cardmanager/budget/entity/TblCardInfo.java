/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tbl_budgetEntity
 * @author ralfchen
 * @version 2019-08-13
 */
@Table(name="tbl_card_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="card_num", attrName="cardNum", label="卡号"),
		@Column(name="budget_id", attrName="budgetId.id", label="budget_id"),
		@Column(name="card_name", attrName="cardName", label="card_name", queryType=QueryType.LIKE),
		@Column(name="card_limit", attrName="cardLimit", label="限额度"),
		@Column(name="nickname", attrName="nickname", label="nickname"),
		@Column(name="card_type", attrName="cardType", label="卡类型", comment="卡类型:burner,subscription"),
		@Column(name="last_charge_on", attrName="lastChargeOn", label="上次充值时间"),
		@Column(name="billing_address", attrName="billingAddress", label="账单地址"),
		@Column(name="card_owner", attrName="cardOwner", label="同card_holder"),
		@Column(name="category", attrName="category", label="category"),
		@Column(name="cvv", attrName="cvv", label="cvv"),
		@Column(name="exp", attrName="exp", label="exp"),
		@Column(name="status", attrName="status", label="卡状态", comment="卡状态:actived,delete,freezed", isUpdate=false),
		@Column(name="card_token", attrName="cardToken", label="用来获取卡号 cvv 过期时间"),
		@Column(name="card_id", attrName="cardId", label="card_id"),
		@Column(name="card_usercode", attrName="cardUsercode", label="card_usercode"),
		@Column(name="expired_date", attrName="expiredDate", label="卡面过期时间"),
		@Column(name="card_brand", attrName="cardBrand", label="卡品牌 master"),
		@Column(name="pan_url", attrName="panUrl", label="获取卡号"),
		@Column(name="user_allocation_id", attrName="userAllocationId", label="user_allocation_id"),
	}, orderBy="a.id ASC"
)
public class TblCardInfo extends DataEntity<TblCardInfo> {
	
	private static final long serialVersionUID = 1L;
	private String cardNum;		// 卡号
	private TblBudget budgetId;		// budget_id 父类
	private String cardName;		// card_name
	private Long cardLimit;		// 限额度
	private String nickname;		// nickname
	private String cardType;		// 卡类型:burner,subscription
	private String lastChargeOn;		// 上次充值时间
	private String billingAddress;		// 账单地址
	private String cardOwner;		// 同card_holder
	private String category;		// category
	private String cvv;		// cvv
	private String exp;		// exp
	private String cardToken;		// 用来获取卡号 cvv 过期时间
	private String cardId;		// card_id
	private String cardUsercode;		// card_usercode
	private String expiredDate;		// 卡面过期时间
	private String cardBrand;		// 卡品牌 master
	private String panUrl;		// 获取卡号
	private String userAllocationId;		// user_allocation_id
	
	public TblCardInfo() {
		this(null);
	}


	public TblCardInfo(TblBudget budgetId){
		this.budgetId = budgetId;
	}
	
	@Length(min=0, max=32, message="卡号长度不能超过 32 个字符")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	public TblBudget getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(TblBudget budgetId) {
		this.budgetId = budgetId;
	}
	
	@NotBlank(message="card_name不能为空")
	@Length(min=0, max=128, message="card_name长度不能超过 128 个字符")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	@NotNull(message="限额度不能为空")
	public Long getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(Long cardLimit) {
		this.cardLimit = cardLimit;
	}
	
	@NotBlank(message="nickname不能为空")
	@Length(min=0, max=32, message="nickname长度不能超过 32 个字符")
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
	
	public String getLastChargeOn() {
		return lastChargeOn;
	}

	public void setLastChargeOn(String lastChargeOn) {
		this.lastChargeOn = lastChargeOn;
	}
	
	@Length(min=0, max=255, message="账单地址长度不能超过 255 个字符")
	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	@Length(min=0, max=128, message="同card_holder长度不能超过 128 个字符")
	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}
	
	@NotBlank(message="category不能为空")
	@Length(min=0, max=128, message="category长度不能超过 128 个字符")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	
	@Length(min=0, max=128, message="用来获取卡号 cvv 过期时间长度不能超过 128 个字符")
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
	
	@Length(min=0, max=128, message="card_usercode长度不能超过 128 个字符")
	public String getCardUsercode() {
		return cardUsercode;
	}

	public void setCardUsercode(String cardUsercode) {
		this.cardUsercode = cardUsercode;
	}
	
	@Length(min=0, max=32, message="卡面过期时间长度不能超过 32 个字符")
	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	@Length(min=0, max=32, message="卡品牌 master长度不能超过 32 个字符")
	public String getCardBrand() {
		return cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}
	
	public String getPanUrl() {
		return panUrl;
	}

	public void setPanUrl(String panUrl) {
		this.panUrl = panUrl;
	}
	
	@Length(min=0, max=128, message="user_allocation_id长度不能超过 128 个字符")
	public String getUserAllocationId() {
		return userAllocationId;
	}

	public void setUserAllocationId(String userAllocationId) {
		this.userAllocationId = userAllocationId;
	}
	
}