package com.ralf.cardmanager.spider.task.epaylinks.operation;

import com.ralf.cardmanager.spider.task.epaylinks.config.EpaylinksSiteConfig;
import com.ralf.cardmanager.spider.task.epaylinks.operation.base.BaseEpaylinksOperation;

public class GetTransaction extends BaseEpaylinksOperation {
    public GetTransaction(EpaylinksSiteConfig config) {
        super(config);
        this.url = host+"/account/record.do?method=qryTradeList";
    }


}
