/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ralf.cardmanager.interceptor;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.io.ResourceUtils;
import com.jeesite.common.lang.ByteUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.lang.TimeUtils;
import com.jeesite.common.service.BaseService;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.sys.utils.LogUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.system.CommonService;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.val;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 日志拦截器
 *
 * @author ThinkGem
 * @version 2018-08-11
 */
public class SiteSwitchInterceptor extends BaseService implements HandlerInterceptor {

    public static final String message = "系统维护中，请稍后再说或者联系管理员";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String switcher = (String) CacheUtils.get(CommonService.SITE_SWITCH_CACHE, CommonService.SITE_SWITCH_CACHE_KEY);
        if (StringUtils.isEmpty(switcher) || switcher.equals("0") || switcher.equals("off")) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(message);
            return false;
        }
        return true;
    }


}
