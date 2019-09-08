package com.ralf.cardmanager.system;

import com.jeesite.common.cache.CacheUtils;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
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

    @Autowired
    TblCardInfoService tblCardInfoService;

    public void updateCardCache() {
        val cardQuery = new TblCardInfo();
        val list = tblCardInfoService.findList(cardQuery);
        list.forEach(t -> {
            updateCardCache(t);
        });

    }

    public void updateCardCache(TblCardInfo t) {
        CacheUtils.put(CARD_CACHE, t.getCardId(), t);
    }

}
