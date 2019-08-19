/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tbl.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.tbl.entity.TblOrder;

/**
 * tbl_orderDAO接口
 * @author ralfchen
 * @version 2019-08-18
 */
@MyBatisDao
public interface TblOrderDao extends CrudDao<TblOrder> {
	
}