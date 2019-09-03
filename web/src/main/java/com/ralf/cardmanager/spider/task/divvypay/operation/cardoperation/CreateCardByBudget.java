package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope("prototype")
public class CreateCardByBudget extends BaseDivvyOperation<CreateCardByBudgetResponse> {

    @Autowired
    CreateCardStep2 createCardStep2;

    public CreateCardByBudget init(String amount, String cardname) {
        super.init(new String[]{
                amount, config.getBudgetId(), cardname, config.getBudgetOwnerId(), String.valueOf(new DateTime().plusYears(3).getMillis() / 1000)
        });
        return this;
    }

    @Autowired
    public CreateCardByBudget(DivvyPaySiteConfig config) {
        super(config);
//        body = "{\"operationName\":\"CreateVirtualCard\",\"variables\":{\"input\":{\"amount\":1,\"budgetId\":\"QnVkZ2V0OjQ5MTQ2\",\"clientMutationId\":\"0\",\"name\":\"testralf\",\"ownerId\":\"VXNlcjo1MTE5OA==\",\"selectedTags\":[{\"tagTypeId\":\"VGFnVHlwZTo3OTQw\",\"tagValueIds\":[\"VGFnVmFsdWU6NDA5NTMy\"]}],\"allocationInterval\":\"MONTHLY\",\"nextAllocation\":1567267200,\"type\":\"RECURRING\"}},\"query\":\"mutation CreateVirtualCard($input: CreateVirtualCardForBudgetInput!) {\\n  createVirtualCardForBudget(input: $input) {\\n    budget {\\n      id\\n      __typename\\n    }\\n    newCardEdge {\\n      node {\\n        id\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
//        body = "{\"operationName\":\"CreateVirtualCard\",\"variables\":{\"input\":{\"amount\":%s,\"budgetId\":\"%s\",\"clientMutationId\":\"0\",\"name\":\"%s\",\"ownerId\":\"%s\",\"selectedTags\":[{\"tagTypeId\":\"VGFnVHlwZTo3OTQw\",\"tagValueIds\":[\"VGFnVmFsdWU6NDA5NTMy\"]}],\"allocationInterval\":\"MONTHLY\",\"nextAllocation\":1567267200,\"type\":\"RECURRING\"}},\"query\":\"mutation CreateVirtualCard($input: CreateVirtualCardForBudgetInput!) {\\n  createVirtualCardForBudget(input: $input) {\\n    budget {\\n      id\\n      __typename\\n    }\\n    newCardEdge {\\n      node {\\n        id\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        body = "{\"operationName\":\"CreateVirtualCard\",\"variables\":{\"input\":{\"amount\":%s,\"budgetId\":\"%s\",\"clientMutationId\":\"0\",\"name\":\"%s\",\"ownerId\":\"%s\",\"selectedTags\":[{\"tagTypeId\":\"VGFnVHlwZTo3OTQw\",\"tagValueIds\":[\"VGFnVmFsdWU6NDA5NTMy\"]}],\"type\":\"ONE_TIME\",\"expiresAt\":%s}},\"query\":\"mutation CreateVirtualCard($input: CreateVirtualCardForBudgetInput!) {\\n  createVirtualCardForBudget(input: $input) {\\n    budget {\\n      id\\n      __typename\\n    }\\n    newCardEdge {\\n      node {\\n        id\\n        expirationDate\\n        lastFour\\n        name\\n        token\\n        user {\\n          id\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        defaultHeader.put("referer", "https://app.divvy.co/cards");
    }


    //返回 {"data":{"createVirtualCardForBudget":{"newCardEdge":{"node":{"id":"Q2FyZDozNjA2ODY=","__typename":"Card"},"__typename":"CardEdge"},"budget":{"id":"QnVkZ2V0OjQ5MTQ2","__typename":"Budget"},"__typename":"CreateVirtualCardForBudgetPayload"}}}
    //需要获取card_id
    @Override
    public CreateCardByBudgetResponse persistent(String rsp) throws IOException {
        JsonObject jsonObject = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("createVirtualCardForBudget").getAsJsonObject().get("newCardEdge").getAsJsonObject();
        val cardId = jsonObject.get("node").getAsJsonObject().get("id").getAsString();
        val budgetId = jsonObject.get("budget").getAsJsonObject().get("id").getAsString();
        return new CreateCardByBudgetResponse(cardId, budgetId, createCardStep2.init(cardId).execute());
    }


}

@Data
@AllArgsConstructor
class CreateCardByBudgetResponse extends BaseDivvyOpertionResp {
    private String cardId;
    private String budgetId;
    private CreateCardStep2Resp step2Resp;
}
