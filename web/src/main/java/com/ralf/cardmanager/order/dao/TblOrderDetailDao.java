/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.order.entity.TblOrderDetail;

/**
 * 订单信息DAO接口
 * @author ralfchen
 * @version 2019-09-06
 */
@MyBatisDao
public interface TblOrderDetailDao extends CrudDao<TblOrderDetail> {
	
}