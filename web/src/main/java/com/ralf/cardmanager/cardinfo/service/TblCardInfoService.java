/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.dao.TblCardInfoDao;

/**
 * 卡信息Service
 * @author ralfchen
 * @version 2019-08-30
 */
@Service
@Transactional(readOnly=true)
public class TblCardInfoService extends CrudService<TblCardInfoDao, TblCardInfo> {
	
	/**
	 * 获取单条数据
	 * @param tblCardInfo
	 * @return
	 */
	@Override
	public TblCardInfo get(TblCardInfo tblCardInfo) {
		return super.get(tblCardInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param tblCardInfo 查询条件
	 * @param tblCardInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<TblCardInfo> findPage(TblCardInfo tblCardInfo) {
		return super.findPage(tblCardInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tblCardInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TblCardInfo tblCardInfo) {
		super.save(tblCardInfo);
	}
	
	/**
	 * 更新状态
	 * @param tblCardInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TblCardInfo tblCardInfo) {
		super.updateStatus(tblCardInfo);
	}
	
	/**
	 * 删除数据
	 * @param tblCardInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TblCardInfo tblCardInfo) {
		super.delete(tblCardInfo);
	}
	
}