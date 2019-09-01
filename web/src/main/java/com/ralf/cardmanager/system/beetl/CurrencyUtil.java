package com.ralf.cardmanager.system.beetl;

import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.val;

public class CurrencyUtil {


    public static Long TranserUSDToRMB(Long rmb) {
        val bizService = SpringUtils.getBean(TblBizParamService.class);
        val createBizParam = new TblBizParam();
        createBizParam.setKey("USDToRMB");
        val cur = Long.valueOf(bizService.findPage(createBizParam).getList().get(0).getValue());
        return (cur * rmb) / 10000;
    }

    public static Long GetUSDToRMBCurrency() {
        val bizService = SpringUtils.getBean(TblBizParamService.class);
        val createBizParam = new TblBizParam();
        createBizParam.setKey("USDToRMB");
        val list = bizService.findList(createBizParam);
        if (list.size()>0){
            return Long.valueOf(list.get(0).getValue());
        }else{
            return 0l;
        }

    }

}
