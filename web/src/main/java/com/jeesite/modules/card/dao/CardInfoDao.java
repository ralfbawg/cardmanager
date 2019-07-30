/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.card.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.card.entity.CardInfo;

/**
 * 卡信息表DAO接口
 * @author ralfchen
 * @version 2019-07-22
 */
@MyBatisDao
public interface CardInfoDao extends CrudDao<CardInfo> {
	
}