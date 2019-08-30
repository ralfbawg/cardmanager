/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.spiderrequest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.spiderrequest.entity.TblSpiderRequest;
import com.ralf.cardmanager.spiderrequest.dao.TblSpiderRequestDao;

/**
 * tbl_spider_requestService
 * @author ralfchen
 * @version 2019-08-22
 */
@Service
@Transactional(readOnly=true)
public class TblSpiderRequestService extends CrudService<TblSpiderRequestDao, TblSpiderRequest> {
	
	/**
	 * 获取单条数据
	 * @param tblSpiderRequest
	 * @return
	 */
	@Override
	public TblSpiderRequest get(TblSpiderRequest tblSpiderRequest) {
		return super.get(tblSpiderRequest);
	}
	
	/**
	 * 查询分页数据
	 * @param tblSpiderRequest 查询条件
	 * @param tblSpiderRequest.page 分页对象
	 * @return
	 */
	@Override
	public Page<TblSpiderRequest> findPage(TblSpiderRequest tblSpiderRequest) {
		return super.findPage(tblSpiderRequest);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tblSpiderRequest
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TblSpiderRequest tblSpiderRequest) {
		super.save(tblSpiderRequest);
	}
	
	/**
	 * 更新状态
	 * @param tblSpiderRequest
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TblSpiderRequest tblSpiderRequest) {
		super.updateStatus(tblSpiderRequest);
	}
	
	/**
	 * 删除数据
	 * @param tblSpiderRequest
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TblSpiderRequest tblSpiderRequest) {
		super.delete(tblSpiderRequest);
	}
	
}