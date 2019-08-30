/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tblbizparam.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;

/**
 * 业务参数表DAO接口
 * @author ralfchen
 * @version 2019-08-26
 */
@MyBatisDao
public interface TblBizParamDao extends CrudDao<TblBizParam> {
	
}