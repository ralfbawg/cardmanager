package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.BaseOperation;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Scope
@Service
public class CreateCardByBudget extends BaseDivvyOperation {
    @Override
    protected void init(String... param) {
        super.init(param);
    }

    public CreateCardByBudget(DivvyPaySiteConfig config) {
        super(config);
        //卡分类 {"data":{"node":{"tagValues":{"pageInfo":{"hasNextPage":false,"endCursor":"YXJyYXljb25uZWN0aW9uOjE1","__typename":"PageInfo"},"edges":[{"node":{"value":"Car Rental","id":"VGFnVmFsdWU6NDA5NTI5","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Entertainment","id":"VGFnVmFsdWU6NDA5NTMw","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Flights","id":"VGFnVmFsdWU6NDA5NTMx","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Food","id":"VGFnVmFsdWU6NDA5NTMy","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Fuel","id":"VGFnVmFsdWU6NDA5NTMz","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Lodging","id":"VGFnVmFsdWU6NDA5NTM0","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Maintenance","id":"VGFnVmFsdWU6NDA5NTM1","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Marketing","id":"VGFnVmFsdWU6NDA5NTM2","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Meetings","id":"VGFnVmFsdWU6NDA5NTM3","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Other","id":"VGFnVmFsdWU6NDA5NTM4","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Shipping","id":"VGFnVmFsdWU6NDA5NTM5","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Subscriptions","id":"VGFnVmFsdWU6NDA5NTQw","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Supplies","id":"VGFnVmFsdWU6NDA5NTQx","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Telecom","id":"VGFnVmFsdWU6NDA5NTQy","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Training","id":"VGFnVmFsdWU6NDA5NTQz","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"},{"node":{"value":"Transportation","id":"VGFnVmFsdWU6NDA5NTQ0","deleted":false,"__typename":"TagValue"},"__typename":"TagValueEdge"}],"__typename":"TagValueConnection"},"name":"Category","multiSelect":false,"id":"VGFnVHlwZTo3OTQw","deleted":false,"allowCustomValues":false,"__typename":"TagType"}}}

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
