/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.cardinfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.DeleteCard;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;

/**
 * 卡信息Controller
 *
 * @author ralfchen
 * @version 2019-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/cardinfo/tblCardInfo")
@Slf4j
public class TblCardInfoController extends BaseController {

    @Autowired
    private TblCardInfoService tblCardInfoService;

    @Autowired
    private TblBudgetService budgetService;

    @Autowired
    private DeleteCard deleteCard;

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
        if (!UserUtils.getUser().isAdmin() && !UserUtils.getUser().isSuperAdmin()) {
            tblCardInfo.setCardOwner(UserUtils.getUser().getUserCode());
        }
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
        val cardQuery = new TblCardInfo();
        cardQuery.setCardOwner(UserUtils.getUser().getUserCode());
        cardQuery.setId(tblCardInfo.getId());
        val cardInfo = tblCardInfoService.get(cardQuery);
        if (cardInfo != null) {
            try {
                val rsp = deleteCard.init(cardInfo.getCardId()).execute();
                if (rsp.getDeleteCardId().equals(cardInfo.getCardId())) {
                    cardInfo.setCardStatus("deleted");
                    tblCardInfoService.update(cardInfo);
                    return renderResult(Global.TRUE, text("删除卡信息成功！"));
                }
                throw new Exception();
            } catch (Exception e) {
                log.error("卡删除失败,cardId:" + tblCardInfo.getId(), e);
                return renderResult(Global.FALSE, text("删除卡信息失败！"));
            }

        } else {
            return renderResult(Global.FALSE, text("这张卡不存在！请联系管理员"));
        }
    }

    /**
     * 单卡充值
     */
    @RequiresPermissions("cardinfo:tblCardInfo:charge")
    @RequestMapping(value = "charge")
    @ResponseBody
    public String charge(@RequestParam("id") String id, @RequestParam("amount") Long amount) {
        val budgetQuery = new TblBudget();
        budgetQuery.setOwnerUsercode(UserUtils.getUser().getUserCode());
        val budget = budgetService.get(budgetQuery);
        if (budget != null) {
            if (budget.getBudgetAmount() < amount) {
                return renderResult(Global.FALSE, text("帐户余额不足，请先充值帐户！"));
            }
            val cardQuery = new TblCardInfo();
            cardQuery.setId(id);
            cardQuery.setBudgetId(budget.getId());
            val cardList = tblCardInfoService.findList(cardQuery);
            if (cardList == null || cardList.size() <= 0) {
                log.error("没找到卡");
                return renderResult(Global.FALSE, text("卡充值失败！请联系管理员"));
            }
            try {
                val card = cardList.get(0);
                if (tblCardInfoService.chargeCard(card, amount)) {
                    return renderResult(Global.TRUE, text("卡充值成功！"));
                } else {
                    return renderResult(Global.FALSE, text("卡充值失败！请联系管理员"));
                }
            } catch (Exception e) {
                log.error("充值失败,id:" + id + ",amount:" + amount, e);
                return renderResult(Global.FALSE, text("卡充值失败！请联系管理员"));
            }

        } else {
            return renderResult(Global.FALSE, text("你没有帐户，是不是有问题！！！！请联系管理员"));
        }

    }

    /**
     * 批量充值
     */
    @RequiresPermissions("cardinfo:tblCardInfo:batchCharge")
    @RequestMapping(value = "batchCharge")
    @ResponseBody
    public String batchCharge(@RequestParam("ids") String[] ids, @RequestParam("amount") Long amount) {
        val budgetQuery = new TblBudget();
        budgetQuery.setOwnerUsercode(UserUtils.getUser().getUserCode());
        val budget = budgetService.get(budgetQuery);
        if (budget != null) {
            if (budget.getBudgetAmount() < (ids.length * amount*100)) {
                return renderResult(Global.FALSE, text("帐户余额不足，请先充值帐户！"));
            }
            try {
                tblCardInfoService.batchChargeCard(ids, budget.getId(), amount);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("充值失败,id:{},amounts:{}" + ids, ids.length * amount);
                return renderResult(Global.FALSE, text("批量卡充值失败！请联系管理员"));
            }
        } else {
            return renderResult(Global.FALSE, text("你没有帐户，是不是有问题！！！！请联系管理员"));
        }
        return renderResult(Global.FALSE, text("未知错误，请联系管理员"));
    }

    /**
     * 批量回收余额
     */
    @RequiresPermissions("cardinfo:tblCardInfo:batchRefund")
    @RequestMapping(value = "batchRefund")
    @ResponseBody
    public String batchRefund(@RequestParam("ids") String[] ids) {
        val budgetQuery = new TblBudget();
        budgetQuery.setOwnerUsercode(UserUtils.getUser().getUserCode());
        val budget = budgetService.get(budgetQuery);

        return renderResult(Global.FALSE, text("未知错误，请联系管理员"));
    }
    /**
     * 批量回收
     */
    @RequiresPermissions("cardinfo:tblCardInfo:batchDelete")
    @RequestMapping(value = "batchDelete")
    @ResponseBody
    public String batchDelete(@RequestParam("ids") String[] ids) {
        val budgetQuery = new TblBudget();
        budgetQuery.setOwnerUsercode(UserUtils.getUser().getUserCode());
        val budget = budgetService.get(budgetQuery);

        return renderResult(Global.FALSE, text("未知错误，请联系管理员"));
    }
}