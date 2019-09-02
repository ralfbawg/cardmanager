/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardtransaction.web;

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
import com.ralf.cardmanager.cardtransaction.entity.TblCardTransaction;
import com.ralf.cardmanager.cardtransaction.service.TblCardTransactionService;

/**
 * tbl_card_transactionController
 * @author ralfchen
 * @version 2019-09-02
 */
@Controller
@RequestMapping(value = "${adminPath}/cardtransaction/tblCardTransaction")
public class TblCardTransactionController extends BaseController {

	@Autowired
	private TblCardTransactionService tblCardTransactionService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TblCardTransaction get(String id, boolean isNewRecord) {
		return tblCardTransactionService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("cardtransaction:tblCardTransaction:view")
	@RequestMapping(value = {"list", ""})
	public String list(TblCardTransaction tblCardTransaction, Model model) {
		model.addAttribute("tblCardTransaction", tblCardTransaction);
		return "cardmanager/cardtransaction/tblCardTransactionList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("cardtransaction:tblCardTransaction:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TblCardTransaction> listData(TblCardTransaction tblCardTransaction, HttpServletRequest request, HttpServletResponse response) {
		tblCardTransaction.setPage(new Page<>(request, response));
		Page<TblCardTransaction> page = tblCardTransactionService.findPage(tblCardTransaction);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("cardtransaction:tblCardTransaction:view")
	@RequestMapping(value = "form")
	public String form(TblCardTransaction tblCardTransaction, Model model) {
		model.addAttribute("tblCardTransaction", tblCardTransaction);
		return "cardmanager/cardtransaction/tblCardTransactionForm";
	}

	/**
	 * 保存tbl_card_transaction
	 */
	@RequiresPermissions("cardtransaction:tblCardTransaction:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TblCardTransaction tblCardTransaction) {
		tblCardTransactionService.save(tblCardTransaction);
		return renderResult(Global.TRUE, text("保存tbl_card_transaction成功！"));
	}
	
	/**
	 * 删除tbl_card_transaction
	 */
	@RequiresPermissions("cardtransaction:tblCardTransaction:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TblCardTransaction tblCardTransaction) {
		tblCardTransactionService.delete(tblCardTransaction);
		return renderResult(Global.TRUE, text("删除tbl_card_transaction成功！"));
	}
	
}