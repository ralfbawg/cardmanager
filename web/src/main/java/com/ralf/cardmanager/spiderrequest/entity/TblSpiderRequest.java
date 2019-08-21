/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.spiderrequest.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tbl_spider_requestEntity
 * @author ralfchen
 * @version 2019-08-21
 */
@Table(name="tbl_spider_request", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="type"),
		@Column(name="args_expression", attrName="argsExpression", label="args_expression"),
		@Column(name="callback_class", attrName="callbackClass", label="callback_class"),
		@Column(name="callback_method", attrName="callbackMethod", label="callback_method"),
		@Column(name="order_id", attrName="orderId", label="order_id"),
		@Column(name="param", attrName="param", label="param"),
		@Column(name="response", attrName="response", label="response"),
	}, orderBy="a.id DESC"
)
public class TblSpiderRequest extends DataEntity<TblSpiderRequest> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String type;		// type
	private String argsExpression;		// args_expression
	private String callbackClass;		// callback_class
	private String callbackMethod;		// callback_method
	private String orderId;		// order_id
	private String param;		// param
	private String response;		// response
	
	public TblSpiderRequest() {
		this(null);
	}

	public TblSpiderRequest(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="name长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="type长度不能超过 255 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="args_expression长度不能超过 255 个字符")
	public String getArgsExpression() {
		return argsExpression;
	}

	public void setArgsExpression(String argsExpression) {
		this.argsExpression = argsExpression;
	}
	
	public String getCallbackClass() {
		return callbackClass;
	}

	public void setCallbackClass(String callbackClass) {
		this.callbackClass = callbackClass;
	}
	
	public String getCallbackMethod() {
		return callbackMethod;
	}

	public void setCallbackMethod(String callbackMethod) {
		this.callbackMethod = callbackMethod;
	}
	
	@Length(min=0, max=64, message="order_id长度不能超过 64 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}