package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope("prototype")
public class CreateCardStep2 extends BaseDivvyOperation<CreateCardStep2Resp> {
    @Autowired
    private CreateCardStep3GetPanUrl step3GetPanUrl;


    public CreateCardStep2 init(String cardId) throws Exception {
        super.init(new String[]{
                cardId
        });
        return this;
    }

    public CreateCardStep2(DivvyPaySiteConfig config) {
        super(config);

//        defaultHeader.put("referer", "https://app.divvy.co/virtual-cards");
        defaultHeader.put("referer", "https://app.divvy.co/cards");

//        body = "{\"operationName\":\"GetBankCard\",\"variables\":{\"cardId\":\"Q2FyZDozNjA2ODY=\"},\"query\":\"query GetBankCard($cardId: ID!) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      brand\\n      expirationDate\\n      lastFour\\n      cardType\\n      latestTransaction {\\n        id\\n        merchantName\\n        __typename\\n      }\\n      token\\n      user {\\n        id\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        expiresAt\\n        type\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
//        body = "{\"operationName\":\"GetBankCard\",\"variables\":{\"cardId\":\"%s\"},\"query\":\"query GetBankCard($cardId: ID!) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      brand\\n      expirationDate\\n      lastFour\\n      cardType\\n      latestTransaction {\\n        id\\n        merchantName\\n        __typename\\n      }\\n      token\\n      user {\\n        id\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        expiresAt\\n        type\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
          body = "{\"operationName\":\"GetBankCard\",\"variables\":{\"cardId\":\"%s\"},\"query\":\"query GetBankCard($cardId: ID!) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      user {\\n        id\\n        displayName\\n        activeUserAllocation {\\n          id\\n          expiresAt\\n          type\\n          totalCleared\\n          totalPending\\n          availableFunds\\n          recurringAmount\\n          budget {\\n            id\\n            name\\n            balance\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        expiresAt\\n        type\\n        availableFunds\\n        recurringAmount\\n        budget {\\n          id\\n          name\\n          __typename\\n        }\\n        __typename\\n      }\\n      ...BankCardInfo\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment BankCardInfo on Card {\\n  id\\n  activationStatus\\n  brand\\n  cardType\\n  expirationDate\\n  frozen\\n  lastFour\\n  name\\n  token\\n  __typename\\n}\\n\"}\n";
//        bodyParams = new String[]{"cardId"};
    }

    //{"data":{"node":{"userAllocation":{"type":"RECURRING","id":"VXNlckFsbG9jYXRpb246NDY0MzUw","expiresAt":null,"__typename":"UserAllocation"},"user":{"id":"VXNlcjo1MTE5OA==","__typename":"User"},"token":"001.R.20190806122948189060","latestTransaction":null,"lastFour":"4902","id":"Q2FyZDozNjA2ODY=","expirationDate":"08/22","cardType":"SUBSCRIPTION","brand":"mastercard","__typename":"Card"}}}
    //{"data":{"node":{"userAllocation":{"type":"ONE_TIME","recurringAmount":null,"id":"VXNlckFsbG9jYXRpb246NTE5MDQ2","expiresAt":1662307199,"budget":{"name":"ceshi","id":"QnVkZ2V0OjQ3MzAz","__typename":"Budget"},"availableFunds":5,"__typename":"UserAllocation"},"user":{"id":"VXNlcjo1MTIzNg==","displayName":"MING SITK","activeUserAllocation":{"type":"ONE_TIME","totalPending":0,"totalCleared":0,"recurringAmount":null,"id":"VXNlckFsbG9jYXRpb246NDQxNTY3","expiresAt":null,"budget":{"name":"ceshi","id":"QnVkZ2V0OjQ3MzAz","balance":5475,"__typename":"Budget"},"availableFunds":2700,"__typename":"UserAllocation"},"__typename":"User"},"token":"001.R.20190903112556499368","name":"用户2345","lastFour":"6265","id":"Q2FyZDo0MDc0MDA=","frozen":false,"expirationDate":"09/22","cardType":"BURNER","brand":"mastercard","activationStatus":"ACTIVATED","__typename":"Card"}}}
    //{"data":{"node":{"userAllocation":{"type":"ONE_TIME","id":"VXNlckFsbG9jYXRpb246NTI1Mjk3","expiresAt":1662398659,"__typename":"UserAllocation"},"user":{"id":"VXNlcjo1MTIzNg==","__typename":"User"},"token":"001.R.20190905122452748380","latestTransaction":null,"lastFour":"5047","id":"Q2FyZDo0MTIzNTk=","expirationDate":"09/22","cardType":"BURNER","brand":"mastercard","__typename":"Card"}}}
    //{"data":{"node":{"userAllocation":{"type":"ONE_TIME","id":"VXNlckFsbG9jYXRpb246NTI1Mjk3","expiresAt":1662398659,"__typename":"UserAllocation"},"user":{"id":"VXNlcjo1MTIzNg==","__typename":"User"},"token":"001.R.20190905122452748380","latestTransaction":null,"lastFour":"5047","id":"Q2FyZDo0MTIzNTk=","expirationDate":"09/22","cardType":"BURNER","brand":"mastercard","__typename":"Card"}}}
    //需要获取token(用来获取卡号)与userAllocation的id(用来修改卡的recurring-funds,而且每个月[好像]会更新)
    @Override
    public CreateCardStep2Resp persistent(String rsp) throws Exception {
        JsonObject jsonObject = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("node").getAsJsonObject();
        String token = jsonObject.get("token").getAsString();
        String cardType = jsonObject.get("cardType").getAsString();
        String expirationDate = jsonObject.get("expirationDate").getAsString();
        String brand = jsonObject.get("brand").getAsString();
        String cardStatus = jsonObject.get("activationStatus").getAsString();
        val frozen = jsonObject.get("frozen").getAsBoolean();
        String activationStatus = jsonObject.get("activationStatus").getAsString();
        String allocationId = jsonObject.get("userAllocation").getAsJsonObject().get("id").getAsString();
        val rsp2 = new CreateCardStep2Resp(token, cardType, expirationDate, brand, frozen ? "freezed" : "actived", activationStatus, allocationId, step3GetPanUrl.init(token).execute());
        return rsp2;
    }


}

