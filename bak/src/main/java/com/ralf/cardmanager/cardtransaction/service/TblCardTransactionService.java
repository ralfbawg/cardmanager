/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardtransaction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.cardtransaction.entity.TblCardTransaction;
import com.ralf.cardmanager.cardtransaction.dao.TblCardTransactionDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * tbl_card_transactionService
 * @author ralfchen
 * @version 2019-08-18
 */
@Service
@Transactional(readOnly=true)
public class TblCardTransactionService extends CrudService<TblCardTransactionDao, TblCardTransaction> {
	
	/**
	 * 获取单条数据
	 * @param tblCardTransaction
	 * @return
	 */
	@Override
	public TblCardTransaction get(TblCardTransaction tblCardTransaction) {
		return super.get(tblCardTransaction);
	}
	
	/**
	 * 查询分页数据
	 * @param tblCardTransaction 查询条件
	 * @param tblCardTransaction.page 分页对象
	 * @return
	 */
	@Override
	public Page<TblCardTransaction> findPage(TblCardTransaction tblCardTransaction) {
		return super.findPage(tblCardTransaction);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tblCardTransaction
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TblCardTransaction tblCardTransaction) {
		super.save(tblCardTransaction);
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tblCardTransaction.getId(), "tblCardTransaction_file");
	}
	
	/**
	 * 更新状态
	 * @param tblCardTransaction
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TblCardTransaction tblCardTransaction) {
		super.updateStatus(tblCardTransaction);
	}
	
	/**
	 * 删除数据
	 * @param tblCardTransaction
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TblCardTransaction tblCardTransaction) {
		super.delete(tblCardTransaction);
	}
	
}