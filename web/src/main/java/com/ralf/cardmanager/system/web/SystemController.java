package com.ralf.cardmanager.system.web;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("system")
@RestController
public class SystemController extends BaseController {
    @Value("${cm.token}")
    public String SWITCH_TOKEN;

    @Autowired
    TblBizParamService tblBizParamService;

    @Autowired
    UserService userService;

    @Autowired
    TblBudgetService budgetService;

    @Autowired
    TblCardInfoService cardInfoService;

    public void getToken() {

    }

    @RequestMapping("/siteSwitch")
    @ResponseBody
    public String siteSwitch(@RequestParam("token") String token, @RequestParam(required = false) String status) {
        if (token.equals(SWITCH_TOKEN)) {
            val param = new TblBizParam();
            param.setKey("SiteSwitch");
            val list = tblBizParamService.findList(param);
            if (list != null && list.size() > 0) {
                val value = list.get(0);
                if (!StringUtils.isEmpty(status)) {
                    value.setValue(status);
                } else {
                    value.setValue(value.getValue().equalsIgnoreCase("on") ? "off" : "on");
                }
                tblBizParamService.update(value);
            }
        } else {
            return renderResult("false", "错误错误！");
        }
        return ".......有问题你";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(String usercode) {
        return UserUtils.get(usercode);
    }

    @RequestMapping("/getBudget")
    @ResponseBody
    public TblBudget getBudget(@RequestParam(value = "budgetId", required = false) String budgetId) {
        TblBudget budget = null;
        if (StringUtils.isEmpty(budgetId)) {
            val budgetQuery = new TblBudget();
            budgetQuery.setOwnerUsercode(UserUtils.getUser().getUserCode());
            val list = budgetService.findList(budgetQuery);
            if (list != null && list.size() > 0) {
                budget = list.get(0);
            }
        } else {
            budget = budgetService.get(budgetId);
        }
        return budget != null ? budget : null;
    }

    @RequestMapping("/getBudgetCardCount")
    @ResponseBody
    public Long getBudgetCardCount(String budgetId) {
        val cardinfo = new TblCardInfo();
        cardinfo.setBudgetId(budgetId);
        return cardInfoService.findCount(cardinfo);
    }

    @RequestMapping("/getBudgetByUsercode")
    @ResponseBody
    public TblBudget getBudgetByUsercode(String usercode) {
        return budgetService.findBudgetCacheByUsercode(usercode);
    }
}
