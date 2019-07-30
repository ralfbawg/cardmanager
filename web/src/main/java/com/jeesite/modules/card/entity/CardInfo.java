/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.card.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 卡信息表Entity
 * @author ralfchen
 * @version 2019-07-22
 */
@Table(name="card_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="card_name", attrName="cardName", label="card_name", queryType=QueryType.LIKE),
		@Column(name="card_num", attrName="cardNum", label="card_num"),
		@Column(name="create_time", attrName="createTime", label="create_time"),
		@Column(name="owner_user_id", attrName="ownerUserId", label="owner_user_id"),
		@Column(name="update_time", attrName="updateTime", label="update_time"),
	}, orderBy="a.id DESC"
)
public class CardInfo extends DataEntity<CardInfo> {
	
	private static final long serialVersionUID = 1L;
	private String cardName;		// card_name
	private Long cardNum;		// card_num
	private Date createTime;		// create_time
	private Long ownerUserId;		// owner_user_id
	private Date updateTime;		// update_time
	
	public CardInfo() {
		this(null);
	}

	public CardInfo(String id){
		super(id);
	}
	
	@NotBlank(message="card_name不能为空")
	@Length(min=0, max=64, message="card_name长度不能超过 64 个字符")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	@NotNull(message="card_num不能为空")
	public Long getCardNum() {
		return cardNum;
	}

	public void setCardNum(Long cardNum) {
		this.cardNum = cardNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Long getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}