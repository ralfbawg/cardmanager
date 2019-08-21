/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardmanager.web;

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
import com.ralf.cardmanager.cardmanager.entity.TblCompanyInfo;
import com.ralf.cardmanager.cardmanager.service.TblCompanyInfoService;

/**
 * CompanyInfoController
 * @author ralfchen
 * @version 2019-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cardmanager/tblCompanyInfo")
public class TblCompanyInfoController extends BaseController {

	@Autowired
	private TblCompanyInfoService tblCompanyInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TblCompanyInfo get(String id, boolean isNewRecord) {
		return tblCompanyInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("cardmanager:tblCompanyInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TblCompanyInfo tblCompanyInfo, Model model) {
		model.addAttribute("tblCompanyInfo", tblCompanyInfo);
		return "cardmanager/cardmanager/tblCompanyInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("cardmanager:tblCompanyInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TblCompanyInfo> listData(TblCompanyInfo tblCompanyInfo, HttpServletRequest request, HttpServletResponse response) {
		tblCompanyInfo.setPage(new Page<>(request, response));
		Page<TblCompanyInfo> page = tblCompanyInfoService.findPage(tblCompanyInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("cardmanager:tblCompanyInfo:view")
	@RequestMapping(value = "form")
	public String form(TblCompanyInfo tblCompanyInfo, Model model) {
		model.addAttribute("tblCompanyInfo", tblCompanyInfo);
		return "cardmanager/cardmanager/tblCompanyInfoForm";
	}

	/**
	 * 保存company
	 */
	@RequiresPermissions("cardmanager:tblCompanyInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TblCompanyInfo tblCompanyInfo) {
		tblCompanyInfoService.save(tblCompanyInfo);
		return renderResult(Global.TRUE, text("保存company成功！"));
	}
	
	/**
	 * 删除company
	 */
	@RequiresPermissions("cardmanager:tblCompanyInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TblCompanyInfo tblCompanyInfo) {
		tblCompanyInfoService.delete(tblCompanyInfo);
		return renderResult(Global.TRUE, text("删除company成功！"));
	}
	
}