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
 *
 * @author ralfchen
 * @version 2019-08-30
 */
@MyBatisDao
public interface TblBudgetDao extends CrudDao<TblBudget> {

    @Update({"update tbl_budget set budget_amount=budget_amount+#{amount},total_amount=total_amount+#{amount},unsign_amount=unsign_amount+#{amount}, last_charge_on=NOW() where id =#{id}"})
    public int charge(@Param("id") String id, @Param("amount") long amount);

    @Update({"update tbl_budget set budget_amount=budget_amount-#{amount},assign_amount=assign_amount+#{amount},unsign_amount=unsign_amount-#{amount} where id =#{id}"})
    public int minus(@Param("id") String id, @Param("amount") long amount);

    @Update({"update tbl_budget set budget_amount=budget_amount+#{amount},unsign_amount=unsign_amount+#{amount},  assign_amount=assign_amount-#{amount} where id =#{id}"})
    public int refund(@Param("id") String id, @Param("amount") long amount);
}