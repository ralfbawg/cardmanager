/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.budget.entity.TblCardInfo;

/**
 * tbl_budgetDAO接口
 * @author ralfchen
 * @version 2019-08-18
 */
@MyBatisDao
public interface TblCardInfoDao extends CrudDao<TblCardInfo> {
	
}