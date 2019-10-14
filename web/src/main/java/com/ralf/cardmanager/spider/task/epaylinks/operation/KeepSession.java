package com.ralf.cardmanager.spider.task.epaylinks.operation;

import com.ralf.cardmanager.spider.task.epaylinks.config.EpaylinksSiteConfig;
import com.ralf.cardmanager.spider.task.epaylinks.operation.base.BaseEpaylinksOperation;
import org.springframework.stereotype.Service;

@Service("eplaylinksKeepSession")
public class KeepSession extends BaseEpaylinksOperation<KeepSessionRsp> {
    public KeepSession(EpaylinksSiteConfig config) {
        super(config);
    }

    @Override
    public KeepSessionRsp persistent(String rsp) {
        return null;
    }
}

