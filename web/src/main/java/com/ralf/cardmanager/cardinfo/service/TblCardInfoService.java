/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardinfo.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.cardinfo.dao.TblCardInfoDao;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.UpdateVirtualCard;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡信息Service
 *
 * @author ralfchen
 * @version 2019-08-30
 */
@Service
@Transactional(readOnly = true)
public class TblCardInfoService extends CrudService<TblCardInfoDao, TblCardInfo> {
    @Autowired
    private TblBudgetService budgetService;
    @Autowired
    private UpdateVirtualCard updateVirtualCard;

    /**
     * 获取单条数据
     *
     * @param tblCardInfo
     * @return
     */
    @Override
    public TblCardInfo get(TblCardInfo tblCardInfo) {
        return super.get(tblCardInfo);
    }

    /**
     * 查询分页数据
     *
     * @param tblCardInfo 查询条件
     * @return
     */
    @Override
    public Page<TblCardInfo> findPage(TblCardInfo tblCardInfo) {
        return super.findPage(tblCardInfo);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param tblCardInfo
     */
    @Override
    @Transactional(readOnly = false)
    public void save(TblCardInfo tblCardInfo) {
        super.save(tblCardInfo);
    }

    /**
     * 更新状态
     *
     * @param tblCardInfo
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(TblCardInfo tblCardInfo) {
        super.updateStatus(tblCardInfo);
    }

    /**
     * 删除数据
     *
     * @param tblCardInfo
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(TblCardInfo tblCardInfo) {
        super.delete(tblCardInfo);
    }

    /**
     * 卡充值
     *
     * @param cardId
     * @param amount
     */
    @Transactional()
    public void charge(String cardId, Long amount) {
        dao.charge(cardId, amount);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean chargeCard(TblCardInfo card, Long amount) throws Exception {
        budgetService.minus(card.getBudgetId(), amount);
        SpringUtils.getBean(TblCardInfoService.class).charge(card.getId(), amount);
        val rsp = updateVirtualCard.init(amount, card.getCardId(), card.getCardName()).execute();
        return rsp.getUpdateCardId().equals(card.getCardId());
    }
}