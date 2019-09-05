package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope("prototype")
public class CreateCardStep3GetPanUrl extends BaseDivvyOperation<CreateCardStep3GetPanUrlRsp> {
    @Autowired
    GetCardinfo getCardinfo;

    protected CreateCardStep3GetPanUrl init(String cardToken) throws Exception {
        super.init();
        bodyParams = new String[]{
                cardToken
        };
        return this;
    }

    public CreateCardStep3GetPanUrl(DivvyPaySiteConfig config) {
        super(config);
        defaultHeader.put("referer", "https://app.divvy.co/cards");
//        body="{\"operationName\":\"FetchPanUrl\",\"variables\":{\"input\":{\"cardToken\":\"001.R.20190725123000679671\",\"clientMutationId\":\"0\"}},\"query\":\"mutation FetchPanUrl($input: GetPanUrlInput!) {\\n  getPanUrl(input: $input) {\\n    url\\n    __typename\\n  }\\n}\\n\"}";
        body = "{\"operationName\":\"FetchPanUrl\",\"variables\":{\"input\":{\"cardToken\":\"%s\",\"clientMutationId\":\"0\"}},\"query\":\"mutation FetchPanUrl($input: GetPanUrlInput!) {\\n  getPanUrl(input: $input) {\\n    url\\n    __typename\\n  }\\n}\\n\"}";

    }

    //返回 {"data":{"getPanUrl":{"url":"https://app.divvy.co/de/rest/pan?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoiUkllQzlVMkxqZ0R6MWlrSkU3a2NiY2M2b3NaZTNrRkZHQUNCczY4cEVPT3EwazBkeXBDWDdCbXM3WnJaeFVwQXZOR3BQS1JKeTdSK3RBbDBFNytlZG9xL3JyNmcwSk1kd29NdW1iT3N1V2RPSGhxVC9UV092dmdZRDVpbSIsImV4cCI6MTU2NTExNTQ4M30.FESZCTtA5N7MQz14Yqrnfvhq8XGMWtidDHhBvZCI44Q","__typename":"GetPanUrlPayload"}}}
    //通过url获取卡号
    //返回 {"expirationDate":"202408","cvv":"378","cardNumber":"5532320925504902"}
    @Override
    public CreateCardStep3GetPanUrlRsp persistent(String rsp) throws Exception {
        JsonObject jsonObject = new JsonParser().parse(rsp).getAsJsonObject();
        String panUrl = jsonObject.get("data").getAsJsonObject().get("getPanUrl").getAsJsonObject().get("url").getAsString();
        return new CreateCardStep3GetPanUrlRsp(getCardinfo.init(panUrl).execute(), panUrl);
    }


}
