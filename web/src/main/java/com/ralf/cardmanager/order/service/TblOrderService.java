/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.order.entity.TblOrder;
import com.ralf.cardmanager.order.dao.TblOrderDao;
import com.ralf.cardmanager.order.entity.TblOrderDetail;
import com.ralf.cardmanager.order.dao.TblOrderDetailDao;

/**
 * 订单Service
 * @author ralfchen
 * @version 2019-08-25
 */
@Service
@Transactional(readOnly=true)
public class TblOrderService extends CrudService<TblOrderDao, TblOrder> {
	
	@Autowired
	private TblOrderDetailDao tblOrderDetailDao;
	
	/**
	 * 获取单条数据
	 * @param tblOrder
	 * @return
	 */
	@Override
	public TblOrder get(TblOrder tblOrder) {
		TblOrder entity = super.get(tblOrder);
		if (entity != null){
			TblOrderDetail tblOrderDetail = new TblOrderDetail(entity);
			tblOrderDetail.setStatus(TblOrderDetail.STATUS_NORMAL);
			entity.setTblOrderDetailList(tblOrderDetailDao.findList(tblOrderDetail));
		}
		return entity;
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
		// 保存 TblOrder子表
		for (TblOrderDetail tblOrderDetail : tblOrder.getTblOrderDetailList()){
			if (!TblOrderDetail.STATUS_DELETE.equals(tblOrderDetail.getStatus())){
				tblOrderDetail.setOrderId(tblOrder);
				if (tblOrderDetail.getIsNewRecord()){
					tblOrderDetailDao.insert(tblOrderDetail);
				}else{
					tblOrderDetailDao.update(tblOrderDetail);
				}
			}else{
				tblOrderDetailDao.delete(tblOrderDetail);
			}
		}
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
		TblOrderDetail tblOrderDetail = new TblOrderDetail();
		tblOrderDetail.setOrderId(tblOrder);
		tblOrderDetailDao.deleteByEntity(tblOrderDetail);
	}
	
}