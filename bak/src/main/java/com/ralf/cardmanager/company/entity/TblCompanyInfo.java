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
 * 公司信息Entity
 * @author ralfchen
 * @version 2019-08-27
 */
@Table(name="tbl_company_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="中文名", queryType=QueryType.LIKE),
		@Column(name="name_en", attrName="nameEn", label="英文名", queryType=QueryType.LIKE),
		@Column(name="manager_usercode", attrName="managerUsercode", label="管理员"),
		@Column(name="manager_first_name", attrName="managerFirstName", label="管理员firstName", queryType=QueryType.LIKE),
		@Column(name="manager_last_name", attrName="managerLastName", label="管理员lastName", queryType=QueryType.LIKE),
		@Column(name="biz_file_id", attrName="bizFileId", label="文件"),
	}, orderBy="a.id DESC"
)
public class TblCompanyInfo extends DataEntity<TblCompanyInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 中文名
	private String nameEn;		// 英文名
	private String managerUsercode;		// 管理员
	private String managerFirstName;		// 管理员firstName
	private String managerLastName;		// 管理员lastName
	private String bizFileId;		// 文件
	
	public TblCompanyInfo() {
		this(null);
	}

	public TblCompanyInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="中文名长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="英文名长度不能超过 255 个字符")
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	@Length(min=0, max=255, message="管理员长度不能超过 255 个字符")
	public String getManagerUsercode() {
		return managerUsercode;
	}

	public void setManagerUsercode(String managerUsercode) {
		this.managerUsercode = managerUsercode;
	}
	
	@Length(min=0, max=255, message="管理员firstName长度不能超过 255 个字符")
	public String getManagerFirstName() {
		return managerFirstName;
	}

	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}
	
	@Length(min=0, max=255, message="管理员lastName长度不能超过 255 个字符")
	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}
	
	@Length(min=0, max=64, message="文件长度不能超过 64 个字符")
	public String getBizFileId() {
		return bizFileId;
	}

	public void setBizFileId(String bizFileId) {
		this.bizFileId = bizFileId;
	}
	
}