/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package main.java.com.ralf.cardmanager.cardinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;

/**
 * 卡信息DAO接口
 * @author ralfchen
 * @version 2019-08-30
 */
@MyBatisDao
public interface TblCardInfoDao extends CrudDao<TblCardInfo> {
	
}