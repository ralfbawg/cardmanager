/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.company.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.company.entity.TblCompanyInfo;

/**
 * 公司信息DAO接口
 * @author ralfchen
 * @version 2019-08-27
 */
@MyBatisDao
public interface TblCompanyInfoDao extends CrudDao<TblCompanyInfo> {
	
}