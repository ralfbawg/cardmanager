/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.web;

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
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.service.TblBudgetService;

/**
 * tbl_budgetController
 * @author ralfchen
 * @version 2019-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/budget/tblBudget")
public class TblBudgetController extends BaseController {

	@Autowired
	private TblBudgetService tblBudgetService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TblBudget get(Long id, boolean isNewRecord) {
		return tblBudgetService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("budget:tblBudget:view")
	@RequestMapping(value = {"list", ""})
	public String list(TblBudget tblBudget, Model model) {
		model.addAttribute("tblBudget", tblBudget);
		return "cardmanager/budget/tblBudgetList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("budget:tblBudget:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TblBudget> listData(TblBudget tblBudget, HttpServletRequest request, HttpServletResponse response) {
		tblBudget.setPage(new Page<>(request, response));
		Page<TblBudget> page = tblBudgetService.findPage(tblBudget);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("budget:tblBudget:view")
	@RequestMapping(value = "form")
	public String form(TblBudget tblBudget, Model model) {
		model.addAttribute("tblBudget", tblBudget);
		return "cardmanager/budget/tblBudgetForm";
	}

	/**
	 * 保存budget
	 */
	@RequiresPermissions("budget:tblBudget:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TblBudget tblBudget) {
		tblBudgetService.save(tblBudget);
		return renderResult(Global.TRUE, text("保存budget成功！"));
	}
	
	/**
	 * 停用budget
	 */
	@RequiresPermissions("budget:tblBudget:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(TblBudget tblBudget) {
		tblBudget.setStatus(TblBudget.STATUS_DISABLE);
		tblBudgetService.updateStatus(tblBudget);
		return renderResult(Global.TRUE, text("停用budget成功"));
	}
	
	/**
	 * 启用budget
	 */
	@RequiresPermissions("budget:tblBudget:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(TblBudget tblBudget) {
		tblBudget.setStatus(TblBudget.STATUS_NORMAL);
		tblBudgetService.updateStatus(tblBudget);
		return renderResult(Global.TRUE, text("启用budget成功"));
	}
	
}