package com.ralf.cardmanager.system.web;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.common.web.BaseController;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import static com.ralf.cardmanager.system.CommonService.SITE_SWITCH_CACHE;
import static com.ralf.cardmanager.system.CommonService.SITE_SWITCH_CACHE_KEY;

@RequestMapping("system")
@RestController
public class SystemController extends BaseController {
    @Value("${cm.token}")
    public String SWITCH_TOKEN;

    @Autowired
    TblBizParamService tblBizParamService;

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
}
