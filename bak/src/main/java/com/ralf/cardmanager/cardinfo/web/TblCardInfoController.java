/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package main.java.com.ralf.cardmanager.cardinfo.web;

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
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;

/**
 * 卡信息Controller
 * @author ralfchen
 * @version 2019-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/cardinfo/tblCardInfo")
public class TblCardInfoController extends BaseController {

	@Autowired
	private TblCardInfoService tblCardInfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TblCardInfo get(String id, boolean isNewRecord) {
		return tblCardInfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("cardinfo:tblCardInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TblCardInfo tblCardInfo, Model model) {
		model.addAttribute("tblCardInfo", tblCardInfo);
		return "cardmanager/cardinfo/tblCardInfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("cardinfo:tblCardInfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TblCardInfo> listData(TblCardInfo tblCardInfo, HttpServletRequest request, HttpServletResponse response) {
		tblCardInfo.setPage(new Page<>(request, response));
		Page<TblCardInfo> page = tblCardInfoService.findPage(tblCardInfo);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("cardinfo:tblCardInfo:view")
	@RequestMapping(value = "form")
	public String form(TblCardInfo tblCardInfo, Model model) {
		model.addAttribute("tblCardInfo", tblCardInfo);
		return "cardmanager/cardinfo/tblCardInfoForm";
	}

	/**
	 * 保存卡信息
	 */
	@RequiresPermissions("cardinfo:tblCardInfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TblCardInfo tblCardInfo) {
		tblCardInfoService.save(tblCardInfo);
		return renderResult(Global.TRUE, text("保存卡信息成功！"));
	}
	
	/**
	 * 删除卡信息
	 */
	@RequiresPermissions("cardinfo:tblCardInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TblCardInfo tblCardInfo) {
		tblCardInfoService.delete(tblCardInfo);
		return renderResult(Global.TRUE, text("删除卡信息成功！"));
	}
	
}