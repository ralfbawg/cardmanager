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
import com.ralf.cardmanager.budget.entity.TblCardInfo;
import com.ralf.cardmanager.budget.dao.TblCardInfoDao;

/**
 * tbl_budgetService
 * @author ralfchen
 * @version 2019-08-18
 */
@Service
@Transactional(readOnly=true)
public class TblBudgetService extends CrudService<TblBudgetDao, TblBudget> {
	
	@Autowired
	private TblCardInfoDao tblCardInfoDao;
	
	/**
	 * 获取单条数据
	 * @param tblBudget
	 * @return
	 */
	@Override
	public TblBudget get(TblBudget tblBudget) {
		TblBudget entity = super.get(tblBudget);
		if (entity != null){
			TblCardInfo tblCardInfo = new TblCardInfo(entity);
			tblCardInfo.setStatus(TblCardInfo.STATUS_NORMAL);
			entity.setTblCardInfoList(tblCardInfoDao.findList(tblCardInfo));
		}
		return entity;
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
		// 保存 TblBudget子表
		for (TblCardInfo tblCardInfo : tblBudget.getTblCardInfoList()){
			if (!TblCardInfo.STATUS_DELETE.equals(tblCardInfo.getStatus())){
				tblCardInfo.setBudgetId(tblBudget);
				if (tblCardInfo.getIsNewRecord()){
					tblCardInfoDao.insert(tblCardInfo);
				}else{
					tblCardInfoDao.update(tblCardInfo);
				}
			}else{
				tblCardInfoDao.delete(tblCardInfo);
			}
		}
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
		TblCardInfo tblCardInfo = new TblCardInfo();
		tblCardInfo.setBudgetId(tblBudget);
		tblCardInfoDao.deleteByEntity(tblCardInfo);
	}
	
}