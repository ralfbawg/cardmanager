/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.order.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 订单Entity
 *
 * @author ralfchen
 * @version 2019-08-25
 */
@Table(name = "tbl_order", alias = "a", columns = {
        @Column(name = "id", attrName = "id", label = "id", isPK = true),
        @Column(name = "type", attrName = "type", label = "订单类型"),
        @Column(name = "submit_time", attrName = "submitTime", label = "提交时间", isQuery = false),
        @Column(name = "submit_usercode", attrName = "submitUsercode", label = "提交用户"),
        @Column(name = "audit_usercode", attrName = "auditUsercode", label = "审核用户", isQuery = false),
        @Column(name = "audit_time", attrName = "auditTime", label = "审核时间"),
        @Column(name = "order_amount", attrName = "orderAmount", label = "订单金额"),
        @Column(name = "pay_status", attrName = "payStatus", label = "支付状态", isQuery = false),
        @Column(name = "status", attrName = "status", label = "状态"),
}, orderBy = "a.id DESC"
)
public class TblOrder extends DataEntity<TblOrder> {

    private static final long serialVersionUID = 1L;
    private String type;        // 订单类型
    private Date submitTime;        // 提交时间
    private String submitUsercode;        // 提交用户
    private String auditUsercode;        // 审核用户
    private Date auditTime;        // 审核时间
    private Long orderAmount;        // 订单金额
    private String payStatus;        // 支付状态
    private List<TblOrderDetail> tblOrderDetailList = ListUtils.newArrayList();        // 子表列表

    public TblOrder() {
        this(null);
    }

    public TblOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 32, message = "订单类型长度不能超过 32 个字符")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @Length(min = 0, max = 128, message = "提交用户长度不能超过 128 个字符")
    public String getSubmitUsercode() {
        return submitUsercode;
    }

    public void setSubmitUsercode(String submitUsercode) {
        this.submitUsercode = submitUsercode;
    }

    @Length(min = 0, max = 255, message = "审核用户长度不能超过 255 个字符")
    public String getAuditUsercode() {
        return auditUsercode;
    }

    public void setAuditUsercode(String auditUsercode) {
        this.auditUsercode = auditUsercode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Length(min = 0, max = 2, message = "支付状态长度不能超过 2 个字符")
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public void setPayStatus_eq(String payStatus) {
        sqlMap.getWhere().and("pay_status", QueryType.EQ, payStatus);
    }

    public Date getAuditTime_gte() {
        return sqlMap.getWhere().getValue("audit_time", QueryType.GTE);
    }

    public void setAuditTime_gte(Date auditTime) {
        sqlMap.getWhere().and("audit_time", QueryType.GTE, auditTime);
    }

    public void setType_eq(String type) {
        sqlMap.getWhere().and("type", QueryType.EQ, type);
    }

    public Date getAuditTime_lte() {
        return sqlMap.getWhere().getValue("audit_time", QueryType.LTE);
    }

    public void setAuditTime_lte(Date auditTime) {
        sqlMap.getWhere().and("audit_time", QueryType.LTE, auditTime);
    }

    public List<TblOrderDetail> getTblOrderDetailList() {
        return tblOrderDetailList;
    }

    public void setTblOrderDetailList(List<TblOrderDetail> tblOrderDetailList) {
        this.tblOrderDetailList = tblOrderDetailList;
    }

}