package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class KeepSession extends BaseDivvyOperation<KeepSessionRsp> {
    public KeepSession(DivvyPaySiteConfig config) {
        super(config);
    }

    @Override
    public KeepSessionRsp persistent(String rsp) {
        return null;
    }
}

@Data
@AllArgsConstructor
class KeepSessionRsp extends BaseDivvyOpertionResp {

}