/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.card.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.card.entity.CardInfo;
import com.jeesite.modules.card.dao.CardInfoDao;

/**
 * 卡信息表Service
 * @author ralfchen
 * @version 2019-07-22
 */
@Service
@Transactional(readOnly=true)
public class CardInfoService extends CrudService<CardInfoDao, CardInfo> {
	
	/**
	 * 获取单条数据
	 * @param cardInfo
	 * @return
	 */
	@Override
	public CardInfo get(CardInfo cardInfo) {
		return super.get(cardInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param cardInfo 查询条件
	 * @param cardInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<CardInfo> findPage(CardInfo cardInfo) {
		return super.findPage(cardInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param cardInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CardInfo cardInfo) {
		super.save(cardInfo);
	}
	
	/**
	 * 更新状态
	 * @param cardInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CardInfo cardInfo) {
		super.updateStatus(cardInfo);
	}
	
	/**
	 * 删除数据
	 * @param cardInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CardInfo cardInfo) {
		super.delete(cardInfo);
	}
	
}