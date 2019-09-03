/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardtransaction.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.cardtransaction.entity.TblCardTransaction;

/**
 * 交易流水DAO接口
 * @author ralfchen
 * @version 2019-09-03
 */
@MyBatisDao
public interface TblCardTransactionDao extends CrudDao<TblCardTransaction> {
	
}