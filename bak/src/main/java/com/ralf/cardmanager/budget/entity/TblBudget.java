/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.entity;

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
 * 帐户信息Entity
 * @author ralfchen
 * @version 2019-08-29
 */
@Table(name="tbl_budget", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="帐户名", queryType=QueryType.LIKE),
		@Column(name="owner_usercode", attrName="ownerUsercode", label="拥有者"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="account_id", attrName="accountId", label="帐户id", isQuery=false),
		@Column(name="card_service_provider", attrName="cardServiceProvider", label="卡服务提供商"),
		@Column(name="sp_budget_id", attrName="spBudgetId", label="卡服务提供商帐户id", isQuery=false),
		@Column(name="create_user_code", attrName="createUserCode", label="创建者"),
		@Column(name="budget_amount", attrName="budgetAmount", label="帐户额度", isQuery=false),
		@Column(name="total_amount", attrName="totalAmount", label="总额度", isQuery=false),
		@Column(name="spend_amount", attrName="spendAmount", label="已使用额度", isQuery=false),
		@Column(name="assign_amount", attrName="assignAmount", label="已分配额度", isQuery=false),
		@Column(name="unsign_amount", attrName="unsignAmount", label="未分配额度", isQuery=false),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
		@Column(name="virtual", attrName="virtual", label="是否虚拟", isQuery=false),
		@Column(name="card_create_limit", attrName="cardCreateLimit", label="卡数上限", isQuery=false),
		@Column(name="last_charge_on", attrName="lastChargeOn", label="last_charge_on"),
	}, orderBy="a.id DESC"
)
public class TblBudget extends DataEntity<TblBudget> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 帐户名
	private String ownerUsercode;		// 拥有者
	private Date createTime;		// 创建时间
	private String accountId;		// 帐户id
	private String cardServiceProvider;		// 卡服务提供商
	private String spBudgetId;		// 卡服务提供商帐户id
	private String createUserCode;		// 创建者
	private Long budgetAmount;		// 帐户额度
	private Long totalAmount;		// 总额度
	private Long spendAmount;		// 已使用额度
	private Long assignAmount;		// 已分配额度
	private Long unsignAmount;		// 未分配额度
	private Integer virtual;		// 是否虚拟
	private Long cardCreateLimit;		// 卡数上限
	private Date lastChargeOn;		// last_charge_on
	
	public TblBudget() {
		this(null);
	}

	public TblBudget(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="帐户名长度不能超过 64 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="拥有者长度不能超过 64 个字符")
	public String getOwnerUsercode() {
		return ownerUsercode;
	}

	public void setOwnerUsercode(String ownerUsercode) {
		this.ownerUsercode = ownerUsercode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=64, message="帐户id长度不能超过 64 个字符")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=64, message="卡服务提供商长度不能超过 64 个字符")
	public String getCardServiceProvider() {
		return cardServiceProvider;
	}

	public void setCardServiceProvider(String cardServiceProvider) {
		this.cardServiceProvider = cardServiceProvider;
	}
	
	@Length(min=0, max=128, message="卡服务提供商帐户id长度不能超过 128 个字符")
	public String getSpBudgetId() {
		return spBudgetId;
	}

	public void setSpBudgetId(String spBudgetId) {
		this.spBudgetId = spBudgetId;
	}
	
	@Length(min=0, max=64, message="创建者长度不能超过 64 个字符")
	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	
	public Long getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(Long budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	
	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public Long getSpendAmount() {
		return spendAmount;
	}

	public void setSpendAmount(Long spendAmount) {
		this.spendAmount = spendAmount;
	}
	
	public Long getAssignAmount() {
		return assignAmount;
	}

	public void setAssignAmount(Long assignAmount) {
		this.assignAmount = assignAmount;
	}
	
	public Long getUnsignAmount() {
		return unsignAmount;
	}

	public void setUnsignAmount(Long unsignAmount) {
		this.unsignAmount = unsignAmount;
	}
	
	public Integer getVirtual() {
		return virtual;
	}

	public void setVirtual(Integer virtual) {
		this.virtual = virtual;
	}
	
	public Long getCardCreateLimit() {
		return cardCreateLimit;
	}

	public void setCardCreateLimit(Long cardCreateLimit) {
		this.cardCreateLimit = cardCreateLimit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastChargeOn() {
		return lastChargeOn;
	}

	public void setLastChargeOn(Date lastChargeOn) {
		this.lastChargeOn = lastChargeOn;
	}
	
}