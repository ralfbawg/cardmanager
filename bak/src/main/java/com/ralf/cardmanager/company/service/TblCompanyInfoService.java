/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.company.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.ralf.cardmanager.company.entity.TblCompanyInfo;
import com.ralf.cardmanager.company.dao.TblCompanyInfoDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 公司信息Service
 * @author ralfchen
 * @version 2019-08-27
 */
@Service
@Transactional(readOnly=true)
public class TblCompanyInfoService extends CrudService<TblCompanyInfoDao, TblCompanyInfo> {
	
	/**
	 * 获取单条数据
	 * @param tblCompanyInfo
	 * @return
	 */
	@Override
	public TblCompanyInfo get(TblCompanyInfo tblCompanyInfo) {
		return super.get(tblCompanyInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param tblCompanyInfo 查询条件
	 * @param tblCompanyInfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<TblCompanyInfo> findPage(TblCompanyInfo tblCompanyInfo) {
		return super.findPage(tblCompanyInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tblCompanyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TblCompanyInfo tblCompanyInfo) {
		super.save(tblCompanyInfo);
		// 保存上传附件
		FileUploadUtils.saveFileUpload(tblCompanyInfo.getId(), "tblCompanyInfo_file");
	}
	
	/**
	 * 更新状态
	 * @param tblCompanyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TblCompanyInfo tblCompanyInfo) {
		super.updateStatus(tblCompanyInfo);
	}
	
	/**
	 * 删除数据
	 * @param tblCompanyInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TblCompanyInfo tblCompanyInfo) {
		super.delete(tblCompanyInfo);
	}
	
}