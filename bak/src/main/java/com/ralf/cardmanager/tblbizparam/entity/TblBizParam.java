/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tblbizparam.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 业务参数表Entity
 * @author ralfchen
 * @version 2019-08-26
 */
@Table(name="tbl_biz_param", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="参数名称", queryType=QueryType.LIKE),
		@Column(name="key", attrName="key", label="参数key"),
		@Column(name="value", attrName="value", label="参数value"),
	}, orderBy="a.id DESC"
)
public class TblBizParam extends DataEntity<TblBizParam> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 参数名称
	private String key;		// 参数key
	private String value;		// 参数value
	
	public TblBizParam() {
		this(null);
	}

	public TblBizParam(String id){
		super(id);
	}
	
	@NotBlank(message="参数名称不能为空")
	@Length(min=0, max=255, message="参数名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank(message="参数key不能为空")
	@Length(min=0, max=64, message="参数key长度不能超过 64 个字符")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@NotBlank(message="参数value不能为空")
	@Length(min=0, max=128, message="参数value长度不能超过 128 个字符")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}