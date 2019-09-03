/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.tblpeople.web;

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
import com.ralf.cardmanager.tblpeople.entity.TblCardPeople;
import com.ralf.cardmanager.tblpeople.service.TblCardPeopleService;

/**
 * 持卡人管理Controller
 * @author ralfchen
 * @version 2019-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/tblpeople/tblCardPeople")
public class TblCardPeopleController extends BaseController {

	@Autowired
	private TblCardPeopleService tblCardPeopleService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TblCardPeople get(String id, boolean isNewRecord) {
		return tblCardPeopleService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tblpeople:tblCardPeople:view")
	@RequestMapping(value = {"list", ""})
	public String list(TblCardPeople tblCardPeople, Model model) {
		model.addAttribute("tblCardPeople", tblCardPeople);
		return "cardmanager/tblpeople/tblCardPeopleList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tblpeople:tblCardPeople:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TblCardPeople> listData(TblCardPeople tblCardPeople, HttpServletRequest request, HttpServletResponse response) {
		tblCardPeople.setPage(new Page<>(request, response));
		Page<TblCardPeople> page = tblCardPeopleService.findPage(tblCardPeople);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tblpeople:tblCardPeople:view")
	@RequestMapping(value = "form")
	public String form(TblCardPeople tblCardPeople, Model model) {
		model.addAttribute("tblCardPeople", tblCardPeople);
		return "cardmanager/tblpeople/tblCardPeopleForm";
	}

	/**
	 * 保存持卡人
	 */
	@RequiresPermissions("tblpeople:tblCardPeople:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TblCardPeople tblCardPeople) {
		tblCardPeopleService.save(tblCardPeople);
		return renderResult(Global.TRUE, text("保存持卡人成功！"));
	}
	
	/**
	 * 删除持卡人
	 */
	@RequiresPermissions("tblpeople:tblCardPeople:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TblCardPeople tblCardPeople) {
		tblCardPeopleService.delete(tblCardPeople);
		return renderResult(Global.TRUE, text("删除持卡人成功！"));
	}
	
}