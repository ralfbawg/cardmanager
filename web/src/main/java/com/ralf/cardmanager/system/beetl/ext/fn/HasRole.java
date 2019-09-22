package com.ralf.cardmanager.system.beetl.ext.fn;

import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.system.CommonService;
import org.beetl.core.Context;
import org.beetl.core.Function;

public class HasRole implements Function {
    public HasRole() {
    }
    @Override
    public Object call(Object[] objects, Context context) {
        return SpringUtils.getBean(CommonService.class).isAdminOrSecMgr();
    }
}
