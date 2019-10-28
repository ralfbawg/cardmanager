package com.ralf.cardmanager.system.beetl;

import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.val;

public class CommonUtil {
    public static Long getBudgetAmount(String id) {
        val budget = new TblBudget();
        budget.setOwnerUsercode(UserUtils.getUser().getUserCode());
        val list = SpringUtils.getBean(TblBudgetService.class).findList(budget);
        if (list != null && list.size() > 0) {
            return list.get(0).getBudgetAmount();
        }
        return 0l;
    }

    public static Long getPerCardPrice() {
        val BizParam = new TblBizParam();
        BizParam.setKey("PerCardCost");
        return Long.valueOf(SpringUtils.getBean(TblBizParamService.class).findList(BizParam).get(0).getValue());
    }

}
