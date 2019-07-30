/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.card.web;

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
import com.jeesite.modules.card.entity.CardInfo;
import com.jeesite.modules.card.service.CardInfoService;

/**
 * 卡信息表Controller
 * @author ralfchen
 * @version 2019-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/card/cardInfo")
public class CardInfoController extends BaseController {

	@Autowired
	private CardInfoService cardInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CardInfo get(Long id, boolean isNewRecord) {
		return cardInfoService.get(String.valueOf(id), isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("card:cardInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CardInfo cardInfo, Model model) {
		model.addAttribute("cardInfo", cardInfo);
		return "modules/card/cardInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("card:cardInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CardInfo> listData(CardInfo cardInfo, HttpServletRequest request, HttpServletResponse response) {
		cardInfo.setPage(new Page<>(request, response));
		Page<CardInfo> page = cardInfoService.findPage(cardInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("card:cardInfo:view")
	@RequestMapping(value = "form")
	public String form(CardInfo cardInfo, Model model) {
		model.addAttribute("cardInfo", cardInfo);
		return "modules/card/cardInfoForm";
	}

	/**
	 * 保存卡信息表
	 */
	@RequiresPermissions("card:cardInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated CardInfo cardInfo) {
		cardInfoService.save(cardInfo);
		return renderResult(Global.TRUE, text("保存卡信息表成功！"));
	}
	
	/**
	 * 删除卡信息表
	 */
	@RequiresPermissions("card:cardInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CardInfo cardInfo) {
		cardInfoService.delete(cardInfo);
		return renderResult(Global.TRUE, text("删除卡信息表成功！"));
	}
	
}