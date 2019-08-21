/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.company.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * CompanyInfoEntity
 * @author ralfchen
 * @version 2019-08-21
 */
@Table(name="tbl_company_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="name_en", attrName="nameEn", label="name_en"),
		@Column(name="manager_usercode", attrName="managerUsercode", label="manager_usercode"),
		@Column(name="manager_first_name", attrName="managerFirstName", label="manager_first_name", queryType=QueryType.LIKE),
		@Column(name="manager_last_name", attrName="managerLastName", label="manager_last_name", queryType=QueryType.LIKE),
		@Column(name="biz_file_id", attrName="bizFileId", label="biz_file_id"),
	}, orderBy="a.id DESC"
)
public class TblCompanyInfo extends DataEntity<TblCompanyInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String nameEn;		// name_en
	private String managerUsercode;		// manager_usercode
	private String managerFirstName;		// manager_first_name
	private String managerLastName;		// manager_last_name
	private String bizFileId;		// biz_file_id
	
	public TblCompanyInfo() {
		this(null);
	}

	public TblCompanyInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="name长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="name_en长度不能超过 255 个字符")
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	@Length(min=0, max=255, message="manager_usercode长度不能超过 255 个字符")
	public String getManagerUsercode() {
		return managerUsercode;
	}

	public void setManagerUsercode(String managerUsercode) {
		this.managerUsercode = managerUsercode;
	}
	
	@Length(min=0, max=255, message="manager_first_name长度不能超过 255 个字符")
	public String getManagerFirstName() {
		return managerFirstName;
	}

	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}
	
	@Length(min=0, max=255, message="manager_last_name长度不能超过 255 个字符")
	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}
	
	@Length(min=0, max=64, message="biz_file_id长度不能超过 64 个字符")
	public String getBizFileId() {
		return bizFileId;
	}

	public void setBizFileId(String bizFileId) {
		this.bizFileId = bizFileId;
	}
	
}