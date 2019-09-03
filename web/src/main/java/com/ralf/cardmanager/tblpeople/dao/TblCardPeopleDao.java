/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tblpeople.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.tblpeople.entity.TblCardPeople;

/**
 * 持卡人管理DAO接口
 * @author ralfchen
 * @version 2019-09-03
 */
@MyBatisDao
public interface TblCardPeopleDao extends CrudDao<TblCardPeople> {
	
}