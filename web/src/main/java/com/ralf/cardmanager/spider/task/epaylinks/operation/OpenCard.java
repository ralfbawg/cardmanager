package com.ralf.cardmanager.spider.task.epaylinks.operation;

import com.ralf.cardmanager.spider.task.epaylinks.config.EpaylinksSiteConfig;
import com.ralf.cardmanager.spider.task.epaylinks.operation.base.BaseEpaylinksOperation;

public class OpenCard extends BaseEpaylinksOperation {
    public OpenCard(EpaylinksSiteConfig config) {
        super(config);
        this.url = host + "/space/ecardManage.do?method=activaSBMerResult&sideBarNavId=05";

    }

    protected void init(Long cardNo,Double initfee) throws Exception {
        body="cardNumber=%s&cardOrgan=%s&initFee=%%s";
        bodyParams = new String[]{
               String.valueOf(cardNo),"00","15.00"
        };
    }
}
