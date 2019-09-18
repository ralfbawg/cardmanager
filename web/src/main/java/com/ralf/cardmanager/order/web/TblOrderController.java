/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.web;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.DictDataService;
import com.jeesite.modules.sys.service.support.DictDataServiceSupport;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.order.entity.TblOrderDetail;
import com.ralf.cardmanager.spider.task.divvypay.operation.company.Budget;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.ralf.cardmanager.order.entity.TblOrder;
import com.ralf.cardmanager.order.service.TblOrderService;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * 订单表Controller
 *
 * @author ralfchen
 * @version 2019-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/order/tblOrder")
public class TblOrderController extends BaseController {
    public static final String STATUS_DRAFT = "01";

    public static final String STATUS_WAIT_PAY = "02";

    public static final String STATUS_AUDIT_PASS_WAIT_PRO = "03";
    public static final String STATUS_AUDIT_FAIL = "04";
    public static final String STATUS_AUDIT_PASS_PRO_SUCCESS = "04";
    public static final String STATUS_AUDIT_PASS_PRO_FAIL = "06";


    @Autowired
    private TblOrderService tblOrderService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private TblBizParamService bizParamService;
    @Autowired
    private TblBudgetService budgetService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public TblOrder get(String id, boolean isNewRecord) {
        return tblOrderService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("order:tblOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(TblOrder tblOrder, Model model) {
        model.addAttribute("tblOrder", tblOrder);
        return "cardmanager/order/tblOrderList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("order:tblOrder:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<TblOrder> listData(TblOrder tblOrder, HttpServletRequest request, HttpServletResponse response) {
        tblOrder.setPage(new Page<>(request, response));
        if (!UserUtils.getUser().isSuperAdmin() && !UserUtils.getUser().isAdmin()) {
            tblOrder.setSubmitUsercode(UserUtils.getUser().getUserCode());
        }
        Page<TblOrder> page = tblOrderService.findPage(tblOrder);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("order:tblOrder:view")
    @RequestMapping(value = "form")
    public String form(TblOrder tblOrder, Model model) {
        model.addAttribute("tblOrder", tblOrder);
        return "cardmanager/order/tblOrderForm";
    }

    /**
     * 保存订单
     */
    @RequiresPermissions("order:tblOrder:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated TblOrder tblOrder) {
        val queryOrder = new TblOrder();
        queryOrder.setType_eq("create");
        queryOrder.setSubmitUsercode(UserUtils.getUser().getUserCode());
        Long createOrderCount = tblOrderService.findCount(queryOrder);
        if (createOrderCount <= 0 && !tblOrder.getType().equalsIgnoreCase("create")) {
            return renderResult(Global.FALSE, text("请先提交创建订单！"));
        }
        if (createOrderCount == 1 && tblOrder.getType().equalsIgnoreCase("create") && StringUtils.isEmpty(tblOrder.getId())) {
            return renderResult(Global.FALSE, text("只能提交一条创建订单！"));
        }
        String usercode = UserUtils.getUser().getUserCode();
        tblOrder.setSubmitTime(new Date());
        tblOrder.setSubmitUsercode(usercode);
        tblOrder.setPayStatus(STATUS_DRAFT);
        long budgetPrice = 0;
        long cardPrice = 0;
        long cardAmountPrice = 0;
        long chargePrice = tblOrder.getChargeAmount() == null ? 0 : tblOrder.getChargeAmount();//充值金额
        val BizParam = new TblBizParam();
        //创建帐户金额
        if (tblOrder.getType().equalsIgnoreCase("create")) {
            BizParam.setKey("CreateBudgetCost");
            BizParam.setPageSize(Integer.MAX_VALUE);
            budgetPrice = Long.valueOf(bizParamService.findList(BizParam).get(0).getValue()) * 100;
        }
        //建卡金额
        BizParam.setKey("PerCardCost");
        val perCardPrice = Long.valueOf(bizParamService.findList(BizParam).get(0).getValue());
        if ((tblOrder.getType().equalsIgnoreCase("createCard") || tblOrder.getType().equalsIgnoreCase("create")) && tblOrder.getTblOrderDetailList() != null && tblOrder.getTblOrderDetailList().size() > 0) {
            cardPrice = tblOrder.getTblOrderDetailList().stream().filter(t -> !t.getStatus().equalsIgnoreCase("1")).count() * perCardPrice;
            cardAmountPrice = tblOrder.getTblOrderDetailList().stream().filter(t -> !t.getStatus().equalsIgnoreCase("1")).collect(Collectors.summingLong(TblOrderDetail::getLimitAmount));//卡内额度金额
        } else {
            if (tblOrder.getType().equalsIgnoreCase("batchCreateCard")) {
                cardPrice = tblOrder.getBatchCardNum() * perCardPrice;
                cardAmountPrice = tblOrder.getBatchCardNum() * tblOrder.getBatchCardAmount();
            }
        }
        val totalPrice = budgetPrice + cardPrice + cardAmountPrice + chargePrice;
        tblOrder.setOrderAmount(totalPrice);
        tblOrderService.save(tblOrder);
        return renderResult(Global.TRUE, text("保存订单成功！"));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("order:tblOrder:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(TblOrder tblOrder) {
        val dict = new DictData();
        dict.setDictType("cm_order_pay_status");
        DictData a = dictDataService.get(dict);
        if (!StringUtils.isEmpty(tblOrder.getPayStatus()) && !tblOrder.getPayStatus().equalsIgnoreCase("01")) {//编辑中
            return renderResult(Global.FALSE, text("订单已经提交，不能删除！"));
        }
        tblOrderService.delete(tblOrder);
        return renderResult(Global.TRUE, text("删除订单成功！"));
    }

    /**
     * 审核订单
     *
     * @param tblOrder
     * @return
     */
    @RequiresPermissions({"order:tblOrder:audit"})
    @RequestMapping({"audit"})
    @ResponseBody
    public String auditForm(TblOrder tblOrder) {
        if (tblOrderService.AuditAndProcess(tblOrder)) {
            return renderResult(Global.TRUE, text("审核订单成功！后台执行中"));
        } else {
            return renderResult(Global.FALSE, text("审核订单失败！"));
        }

    }

    /**
     * 提交订单
     */
    @RequiresPermissions("order:tblOrder:submit")
    @RequestMapping(value = "submit")
    @ResponseBody
    public String submit(TblOrder tblOrder) {
//        if (tblOrder.getType().equalsIgnoreCase(TblOrderService.TYPE_CREATE_CARD)||tblOrder.getType().equalsIgnoreCase(TblOrderService.TYPE_BATCH_CREATE_CARD)){
//            val budget = new TblBudget();
//            budget.setOwnerUsercode(UserUtils.getUser().getUserCode());
//            val list = budgetService.findList(budget);
//            if (list!=null&&list.size()>0){
//                if (list.get(0).getBudgetAmount()<tblOrder.getOrderAmount()) {
//                    return renderResult(Global.FALSE, text(" 账户余额不足，请先充值！"));
//                }
//            }else {
//                return renderResult(Global.FALSE, text(" 账户不存在！"));
//            }
//
//        }
        tblOrder.setPayStatus(STATUS_WAIT_PAY);
        tblOrderService.update(tblOrder);
        return renderResult(Global.TRUE, text(" 提交订单成功！"));
    }

}