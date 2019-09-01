/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.system.SpType;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
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

import static com.ralf.cardmanager.order.web.TblOrderController.STATUS_AUDIT_PASS_WAIT_PRO;
import static com.ralf.cardmanager.order.web.TblOrderController.STATUS_WAIT_PAY;

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


    @Autowired
    private TblOrderDetailDao tblOrderDetailDao;

    @Autowired
    private TblBizParamService bizParamService;


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
     * @param tblOrder      查询条件
     * @param tblOrder.page 分页对象
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
        tblOrder.setSubmitUsercode(UserUtils.getUser().getUserCode());
        tblOrder.setSubmitTime(new Date());

        this.update(tblOrder);
        new Thread(() -> Process(tblOrder)).start();
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void Process(TblOrder tblOrder) {
        switch (tblOrder.getType()) {
            case TYPE_CREATE:
                val budget = new TblBudget();
                budget.setIsNewRecord(true);
                budget.setCreateUserCode(UserUtils.getUser().getUserCode());
                budget.setCardServiceProvider(SpType.DIVVY.toString());
                budget.setName(UserUtils.get(tblOrder.getSubmitUsercode()).getUserName());
                budget.setOwnerUsercode(UserUtils.get(tblOrder.getSubmitUsercode()).getUserName());
                val param = new TblBizParam();
//                param.
//                        bizParamService.get()
                break;
            case TYPE_CREATE_CARD:
                break;
            case TYPE_CHARGE:
                break;
        }
    }

}