package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class CreateCardByBudget extends BaseDivvyOperation {

    public void init(String amount, String cardname) {
        super.init(new String[]{
                amount, config.getBudgetId(), cardname, config.getBudgetOwnerId()
        });
    }

    @Autowired
    public CreateCardByBudget(DivvyPaySiteConfig config) {
        super(config);
//        body = "{\"operationName\":\"CreateVirtualCard\",\"variables\":{\"input\":{\"amount\":1,\"budgetId\":\"QnVkZ2V0OjQ5MTQ2\",\"clientMutationId\":\"0\",\"name\":\"testralf\",\"ownerId\":\"VXNlcjo1MTE5OA==\",\"selectedTags\":[{\"tagTypeId\":\"VGFnVHlwZTo3OTQw\",\"tagValueIds\":[\"VGFnVmFsdWU6NDA5NTMy\"]}],\"allocationInterval\":\"MONTHLY\",\"nextAllocation\":1567267200,\"type\":\"RECURRING\"}},\"query\":\"mutation CreateVirtualCard($input: CreateVirtualCardForBudgetInput!) {\\n  createVirtualCardForBudget(input: $input) {\\n    budget {\\n      id\\n      __typename\\n    }\\n    newCardEdge {\\n      node {\\n        id\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        body = "{\"operationName\":\"CreateVirtualCard\",\"variables\":{\"input\":{\"amount\":%s,\"budgetId\":\"%s\",\"clientMutationId\":\"0\",\"name\":\"%s\",\"ownerId\":\"%s\",\"selectedTags\":[{\"tagTypeId\":\"VGFnVHlwZTo3OTQw\",\"tagValueIds\":[\"VGFnVmFsdWU6NDA5NTMy\"]}],\"allocationInterval\":\"MONTHLY\",\"nextAllocation\":1567267200,\"type\":\"RECURRING\"}},\"query\":\"mutation CreateVirtualCard($input: CreateVirtualCardForBudgetInput!) {\\n  createVirtualCardForBudget(input: $input) {\\n    budget {\\n      id\\n      __typename\\n    }\\n    newCardEdge {\\n      node {\\n        id\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        bodyParams = new String[]{"amount", config.getBudgetId(), "cardname", "ownerId"};
        defaultHeader.put("referer", "https://app.divvy.co/virtual-cards");
    }


    //返回 {"data":{"createVirtualCardForBudget":{"newCardEdge":{"node":{"id":"Q2FyZDozNjA2ODY=","__typename":"Card"},"__typename":"CardEdge"},"budget":{"id":"QnVkZ2V0OjQ5MTQ2","__typename":"Budget"},"__typename":"CreateVirtualCardForBudgetPayload"}}}
    //需要获取card_id
    @Override
    public void persistent(String rsp) {

    }

    @Override
    public String getUrl() {
        return null;
    }
}
