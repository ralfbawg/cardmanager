/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardtransaction.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.cardtransaction.entity.TblCardTransaction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 交易流水DAO接口
 * @author ralfchen
 * @version 2019-09-07
 */
@MyBatisDao
public interface TblCardTransactionDao extends CrudDao<TblCardTransaction> {
    @Select("select sum(amount) from tbl_card_transaction where card_id in (${ids}) and transaction_status=${status}")
    Long getBudgetAmount(@Param("ids") String ids, @Param("status") String complete);

    @Select("select sum(amount) from tbl_card_transaction where card_id = ${cardId} and transaction_status=${status} ")
    Long getCardAmount(@Param("cardId")String cardId,@Param("status") String complete);
}