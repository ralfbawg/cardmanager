package com.ralf.cardmanager.system;

import com.jeesite.common.cache.CacheUtils;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
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
    @Autowired
    TblBizParamService bizParamService;

    public  Long getAutoChargeAmount() {
        val bizParam  = new TblBizParam();
        bizParam.setKey("AutoChargeAmount");
        val list = bizParamService.findList(bizParam);
        if (list!=null&&list.size()>0){
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

    public void updateCardCache(TblCardInfo t) {
        CacheUtils.put(CARD_CACHE, t.getCardId(), t);
    }

    public TblCardInfo getCardinfoByCardId(String cardId){
        try {
            val result = CacheUtils.get(CARD_CACHE, cardId);
            if (result==null){
                val cardInfo = new TblCardInfo();
                cardInfo.setCardId(cardId);
                val cardInfoList = tblCardInfoService.findList(cardInfo);
                if (cardInfoList!=null&&cardInfoList.size()>0){
                    CacheUtils.put(CARD_CACHE,cardInfoList.get(0).getCardId(),cardInfoList.get(0));
                }
            }else{
                return (TblCardInfo)result;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
