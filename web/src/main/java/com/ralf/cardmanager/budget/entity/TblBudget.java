/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tbl_budgetEntity
 * @author ralfchen
 * @version 2019-08-18
 */
@Table(name="tbl_budget", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="owner_usercode", attrName="ownerUsercode", label="owner_usercode", isUpdate=false),
		@Column(name="create_time", attrName="createTime", label="create_time", isUpdate=false),
		@Column(name="account_id", attrName="accountId", label="account_id", isUpdate=false),
		@Column(name="card_service_provider", attrName="cardServiceProvider", label="card_service_provider", isUpdate=false),
		@Column(name="sp_budget_id", attrName="spBudgetId", label="sp_budget_id", isUpdate=false),
		@Column(name="create_user_code", attrName="createUserCode", label="create_user_code", isUpdate=false),
		@Column(name="budget_amount", attrName="budgetAmount", label="所有的帐户值都为整数，实际使用除以100", isUpdate=false),
		@Column(name="total_amount", attrName="totalAmount", label="total_amount", isUpdate=false),
		@Column(name="spend_amount", attrName="spendAmount", label="spend_amount", isUpdate=false),
		@Column(name="assign_amount", attrName="assignAmount", label="assign_amount", isUpdate=false),
		@Column(name="unsign_amount", attrName="unsignAmount", label="unsign_amount", isUpdate=false),
		@Column(name="status", attrName="status", label="status"),
		@Column(name="virtual", attrName="virtual", label="1.divyy帐户2本地帐户", isUpdate=false),
		@Column(name="card_create_limit", attrName="cardCreateLimit", label="建卡数限制", isUpdate=false),
	}, orderBy="a.id DESC"
)
public class TblBudget extends DataEntity<TblBudget> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String ownerUsercode;		// owner_usercode
	private Date createTime;		// create_time
	private String accountId;		// account_id
	private String cardServiceProvider;		// card_service_provider
	private String spBudgetId;		// sp_budget_id
	private String createUserCode;		// create_user_code
	private Long budgetAmount;		// 所有的帐户值都为整数，实际使用除以100
	private Long totalAmount;		// total_amount
	private Long spendAmount;		// spend_amount
	private Long assignAmount;		// assign_amount
	private Long unsignAmount;		// unsign_amount
	private Integer virtual;		// 1.divyy帐户2本地帐户
	private Long cardCreateLimit;		// 建卡数限制
	private List<TblCardInfo> tblCardInfoList = ListUtils.newArrayList();		// 子表列表
	
	public TblBudget() {
		this(null);
	}

	public TblBudget(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="name长度不能超过 64 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
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
	
	@Length(min=0, max=64, message="account_id长度不能超过 64 个字符")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=64, message="card_service_provider长度不能超过 64 个字符")
	public String getCardServiceProvider() {
		return cardServiceProvider;
	}

	public void setCardServiceProvider(String cardServiceProvider) {
		this.cardServiceProvider = cardServiceProvider;
	}
	
	@Length(min=0, max=128, message="sp_budget_id长度不能超过 128 个字符")
	public String getSpBudgetId() {
		return spBudgetId;
	}

	public void setSpBudgetId(String spBudgetId) {
		this.spBudgetId = spBudgetId;
	}
	
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
	
	public List<TblCardInfo> getTblCardInfoList() {
		return tblCardInfoList;
	}

	public void setTblCardInfoList(List<TblCardInfo> tblCardInfoList) {
		this.tblCardInfoList = tblCardInfoList;
	}
	
}