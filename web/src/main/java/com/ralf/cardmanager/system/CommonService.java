package com.ralf.cardmanager.system;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonService {
    public static final String CARD_CACHE = "CardCache";
    public static final String SITE_SWITCH_CACHE = "SiteCache";
    public static final String SITE_SWITCH_CACHE_KEY = "SiteSwitch";
    public static final String CARD_USER_CACHE = "CardCache";
    public static final String UNFREEZE_CARD_COUNT = "CardCache";

    @Autowired
    TblCardInfoService tblCardInfoService;
    @Autowired
    TblBizParamService bizParamService;

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
        CacheUtils.put(CARD_CACHE, t.getCardId(), t,60);
    }

    public TblCardInfo getCardinfoByCardId(String cardId) {
        try {
            val result = CacheUtils.get(CARD_CACHE, cardId);
            if (result == null) {
                val cardInfo = new TblCardInfo();
                cardInfo.setCardId(cardId);
                val cardInfoList = tblCardInfoService.findList(cardInfo);
                if (cardInfoList != null && cardInfoList.size() > 0) {
                    CacheUtils.put(CARD_CACHE, cardInfoList.get(0).getCardId(), cardInfoList.get(0));
                }
            } else {
                return (TblCardInfo) result;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


    public boolean isAdminOrSecMgr() {
        val user = UserUtils.getUser();
        if (user.isAdmin() || user.getMgrType() == "2") {
            return true;
        } else {
            return false;
        }
    }

}
