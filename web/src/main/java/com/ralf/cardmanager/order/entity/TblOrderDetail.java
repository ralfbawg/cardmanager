/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 订单信息Entity
 * @author ralfchen
 * @version 2019-09-06
 */
@Table(name="tbl_order_detail", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="order_id", attrName="orderId.id", label="order_id", isUpdate=false),
		@Column(name="card_name", attrName="cardName", label="卡名", isQuery=false),
		@Column(name="limit_amount", attrName="limitAmount", label="限额", isQuery=false),
		@Column(name="type", attrName="type", label="卡分类", isUpdate=false),
		@Column(name="card_id", attrName="cardId", label="card_id", isUpdate=false),
		@Column(name="status", attrName="status", label="status", isUpdate=false),
	}, orderBy="a.id ASC"
)
public class TblOrderDetail extends DataEntity<TblOrderDetail> {
	
	private static final long serialVersionUID = 1L;
	private TblOrder orderId;		// order_id 父类
	private String cardName;		// 卡名
	private Long limitAmount;		// 限额
	private String type;		// 卡分类
	private String cardId;		// card_id
	
	public TblOrderDetail() {
		this(null);
	}


	public TblOrderDetail(TblOrder orderId){
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="order_id长度不能超过 64 个字符")
	public TblOrder getOrderId() {
		return orderId;
	}

	public void setOrderId(TblOrder orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=255, message="卡名长度不能超过 255 个字符")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public Long getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Long limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	@Length(min=0, max=32, message="卡分类长度不能超过 32 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="card_id长度不能超过 64 个字符")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
}