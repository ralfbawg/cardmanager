/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tbl.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.tbl.entity.TblOrder;
import com.ralf.cardmanager.tbl.dao.TblOrderDao;

/**
 * tbl_orderService
 * @author ralfchen
 * @version 2019-08-18
 */
@Service
@Transactional(readOnly=true)
public class TblOrderService extends CrudService<TblOrderDao, TblOrder> {
	
	/**
	 * 获取单条数据
	 * @param tblOrder
	 * @return
	 */
	@Override
	public TblOrder get(TblOrder tblOrder) {
		return super.get(tblOrder);
	}
	
	/**
	 * 查询分页数据
	 * @param tblOrder 查询条件
	 * @param tblOrder.page 分页对象
	 * @return
	 */
	@Override
	public Page<TblOrder> findPage(TblOrder tblOrder) {
		return super.findPage(tblOrder);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tblOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TblOrder tblOrder) {
		super.save(tblOrder);
	}
	
	/**
	 * 更新状态
	 * @param tblOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TblOrder tblOrder) {
		super.updateStatus(tblOrder);
	}
	
	/**
	 * 删除数据
	 * @param tblOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TblOrder tblOrder) {
		super.delete(tblOrder);
	}
	
}