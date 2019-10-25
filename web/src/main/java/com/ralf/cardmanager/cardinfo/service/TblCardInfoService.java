/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardinfo.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.cardinfo.dao.TblCardInfoDao;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.DeleteCard;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.GetVirtualCardDetailsInfo;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.GetVirtualCardEditInfo;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.UpdateVirtualCard;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 卡信息Service
 *
 * @author ralfchen
 * @version 2019-08-30
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class TblCardInfoService extends CrudService<TblCardInfoDao, TblCardInfo> {
    @Autowired
    private TblBudgetService budgetService;
    @Autowired
    private UpdateVirtualCard updateVirtualCard;
    @Autowired
    private GetVirtualCardEditInfo getVirtualCardEditInfo;
    @Autowired
    private GetVirtualCardDetailsInfo getVirtualCardDetailsInfo;
    @Autowired
    private DeleteCard deleteCard;


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
    public boolean chargeCard(TblCardInfo card, Long amount, boolean budgetMinusFlag) throws Exception {
        if (budgetMinusFlag) {
            val result = budgetService.minus(card.getBudgetId(), amount);
            if (result <= 0) {
                throw new Exception();
            }
        }
        SpringUtils.getBean(TblCardInfoService.class).charge(card.getId(), amount);
        val rsp2 = getVirtualCardEditInfo.init(card.getCardId()).execute();
        val goalAmount = rsp2.getAmount() + amount;
        val rsp = updateVirtualCard.init(goalAmount, card.getCardId(), card.getCardName()).execute();
        val rsp3 = getVirtualCardEditInfo.init(card.getCardId()).execute();
        if (rsp.getUpdateCardId().equals(card.getCardId()) && rsp3.getAmount() == goalAmount) {
            card.setLastChargeOn(new Date());
            SpringUtils.getBean(TblCardInfoService.class).update(card);
            return true;
        } else {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean batchChargeCard(String[] ids, String budgetId, Long amount) throws Exception {
        var result = true;
        if (budgetService.minus(budgetId, amount * ids.length) <= 0) {
            throw new Exception();
        }
        for (String id : ids) {
            val cardQuery = new TblCardInfo();
            cardQuery.setId(id);
            cardQuery.setBudgetId(budgetId);
            val cardList = findList(cardQuery);
            if (cardList == null || cardList.size() <= 0) {
                log.error("没找到卡");
                return false;
            } else {
                result &= chargeCard(cardList.get(0), amount, false);
            }
        }
        return result;
    }

    @Transactional
    public boolean deleteCard(TblCardInfo cardInfo) {
        try {
            val rsp = deleteCard.init(cardInfo.getCardId()).execute();
            if (rsp.getDeleteCardId().equalsIgnoreCase(cardInfo.getCardId())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Transactional
    public boolean batchDeleteCard(String[] ids) {
        var result = true;
        for (String id : ids) {
            val card = get(id);
            if (card != null) {
                result &= deleteCard(card);
            }

        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean batchRefundCard(String[] ids, String budgetId) throws Exception {
        var result = true;

        for (String id : ids) {
            val cardQuery = new TblCardInfo();
            cardQuery.setId(id);
            cardQuery.setBudgetId(budgetId);
            val cardList = findList(cardQuery);
            if (cardList == null || cardList.size() <= 0) {
                log.error("没找到卡");
                return false;
            } else {
                try {
                    result = result && refundCard(cardList.get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("test");
                }
            }
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean refundCard(TblCardInfo cardInfo) throws Exception {
        val rsp = getVirtualCardEditInfo.init(cardInfo.getCardId()).execute();
        log.error("回收前:卡id={},rsp={}", cardInfo.getCardId(), rsp);
        try {
            var refundAmount = rsp.getAmount() - 1;
            val rsp1 = updateVirtualCard.init(1l, cardInfo.getCardId(), cardInfo.getCardName()).execute();
            log.error("回收中:卡id={},rsp1={}", cardInfo.getCardId(), rsp1);
            if (!StringUtils.isEmpty(rsp1.getUpdateCardId()) && cardInfo.getCardId().equals(rsp1.getUpdateCardId())) {
                budgetService.refund(cardInfo.getBudgetId(), refundAmount);
                dao.refund(cardInfo.getId());
                log.error("回收结束:卡id={},refundAmount={}", cardInfo.getCardId(), refundAmount);
            } else {
                throw new Exception("回收余额失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("卡cardNo{}cardName{}回收余额失败", cardInfo.getCardNo(), cardInfo.getCardName());
            updateVirtualCard.init(rsp.getAmount(), cardInfo.getCardId(), cardInfo.getCardName()).execute();
            throw e;
        }
        return true;
    }

    @Transactional
    public Long getClearAmount(String budgetId) {
        var card = new TblCardInfo();
        card.setBudgetId(budgetId);
        val list = findList(card);
        if (list != null && list.size() > 0) {
            val ids = list.parallelStream().map(t -> t.getId()).collect(Collectors.toList());
            return dao.getClearAmount(ids);
        } else {
            return 0l;
        }

    }

    public List<TblCardInfo> getShouldUpdateInfo(long minutes, long size) {
        return dao.getShouldUpdateInfo(minutes, size);
    }
}