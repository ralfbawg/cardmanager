/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 卡信息DAO接口
 *
 * @author ralfchen
 * @version 2019-08-30
 */
@MyBatisDao
public interface TblCardInfoDao extends CrudDao<TblCardInfo> {

    @Update({"update tbl_card_info set card_amount=card_amount+#{amount},last_charge_on=NOW() where id =#{id}"})
    int charge(String id, Long amount);

    @Select({ "<script>select sum(card_spend_amount) from tbl_card_info where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    Long getClearAmount(@Param("ids") List<String> ids);

    @Update({ "<script>update  tbl_card_info set card_amount=1 where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    Long batchRefund(@Param("ids") List<String> ids);

    @Update({"update tbl_card_info set card_amount=1 where id =#{id}"})
    Long refund(@Param("id") String id);
}