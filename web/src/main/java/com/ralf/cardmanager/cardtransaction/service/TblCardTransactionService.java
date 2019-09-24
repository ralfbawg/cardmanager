/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardtransaction.service;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.cardtransaction.entity.TblCardTransaction;
import com.ralf.cardmanager.cardtransaction.dao.TblCardTransactionDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 交易流水Service
 *
 * @author ralfchen
 * @version 2019-09-07
 */
@Service
@Transactional(readOnly = true)
public class TblCardTransactionService extends CrudService<TblCardTransactionDao, TblCardTransaction> {
    @Autowired
    TblBudgetService tblBudgetService;

    @Autowired
    TblCardInfoService cardInfoService;

    @Autowired
    TblCardTransactionDao dao;

    /**
     * 获取单条数据
     *
     * @param tblCardTransaction
     * @return
     */
    @Override
    public TblCardTransaction get(TblCardTransaction tblCardTransaction) {
        return super.get(tblCardTransaction);
    }

    /**
     * 查询分页数据
     *
     * @param tblCardTransaction      查询条件
     * @param tblCardTransaction.page 分页对象
     * @return
     */
    @Override
    public Page<TblCardTransaction> findPage(TblCardTransaction tblCardTransaction) {
        return super.findPage(tblCardTransaction);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param tblCardTransaction
     */
    @Override
    @Transactional(readOnly = false)
    public void save(TblCardTransaction tblCardTransaction) {
        super.save(tblCardTransaction);
        // 保存上传图片
        FileUploadUtils.saveFileUpload(tblCardTransaction.getId(), "tblCardTransaction_image");
    }

    /**
     * 更新状态
     *
     * @param tblCardTransaction
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(TblCardTransaction tblCardTransaction) {
        super.updateStatus(tblCardTransaction);
    }

    /**
     * 删除数据
     *
     * @param tblCardTransaction
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(TblCardTransaction tblCardTransaction) {
        super.delete(tblCardTransaction);
    }


    /**
     * 账户已用金额
     */
    @Transactional(readOnly = false)
    public Long getBudgetSpendAmount(String budgetId) {
        val card = new TblCardInfo();
        card.setBudgetId(budgetId);
        val list = cardInfoService.findList(card);
        if (list != null && list.size() > 0) {
            val ids = list.stream().map(t -> t.getCardId()).collect(Collectors.joining(","));
            return dao.getBudgetAmount(ids, "COMPLETE");
        }
        return 0l;
    }

    /**
     * 卡已用金额
     */
    @Transactional(readOnly = false)
    public Long getCardSpendAmount(String cardId) {
        return dao.getBudgetAmount(cardId, "COMPLETE");
    }
}