package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @program: cardmanager
 * @description: 卡片解冻
 * @author: Ralf Chen
 * @create: 2019-08-29 11:17
 **/
@Service
@Scope("prototype")
public class UnfreezedCard extends BaseDivvyOperation<UnfreezedCardRsp> {
    public UnfreezedCard init(String cardId) throws Exception {
        super.init(new String[]{cardId});
        return this;
    }

    public UnfreezedCard(DivvyPaySiteConfig config) {
        super(config);
//        body="{\"operationName\":\"UnfreezeCard\",\"variables\":{\"input\":{\"cardId\":\"Q2FyZDozNjMzMDU=\",\"clientMutationId\":\"0\"}},\"query\":\"mutation UnfreezeCard($input: UnfreezeCardInput!) {\\n  unfreezeCard(input: $input) {\\n    card {\\n      id\\n      ...BankCardInfo\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment BankCardInfo on Card {\\n  id\\n  activationStatus\\n  brand\\n  cardType\\n  expirationDate\\n  frozen\\n  lastFour\\n  name\\n  token\\n  __typename\\n}\\n\"}";
        body = "{\"operationName\":\"UnfreezeCard\",\"variables\":{\"input\":{\"cardId\":\"%s\",\"clientMutationId\":\"0\"}},\"query\":\"mutation UnfreezeCard($input: UnfreezeCardInput!) {\\n  unfreezeCard(input: $input) {\\n    card {\\n      id\\n      ...BankCardInfo\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment BankCardInfo on Card {\\n  id\\n  activationStatus\\n  brand\\n  cardType\\n  expirationDate\\n  frozen\\n  lastFour\\n  name\\n  token\\n  __typename\\n}\\n\"}";
        defaultHeader.put("referer", "https://app.divvy.co/cards");
    }

    @Override
    //{"data":{"unfreezeCard":{"card":{"token":"001.R.20190903111249705744","name":"final","lastFour":"7495","id":"Q2FyZDo0MDczODg=","frozen":true,"expirationDate":"09/22","cardType":"BURNER","brand":"mastercard","activationStatus":"ACTIVATED","__typename":"Card"},"__typename":"UnfreezeCardPayload"}}}
    public UnfreezedCardRsp persistent(String rsp) {
        JsonObject jsonObject = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("node").getAsJsonObject();
        String token = jsonObject.get("token").getAsString();
        String cardType = jsonObject.get("cardType").getAsString();
        String expirationDate = jsonObject.get("expirationDate").getAsString();
        String brand = jsonObject.get("brand").getAsString();
        String activationStatus = jsonObject.get("activationStatus").getAsString();
        boolean frozen = jsonObject.get("frozen").getAsBoolean();
        String allocationId = jsonObject.get("userAllocation").getAsJsonObject().get("id").getAsString();
        return new UnfreezedCardRsp(token, cardType, expirationDate, brand, frozen, frozen ? "freezed" : "actived", activationStatus, allocationId);
    }
}
