package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class UpdateVirtualCard extends BaseDivvyOperation<UpdateVirtualCardRsp> {
    public UpdateVirtualCard(DivvyPaySiteConfig config) {
        super(config);
        body = "{\"operationName\":\"UpdateVirtualCard\",\"variables\":{\"input\":{\"amount\":%s,\"budgetId\":\"%s\",\"cardId\":\"%s\",\"name\":\"%s\",\"clientMutationId\":\"0\",\"recurringAmount\":0,\"expiresAt\":1662047999,\"selectedTags\":[{\"tagTypeId\":\"VGFnVHlwZTo3OTQw\",\"tagValueIds\":[\"VGFnVmFsdWU6NDA5NTI5\"]}]}},\"query\":\"mutation UpdateVirtualCard($input: UpdateCardInput!) {\\n  updateCard(input: $input) {\\n    card {\\n      id\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
//        body = "{"operationName":"UpdateVirtualCard","variables":{"input":{"amount":80,"budgetId":"QnVkZ2V0OjQ3MzAz","cardId":"Q2FyZDo0MDQ5NDQ=","name":"ceshiforcreate","clientMutationId":"0","recurringAmount":0,"expiresAt":1662047999,"selectedTags":[{"tagTypeId":"VGFnVHlwZTo3OTQw","tagValueIds":["VGFnVmFsdWU6NDA5NTI5"]}]}},"query":"mutation UpdateVirtualCard($input: UpdateCardInput!) {\n  updateCard(input: $input) {\n    card {\n      id\n      __typename\n    }\n    __typename\n  }\n}\n"}";
        bodyParams = new String[]{"amount", config.getBudgetId(), "cardId", "cardName"};
        defaultHeader.put("referer", "https://app.divvy.co/cards");
    }

    //返回 获取allocationId
    //{"data":{"node":{"users":{"edges":[{"role":"MANAGER","node":{"lastName":"SITK","id":"VXNlcjo1MTIzNg==","firstName":"MING","displayName":"MING SITK","avatarUrl":null,"allocations":{"edges":[{"node":{"type":"ONE_TIME","recurringAmount":null,"id":"VXNlckFsbG9jYXRpb246NDQxNTY3","balance":2700,"__typename":"UserAllocation"},"__typename":"UserAllocationEdge"}],"__typename":"UserAllocationConnection"},"__typename":"User"},"__typename":"BudgetToUsersEdge"}],"__typename":"BudgetToUsersConnection"},"type":"ONE_TIME","totalPendingForBudgetPeriod":0,"totalDivviedForBudgetPeriod":3703,"totalClearedForBudgetPeriod":0,"shareBudgetFunds":false,"recurringAmount":10000,"id":"QnVkZ2V0OjQ3MzAz","expiresAt":null,"currentGoal":10000,"allCards":{"edges":[{"node":{"userAllocation":{"type":"RECURRING","recurringAmount":1,"id":"VXNlckFsbG9jYXRpb246NDQ1NTgy","expiresAt":null,"balance":1,"__typename":"UserAllocation"},"name":"ralf","latestTransaction":null,"lastFour":"1173","id":"Q2FyZDozNDU1NTY=","cardType":"SUBSCRIPTION","__typename":"Card"},"__typename":"CardEdge"},{"node":{"userAllocation":{"type":"RECURRING","recurringAmount":1,"id":"VXNlckFsbG9jYXRpb246NDQ1NTM2","expiresAt":null,"balance":1,"__typename":"UserAllocation"},"name":"ralfbawg","latestTransaction":null,"lastFour":"0580","id":"Q2FyZDozNDU0OTg=","cardType":"SUBSCRIPTION","__typename":"Card"},"__typename":"CardEdge"},{"node":{"userAllocation":{"type":"RECURRING","recurringAmount":1,"id":"VXNlckFsbG9jYXRpb246NDQ1MzM4","expiresAt":null,"balance":1,"__typename":"UserAllocation"},"name":"ralfchen","latestTransaction":null,"lastFour":"9187","id":"Q2FyZDozNDUzNTc=","cardType":"SUBSCRIPTION","__typename":"Card"},"__typename":"CardEdge"},{"node":{"userAllocation":{"type":"ONE_TIME","recurringAmount":null,"id":"VXNlckFsbG9jYXRpb246NDYxNjgy","expiresAt":1565593200,"balance":1000,"__typename":"UserAllocation"},"name":"Terrence Bryant","latestTransaction":null,"lastFour":"9691","id":"Q2FyZDozNTg1MzQ=","cardType":"BURNER","__typename":"Card"},"__typename":"CardEdge"}],"__typename":"CardConnection"},"__typename":"Budget"}}}
    @Override
    public CreateCardStep2.CreateCardStep2Resp persistent(String rsp) {
        return null;

    }
}
class UpdateVirtualCardRsp extends BaseDivvyOpertionResp{

}