/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import org.apache.ibatis.annotations.Update;

/**
 * 卡信息DAO接口
 * @author ralfchen
 * @version 2019-08-30
 */
@MyBatisDao
public interface TblCardInfoDao extends CrudDao<TblCardInfo> {

    @Update({"update tbl_card_info set card_amount=card_amount+#{amount},last_charge_on=NOW() where id =#{id}"})
    void charge(String id, Long amount);
}