package com.ralf.cardmanager.spider.task.epaylinks.operation;

import com.ralf.cardmanager.spider.task.epaylinks.config.EpaylinksSiteConfig;
import com.ralf.cardmanager.spider.task.epaylinks.operation.base.BaseEpaylinksOperation;
import org.springframework.beans.factory.annotation.Autowired;

public class GetTransaction extends BaseEpaylinksOperation {
    @Autowired
    public GetTransaction(EpaylinksSiteConfig config) {
        super(config);
        this.url = host+"/account/record.do?method=qryTradeList";
    }
    public GetTransaction init(String amount) throws Exception{
        super.init(new String[]{
                amount
        });
        return this;
    }

}
