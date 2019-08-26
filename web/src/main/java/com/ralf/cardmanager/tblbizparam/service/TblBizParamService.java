/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tblbizparam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.dao.TblBizParamDao;

/**
 * 业务参数表Service
 * @author ralfchen
 * @version 2019-08-26
 */
@Service
@Transactional(readOnly=true)
public class TblBizParamService extends CrudService<TblBizParamDao, TblBizParam> {
	
	/**
	 * 获取单条数据
	 * @param tblBizParam
	 * @return
	 */
	@Override
	public TblBizParam get(TblBizParam tblBizParam) {
		return super.get(tblBizParam);
	}
	
	/**
	 * 查询分页数据
	 * @param tblBizParam 查询条件
	 * @param tblBizParam.page 分页对象
	 * @return
	 */
	@Override
	public Page<TblBizParam> findPage(TblBizParam tblBizParam) {
		return super.findPage(tblBizParam);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tblBizParam
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TblBizParam tblBizParam) {
		super.save(tblBizParam);
	}
	
	/**
	 * 更新状态
	 * @param tblBizParam
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TblBizParam tblBizParam) {
		super.updateStatus(tblBizParam);
	}
	
	/**
	 * 删除数据
	 * @param tblBizParam
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TblBizParam tblBizParam) {
		super.delete(tblBizParam);
	}
	
}