/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.system.SpType;
import com.ralf.cardmanager.system.exception.BudgetNotEnoughException;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import javafx.scene.control.Pagination;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.order.entity.TblOrder;
import com.ralf.cardmanager.order.dao.TblOrderDao;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.ralf.cardmanager.order.entity.TblOrderDetail;
import com.ralf.cardmanager.order.dao.TblOrderDetailDao;

import static com.ralf.cardmanager.order.web.TblOrderController.*;

/**
 * 订单表Service
 *
 * @author ralfchen
 * @version 2019-08-30
 */
@Service
@Transactional(readOnly = true)
public class TblOrderService extends CrudService<TblOrderDao, TblOrder> {
    public static final String TYPE_CREATE = "create";
    public static final String TYPE_CREATE_CARD = "createCard";
    public static final String TYPE_CHARGE = "charge";
    public static final String TYPE_BATCH_CREATE_CARD = "batchCreateCard";


    @Autowired
    private TblOrderDetailDao tblOrderDetailDao;

    @Autowired
    private TblBizParamService bizParamService;

    @Autowired
    private TblBudgetService budgetService;
    @Autowired
    private TblCardInfoService cardInfoService;


    /**
     * 获取单条数据
     *
     * @param tblOrder
     * @return
     */
    @Override
    public TblOrder get(TblOrder tblOrder) {
        TblOrder entity = super.get(tblOrder);
        if (entity != null) {
            TblOrderDetail tblOrderDetail = new TblOrderDetail(entity);
            tblOrderDetail.setStatus(TblOrderDetail.STATUS_NORMAL);
            entity.setTblOrderDetailList(tblOrderDetailDao.findList(tblOrderDetail));
        }
        return entity;
    }

    /**
     * 查询分页数据
     *
     * @param tblOrder 查询条件
     * @return
     */
    @Override
    public Page<TblOrder> findPage(TblOrder tblOrder) {
        return super.findPage(tblOrder);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param tblOrder
     */
    @Override
    @Transactional(readOnly = false)
    public void save(TblOrder tblOrder) {
        super.save(tblOrder);
        // 保存上传附件
        FileUploadUtils.saveFileUpload(tblOrder.getId(), "tblOrder_file");
        // 保存 TblOrder子表
        for (TblOrderDetail tblOrderDetail : tblOrder.getTblOrderDetailList()) {
            if (!TblOrderDetail.STATUS_DELETE.equals(tblOrderDetail.getStatus())) {
                tblOrderDetail.setOrderId(tblOrder);
                if (tblOrderDetail.getIsNewRecord()) {
                    tblOrderDetailDao.insert(tblOrderDetail);
                } else {
                    tblOrderDetailDao.update(tblOrderDetail);
                }
            } else {
                tblOrderDetailDao.delete(tblOrderDetail);
            }
        }
    }

    /**
     * 更新状态
     *
     * @param tblOrder
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(TblOrder tblOrder) {
        super.updateStatus(tblOrder);
    }

    /**
     * 删除数据
     *
     * @param tblOrder
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(TblOrder tblOrder) {
        super.delete(tblOrder);
        TblOrderDetail tblOrderDetail = new TblOrderDetail();
        tblOrderDetail.setOrderId(tblOrder);
        tblOrderDetailDao.deleteByEntity(tblOrderDetail);
    }

    @Transactional
    public boolean AuditAndProcess(TblOrder tblOrder) {
        val order = get(tblOrder.getId());
        if (!order.getPayStatus().equalsIgnoreCase(STATUS_WAIT_PAY)) {
            return false;
        }
        tblOrder.setPayStatus(STATUS_AUDIT_PASS_WAIT_PRO);
        tblOrder.setAuditUsercode(UserUtils.getUser().getUserCode());
        tblOrder.setAuditTime(new Date());
        this.update(tblOrder);
        SpringUtils.getBean(TblOrderService.class).Process(tblOrder);
        return true;
    }

    @Transactional
    public void Process(TblOrder tblOrder) {
        val budget = new TblBudget();
        budget.setOwnerUsercode(tblOrder.getSubmitUsercode());
        val budgetList = budgetService.findList(budget);
        try {
            switch (tblOrder.getType()) {
                case TYPE_CREATE:
                    logger.info("开始创建账户,budgetId={}", budget.getOwnerUsercode());
                    SpringUtils.getBean(TblOrderService.class).createBudgetAndCard(tblOrder, budget);
                    break;
                case TYPE_CREATE_CARD:
                    if (budgetList.size() == 1) {
                        logger.info("开始建卡,budgetId={}", budget.getId());
                        SpringUtils.getBean(TblOrderService.class).createCard(tblOrder, budgetList.get(0).getId());
                    }

                    break;
                case TYPE_CHARGE:
                    if (budgetList.size() == 1) {
                        logger.info("开始充值,budgetId={}", budgetList.get(0).getId());
                        int n = budgetService.charge(budgetList.get(0).getId(), tblOrder.getChargeAmount());
                        logger.info("充值完成,n={},budgetId={}",n,budgetList.get(0).getId());
                    }
                    break;

                case TYPE_BATCH_CREATE_CARD:
                    if (budgetList.size() == 1) {
                        logger.info("开始批量建卡,budgetId={}", budget.getId());
                        SpringUtils.getBean(TblOrderService.class).batchCreateCard(tblOrder, budgetList.get(0));
                        logger.info("结束批量建卡,budgetId={}", budget.getId());
                    }
                    break;
            }
            tblOrder.setPayStatus(STATUS_AUDIT_PASS_PRO_SUCCESS);
            SpringUtils.getBean(TblOrderService.class).update(tblOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建帐户
     *
     * @param tblOrder
     * @param budget
     * @throws Exception
     */
    @Transactional
    public void createBudgetAndCard(TblOrder tblOrder, TblBudget budget) throws Exception {
        budget.setIsNewRecord(true);
        budget.setCreateUserCode(tblOrder.getAuditUsercode());
        budget.setCardServiceProvider(SpType.DIVVY.toString());
        budget.setName(UserUtils.get(tblOrder.getSubmitUsercode()).getUserName() + "的帐户");
        budget.setOwnerUsercode(tblOrder.getSubmitUsercode());
        Long AssignAmount = (tblOrder.getTblOrderDetailList() != null && tblOrder.getTblOrderDetailList().size() > 0) ? tblOrder.getTblOrderDetailList().stream().collect(Collectors.summingLong(TblOrderDetail::getLimitAmount)) : 0l;
        budget.setBudgetAmount(0l);
        budget.setSpendAmount(0l);
        budget.setAssignAmount(AssignAmount);
        budget.setUnsignAmount(0l);
        budget.setTotalAmount(AssignAmount);
        budget.setLastChargeOn(new Date());
        budget.setCreateTime(new Date());
        budgetService.save(budget);
        logger.debug("帐户创建完毕,budget({})", budget);
//        createCard(tblOrder, budget.getId());
    }

    /**
     * 创建卡
     *
     * @param tblOrder
     * @param budgetId
     */
    @Transactional
    public void createCard(TblOrder tblOrder, String budgetId) throws Exception {
        val budget = budgetService.get(budgetId);


        if (tblOrder.getTblOrderDetailList().size() > 0) {
            tblOrder.getTblOrderDetailList().forEach(t -> {
                val card = new TblCardInfo();
                card.setIsNewRecord(true);
                card.setBudgetId(budgetId);
                card.setCardOwner(tblOrder.getSubmitUsercode());
                card.setCardName(t.getCardName());
                card.setCardLimit(t.getLimitAmount());
                card.setCardAmount(t.getLimitAmount());
                card.setCardStatus("tobecreate");
                cardInfoService.insert(card);
            });
            try {
                budgetService.justMinus(budget.getId(),tblOrder.getTblOrderDetailList().stream().mapToLong(TblOrderDetail::getLimitAmount).sum());
            } catch (BudgetNotEnoughException e) {
                logger.debug("帐户余额不足budgetId={},orderId={}",budget.getId(),tblOrder.getId());
                throw new Exception("帐户余额不足");
            }

        }
    }

    /**
     * 批量创建卡
     *
     * @param tblOrder
     * @param budget
     */
    @Transactional
    public void batchCreateCard(TblOrder tblOrder, TblBudget budget) throws Exception {
        for (int i = 0; i < tblOrder.getBatchCardNum(); i++) {
            val card = new TblCardInfo();
            card.setIsNewRecord(true);
            card.setBudgetId(budget.getId());
            card.setCardOwner(tblOrder.getSubmitUsercode());
            card.setCardName(budget.getName() + "," + String.valueOf(System.currentTimeMillis() + i));
            card.setCardLimit(tblOrder.getBatchCardAmount());
            card.setCardAmount(tblOrder.getBatchCardAmount());
            card.setCardStatus("tobecreate");
            cardInfoService.insert(card);
        }
        try {
            budgetService.justMinus(budget.getId(),tblOrder.getBatchCardAmount()*tblOrder.getBatchCardNum());
        } catch (BudgetNotEnoughException e) {
            logger.debug("帐户余额不足budgetId={},orderId={}",budget.getId(),tblOrder.getId());
            throw new Exception("帐户余额不足");
        }
        logger.info("批量制卡完成，制卡数量{}",tblOrder.getBatchCardNum());
    }


}