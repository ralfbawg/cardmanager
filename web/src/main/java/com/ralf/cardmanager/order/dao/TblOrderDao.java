/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.order.entity.TblOrder;

/**
 * 订单表DAO接口
 * @author ralfchen
 * @version 2019-08-30
 */
@MyBatisDao
public interface TblOrderDao extends CrudDao<TblOrder> {
	
}