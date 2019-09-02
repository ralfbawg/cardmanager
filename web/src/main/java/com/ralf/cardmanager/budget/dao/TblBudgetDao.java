/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.budget.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.budget.entity.TblBudget;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 帐户信息DAO接口
 * @author ralfchen
 * @version 2019-08-30
 */
@MyBatisDao
public interface TblBudgetDao extends CrudDao<TblBudget> {

    @Update({"update Tbl_budget set budget_amount=budget_amount+#{amount},total_amount=total_amount+#{amount},last_charge_on=NOW() where id =#{id}"})
    public int charge(@Param("id") String id,@Param("amount") long amount);
}