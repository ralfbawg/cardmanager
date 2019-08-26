/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.DictType;
import com.jeesite.modules.sys.service.DictDataService;
import com.jeesite.modules.sys.service.DictTypeService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.service.support.DictDataServiceSupport;
import com.ralf.cardmanager.spider.task.divvypay.operation.CreateCardByBudget;
import lombok.val;
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
import java.util.List;

/**
 * 订单Controller
 *
 * @author ralfchen
 * @version 2019-08-25
 */
@Controller
@RequestMapping(value = "${adminPath}/order/tblOrder")
public class TblOrderController extends BaseController {

    @Autowired
    private TblOrderService tblOrderService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private CreateCardByBudget createCardByBudget;


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
        Page<TblOrder> page = tblOrderService.findPage(tblOrder);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("order:tblOrder:view")
    @RequestMapping(value = "form")
    public String form(TblOrder tblOrder, Model model, HttpServletRequest request) {
        model.addAttribute("tblOrder", tblOrder);
        return "cardmanager/order/tblOrderForm";
    }

    /**
     * 保存订单
     */
    @RequiresPermissions("order:tblOrder:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated TblOrder tblOrder, HttpServletRequest request) {
        val queryOrder = new TblOrder();
        queryOrder.setType_eq("create");
        Long createOrderCount = tblOrderService.findPage(queryOrder).getCount();
        if (createOrderCount <= 0 && tblOrder.getType() != "create") {
            return renderResult(Global.FALSE, text("请先提交创建订单！"));
        }
        if (createOrderCount == 1 && tblOrder.getType() == "create") {
            return renderResult(Global.FALSE, text("只能提交一条创建订单！"));
        }
        String usercode = (String) request.getSession().getAttribute("userCode");
        tblOrder.setCreateDate(new Date());
        tblOrder.setSubmitUsercode(usercode);
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
        if (tblOrder.getPayStatus() != "01") {//编辑中
            return renderResult(Global.FALSE, text("订单已经提交，不能删除！"));
        }
        tblOrderService.delete(tblOrder);
        return renderResult(Global.TRUE, text("删除订单成功！"));
    }

    /**
     * 审核订单
     *
     * @param tblOrder
     * @param model
     * @return
     */
    @RequiresPermissions({"order:tblOrder:audit"})
    @RequestMapping({"audit"})
    public String auditForm(TblOrder tblOrder, Model model) {
        this.tblOrderService.save(tblOrder);
        return this.renderResult(Global.TRUE, text("审核订单成功！"));
    }
}