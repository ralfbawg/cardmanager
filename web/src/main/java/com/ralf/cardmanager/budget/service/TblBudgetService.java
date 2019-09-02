/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.dao.TblBudgetDao;

/**
 * 帐户信息Service
 * @author ralfchen
 * @version 2019-08-30
 */
@Service
@Transactional(readOnly=true)
public class TblBudgetService extends CrudService<TblBudgetDao, TblBudget> {
	@Autowired
	private TblBudgetDao dao;
	
	/**
	 * 获取单条数据
	 * @param tblBudget
	 * @return
	 */
	@Override
	public TblBudget get(TblBudget tblBudget) {
		return super.get(tblBudget);
	}
	
	/**
	 * 查询分页数据
	 * @param tblBudget 查询条件
	 * @param tblBudget.page 分页对象
	 * @return
	 */
	@Override
	public Page<TblBudget> findPage(TblBudget tblBudget) {
		return super.findPage(tblBudget);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tblBudget
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TblBudget tblBudget) {
		super.save(tblBudget);
	}
	
	/**
	 * 更新状态
	 * @param tblBudget
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TblBudget tblBudget) {
		super.updateStatus(tblBudget);
	}
	
	/**
	 * 删除数据
	 * @param tblBudget
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TblBudget tblBudget) {
		super.delete(tblBudget);
	}

	@Transactional
	public int charge(String id,long amount){
		return dao.charge(id,amount);
	}
	
}