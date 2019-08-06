package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class CreateCardStep3GetPanUrl extends BaseDivvyOperation {
    public CreateCardStep3GetPanUrl(DivvyPaySiteConfig config) {
        super(config);
//        body="{\"operationName\":\"FetchPanUrl\",\"variables\":{\"input\":{\"cardToken\":\"001.R.20190725123000679671\",\"clientMutationId\":\"0\"}},\"query\":\"mutation FetchPanUrl($input: GetPanUrlInput!) {\\n  getPanUrl(input: $input) {\\n    url\\n    __typename\\n  }\\n}\\n\"}";
        body = "{\"operationName\":\"FetchPanUrl\",\"variables\":{\"input\":{\"cardToken\":\"%s\",\"clientMutationId\":\"0\"}},\"query\":\"mutation FetchPanUrl($input: GetPanUrlInput!) {\\n  getPanUrl(input: $input) {\\n    url\\n    __typename\\n  }\\n}\\n\"}";
        bodyParams = new String[]{
                "cardToken"
        };
    }

    //返回 {"data":{"getPanUrl":{"url":"https://app.divvy.co/de/rest/pan?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoiUkllQzlVMkxqZ0R6MWlrSkU3a2NiY2M2b3NaZTNrRkZHQUNCczY4cEVPT3EwazBkeXBDWDdCbXM3WnJaeFVwQXZOR3BQS1JKeTdSK3RBbDBFNytlZG9xL3JyNmcwSk1kd29NdW1iT3N1V2RPSGhxVC9UV092dmdZRDVpbSIsImV4cCI6MTU2NTExNTQ4M30.FESZCTtA5N7MQz14Yqrnfvhq8XGMWtidDHhBvZCI44Q","__typename":"GetPanUrlPayload"}}}
    //通过url获取卡号
    //返回 {"expirationDate":"202408","cvv":"378","cardNumber":"5532320925504902"}
    @Override
    public void persistent(String rsp) {

    }
}
