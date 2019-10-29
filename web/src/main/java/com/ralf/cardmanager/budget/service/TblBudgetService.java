/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.service;

import java.util.List;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.lang.StringUtils;
import com.ralf.cardmanager.system.exception.BudgetNotEnoughException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.dao.TblBudgetDao;

/**
 * 帐户信息Service
 *
 * @author ralfchen
 * @version 2019-08-30
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class TblBudgetService extends CrudService<TblBudgetDao, TblBudget> {
    public static final String BUDGET_CACHE = "Budget_Cache";


    @Autowired
    private TblBudgetDao dao;

    /**
     * 获取单条数据
     *
     * @param tblBudget
     * @return
     */
    @Override
    public TblBudget get(TblBudget tblBudget) {
        return super.get(tblBudget);
    }

    /**
     * 查询分页数据
     *
     * @param tblBudget      查询条件
     * @param tblBudget.page 分页对象
     * @return
     */
    @Override
    public Page<TblBudget> findPage(TblBudget tblBudget) {
        return super.findPage(tblBudget);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param tblBudget
     */
    @Override
    @Transactional(readOnly = false)
    public void save(TblBudget tblBudget) {
        super.save(tblBudget);
    }

    /**
     * 更新状态
     *
     * @param tblBudget
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(TblBudget tblBudget) {
        super.updateStatus(tblBudget);
    }

    /**
     * 删除数据
     *
     * @param tblBudget
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(TblBudget tblBudget) {
        super.delete(tblBudget);
    }

    @Transactional
    public int charge(String id, long amount) {
        return dao.charge(id, amount);
    }

    @Transactional
    public int minus(String id, long amount) throws BudgetNotEnoughException {
        val budget = get(id);
        if (budget.getBudgetAmount() < amount) {
            log.error("账户{}现有余额{}不足以扣减{}", id, budget.getBudgetAmount(), amount);
            throw new BudgetNotEnoughException("账户现有余额不足以扣减");
        }
        return dao.minus(id, amount);
    }

    @Transactional
    public int justMinus(String id, long amount) throws BudgetNotEnoughException {
        val budget = get(id);
        if (budget.getBudgetAmount() < amount) {
            log.error("账户{}现有余额{}不足以扣减{}", id, budget.getBudgetAmount(), amount);
            throw new BudgetNotEnoughException("账户现有余额不足以扣减");
        }
        return dao.justMinus(id, amount);
    }

    public int refund(String budgetId, long refundAmount) {
        return dao.refund(budgetId, refundAmount);
    }

    public TblBudget findBudgetIdCacheByUsercode(String usercode) {
        TblBudget budget = CacheUtils.get(BUDGET_CACHE, usercode);
        if (budget == null) {
            val budgetQuery = new TblBudget();
            val list = dao.findList(budgetQuery);
            list.parallelStream().forEach(t -> {
                CacheUtils.put(BUDGET_CACHE, usercode, t);
            });
        }
        return CacheUtils.get(BUDGET_CACHE, usercode);
    }

}