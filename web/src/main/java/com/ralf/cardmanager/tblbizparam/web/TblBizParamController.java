/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tblbizparam.web;

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
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;

/**
 * 业务参数表Controller
 * @author ralfchen
 * @version 2019-08-26
 */
@Controller
@RequestMapping(value = "${adminPath}/tblbizparam/tblBizParam")
public class TblBizParamController extends BaseController {

	@Autowired
	private TblBizParamService tblBizParamService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TblBizParam get(String id, boolean isNewRecord) {
		return tblBizParamService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tblbizparam:tblBizParam:view")
	@RequestMapping(value = {"list", ""})
	public String list(TblBizParam tblBizParam, Model model) {
		model.addAttribute("tblBizParam", tblBizParam);
		return "cardmanager/tblbizparam/tblBizParamList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tblbizparam:tblBizParam:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TblBizParam> listData(TblBizParam tblBizParam, HttpServletRequest request, HttpServletResponse response) {
		tblBizParam.setPage(new Page<>(request, response));
			Page<TblBizParam> page = tblBizParamService.findPage(tblBizParam);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tblbizparam:tblBizParam:view")
	@RequestMapping(value = "form")
	public String form(TblBizParam tblBizParam, Model model) {
		model.addAttribute("tblBizParam", tblBizParam);
		return "cardmanager/tblbizparam/tblBizParamForm";
	}

	/**
	 * 保存业务参数表
	 */
	@RequiresPermissions("tblbizparam:tblBizParam:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TblBizParam tblBizParam) {
		tblBizParamService.save(tblBizParam);
		return renderResult(Global.TRUE, text("保存业务参数表成功！"));
	}
	
	/**
	 * 删除业务参数表
	 */
	@RequiresPermissions("tblbizparam:tblBizParam:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TblBizParam tblBizParam) {
		tblBizParamService.delete(tblBizParam);
		return renderResult(Global.TRUE, text("删除业务参数表成功！"));
	}
	
}