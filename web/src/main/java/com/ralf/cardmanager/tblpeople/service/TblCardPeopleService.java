/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tblpeople.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.tblpeople.entity.TblCardPeople;
import com.ralf.cardmanager.tblpeople.dao.TblCardPeopleDao;

/**
 * 持卡人管理Service
 * @author ralfchen
 * @version 2019-09-03
 */
@Service
@Transactional(readOnly=true)
public class TblCardPeopleService extends CrudService<TblCardPeopleDao, TblCardPeople> {
	
	/**
	 * 获取单条数据
	 * @param tblCardPeople
	 * @return
	 */
	@Override
	public TblCardPeople get(TblCardPeople tblCardPeople) {
		return super.get(tblCardPeople);
	}
	
	/**
	 * 查询分页数据
	 * @param tblCardPeople 查询条件
	 * @param tblCardPeople.page 分页对象
	 * @return
	 */
	@Override
	public Page<TblCardPeople> findPage(TblCardPeople tblCardPeople) {
		return super.findPage(tblCardPeople);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tblCardPeople
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TblCardPeople tblCardPeople) {
		super.save(tblCardPeople);
	}
	
	/**
	 * 更新状态
	 * @param tblCardPeople
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TblCardPeople tblCardPeople) {
		super.updateStatus(tblCardPeople);
	}
	
	/**
	 * 删除数据
	 * @param tblCardPeople
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TblCardPeople tblCardPeople) {
		super.delete(tblCardPeople);
	}
	
}