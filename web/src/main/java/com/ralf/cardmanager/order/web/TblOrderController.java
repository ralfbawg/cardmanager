/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * tbl_orderController
 * @author ralfchen
 * @version 2019-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/order/tblOrder")
public class TblOrderController extends BaseController {

	@Autowired
	private TblOrderService tblOrderService;
	
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
	public String form(TblOrder tblOrder, Model model) {
		model.addAttribute("tblOrder", tblOrder);
		return "cardmanager/order/tblOrderForm";
	}

	/**
	 * 保存tbl_order
	 */
	@RequiresPermissions("order:tblOrder:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TblOrder tblOrder) {
		tblOrderService.save(tblOrder);
		return renderResult(Global.TRUE, text("保存tbl_order成功！"));
	}
	
	/**
	 * 删除tbl_order
	 */
	@RequiresPermissions("order:tblOrder:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TblOrder tblOrder) {
		tblOrderService.delete(tblOrder);
		return renderResult(Global.TRUE, text("删除tbl_order成功！"));
	}
	
}