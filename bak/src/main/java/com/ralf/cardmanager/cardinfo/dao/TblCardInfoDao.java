/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;

/**
 * cardInfoDAO接口
 * @author ralfchen
 * @version 2019-08-18
 */
@MyBatisDao
public interface TblCardInfoDao extends CrudDao<TblCardInfo> {
	
}