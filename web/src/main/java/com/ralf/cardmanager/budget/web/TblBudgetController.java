/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.system.CommonService;
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
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.service.TblBudgetService;

/**
 * 帐户信息Controller
 * @author ralfchen
 * @version 2019-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/budget/tblBudget")
public class TblBudgetController extends BaseController {

	@Autowired
	private TblBudgetService tblBudgetService;

	@Autowired
	private CommonService commonService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TblBudget get(String id, boolean isNewRecord) {
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
		val user = UserUtils.getUser();
		if(!commonService.isAdminOrSecMgr()){
			tblBudget.setOwnerUsercode(user.getUserCode());
		}
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
	 * 保存帐户信息
	 */
	@RequiresPermissions("budget:tblBudget:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TblBudget tblBudget) {
		tblBudgetService.save(tblBudget);
		return renderResult(Global.TRUE, text("保存帐户信息成功！"));
	}
	
	/**
	 * 删除帐户信息
	 */
	@RequiresPermissions("budget:tblBudget:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TblBudget tblBudget) {
		tblBudgetService.delete(tblBudget);
		return renderResult(Global.TRUE, text("删除帐户信息成功！"));
	}
	
}