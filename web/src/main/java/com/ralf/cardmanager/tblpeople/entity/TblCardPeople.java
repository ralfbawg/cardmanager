/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tblpeople.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 持卡人管理Entity
 * @author ralfchen
 * @version 2019-09-03
 */
@Table(name="tbl_card_people", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="people_id", attrName="peopleId", label="持卡人id"),
		@Column(name="display_name", attrName="displayName", label="显示名", queryType=QueryType.LIKE),
		@Column(name="first_name", attrName="firstName", label="firstName", queryType=QueryType.LIKE),
		@Column(name="last_name", attrName="lastName", label="lastName", queryType=QueryType.LIKE),
		@Column(name="role", attrName="role", label="角色"),
		@Column(name="status", attrName="status", label="status", isUpdate=false),
	}, orderBy="a.id DESC"
)
public class TblCardPeople extends DataEntity<TblCardPeople> {
	
	private static final long serialVersionUID = 1L;
	private String peopleId;		// 持卡人id
	private String displayName;		// 显示名
	private String firstName;		// firstName
	private String lastName;		// lastName
	private String role;		// 角色
	
	public TblCardPeople() {
		this(null);
	}

	public TblCardPeople(String id){
		super(id);
	}
	
	@Length(min=0, max=128, message="持卡人id长度不能超过 128 个字符")
	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}
	
	@Length(min=0, max=255, message="显示名长度不能超过 255 个字符")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Length(min=0, max=255, message="firstName长度不能超过 255 个字符")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Length(min=0, max=255, message="lastName长度不能超过 255 个字符")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Length(min=0, max=255, message="角色长度不能超过 255 个字符")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}