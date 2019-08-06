package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope("prototype")
public class CreateCardStep2 extends BaseDivvyOperation {
    @Autowired
    private CreateCardStep3GetPanUrl step3GetPanUrl;

    public CreateCardStep2(DivvyPaySiteConfig config) {
        super(config);

        defaultHeader.put("refere", "https://app.divvy.co/virtual-cards");

//        body = "{\"operationName\":\"GetBankCard\",\"variables\":{\"cardId\":\"Q2FyZDozNjA2ODY=\"},\"query\":\"query GetBankCard($cardId: ID!) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      brand\\n      expirationDate\\n      lastFour\\n      cardType\\n      latestTransaction {\\n        id\\n        merchantName\\n        __typename\\n      }\\n      token\\n      user {\\n        id\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        expiresAt\\n        type\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        body = "{\"operationName\":\"GetBankCard\",\"variables\":{\"cardId\":\"Q2FyZDozNjA2ODY=\"},\"query\":\"query GetBankCard($cardId: ID!) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      brand\\n      expirationDate\\n      lastFour\\n      cardType\\n      latestTransaction {\\n        id\\n        merchantName\\n        __typename\\n      }\\n      token\\n      user {\\n        id\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        expiresAt\\n        type\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        bodyParams = new String[]{"cardId"};
    }

    //{"data":{"node":{"userAllocation":{"type":"RECURRING","id":"VXNlckFsbG9jYXRpb246NDY0MzUw","expiresAt":null,"__typename":"UserAllocation"},"user":{"id":"VXNlcjo1MTE5OA==","__typename":"User"},"token":"001.R.20190806122948189060","latestTransaction":null,"lastFour":"4902","id":"Q2FyZDozNjA2ODY=","expirationDate":"08/22","cardType":"SUBSCRIPTION","brand":"mastercard","__typename":"Card"}}}
    //需要获取token
    @Override
    public void persistent(String rsp) {
        JsonObject jsonObject = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("node").getAsJsonObject();
        String token = jsonObject.get("token").toString();
        String cardType = jsonObject.get("cardType").toString();
        String expirationDate = jsonObject.get("expirationDate").toString();
        String brand = jsonObject.get("brand").toString();
        step3GetPanUrl.init();
        try {
            step3GetPanUrl.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
