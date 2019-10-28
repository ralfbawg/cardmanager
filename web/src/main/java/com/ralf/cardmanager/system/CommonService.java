package com.ralf.cardmanager.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonService {
    public static final String CARD_CACHE = "CardCache";
    public static final String SITE_SWITCH_CACHE = "SiteCache";
    public static final String SITE_SWITCH_CACHE_KEY = "SiteSwitch";
    public static final String CARD_USER_CACHE = "CardCache";
    public static final String UNFREEZE_CARD_COUNT = "CardCache";

    public static String secAdminStr = "secAdmin";

    @Value("${cm.constant.constant}")
    public void setSecAdminStr(String secAdminStr) {
        secAdminStr = secAdminStr;
    }

    @Autowired
    TblCardInfoService tblCardInfoService;
    @Autowired
    TblBizParamService bizParamService;

    public static final ObjectMapper mapper = new ObjectMapper();

    public Long getAutoChargeAmount() {
        val bizParam = new TblBizParam();
        bizParam.setKey("AutoChargeAmount");
        val list = bizParamService.findList(bizParam);
        if (list != null && list.size() > 0) {
            return Long.valueOf(list.get(0).getValue());
        }
        return 1l;
    }

    public void updateCardCache() {
        val cardQuery = new TblCardInfo();
        val list = tblCardInfoService.findList(cardQuery);
        list.forEach(t -> {
            updateCardCache(t);
        });

    }

    public void updateUnfreezeCardCount(String cardId, Integer count) {
        String result = CacheUtils.get(UNFREEZE_CARD_COUNT, cardId);
        if (result == null || Integer.valueOf(result) <= 0) {
            CacheUtils.put(UNFREEZE_CARD_COUNT, cardId, String.valueOf(count));
        } else {
            CacheUtils.put(UNFREEZE_CARD_COUNT, cardId, String.valueOf(Integer.valueOf(result) + count));
        }
    }

    public Integer getUnfreezeCardCount(String cardId) {
        String result = CacheUtils.get(UNFREEZE_CARD_COUNT, cardId);
        if (result == null || Integer.valueOf(result) <= 0) {
            return 0;
        } else {
            return Integer.valueOf(result);
        }
    }


    public void updateCardCache(TblCardInfo t) {
        try {
            CacheUtils.put(CARD_CACHE, t.getCardId(), mapper.writeValueAsString(t));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public TblCardInfo getCardinfoByCardId(String cardId) {
        try {
            String resultStr = CacheUtils.get(CARD_CACHE, cardId);
            if (StringUtils.isEmpty(resultStr)) {
                val cardInfo = new TblCardInfo();
                cardInfo.setCardId(cardId);
                val cardInfoList = tblCardInfoService.findList(cardInfo);
                if (cardInfoList != null && cardInfoList.size() > 0) {
                    updateCardCache(cardInfoList.get(0));
                }
                return cardInfoList.get(0);
            } else {
                return mapper.readValue(resultStr, TblCardInfo.class);
            }
        } catch (Exception e) {
            log.error("mapper error", e);
            return null;
        }
    }


    public boolean isAdminOrSecMgr() {
        val user = UserUtils.getUser();
        if (user.isAdmin() || user.getMgrType().equals("2") || isSecAdmin(user)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSecAdmin(User user) {
        for (Role role : user.getRoleList()) {
            if (role.getRoleCode().indexOf(secAdminStr) != -1) {
                return true;
            }
        }
        return false;
    }
}
