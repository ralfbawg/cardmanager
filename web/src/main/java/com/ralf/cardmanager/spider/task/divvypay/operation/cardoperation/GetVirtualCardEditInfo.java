package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonParser;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import lombok.val;
import lombok.var;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class GetVirtualCardEditInfo extends BaseDivvyOperation<GetVirtualCardEditRsp> {
    @Autowired
    TblCardInfoService tblCardInfoService;

    public GetVirtualCardEditInfo init(String cardId) throws Exception {
        val t = new DateTime(2019, 12, 1, 1, 1);
        val start = (new DateTime(t.getYear(), t.getMonthOfYear(), 1, 8, 0).getMillis() / 1000);
        val end = (new DateTime((t.getMonthOfYear() != 12 ? 0 : 1) + t.getYear(), (t.getMonthOfYear() == 12 ? -11 : 1) + t.getMonthOfYear(), 1, 8, 0).getMillis() / 1000) - 1;
        super.init(new String[]{cardId, String.valueOf(start), String.valueOf(end)});
        return this;
    }


    public GetVirtualCardEditInfo(DivvyPaySiteConfig config) {
        super(config);
        defaultHeader.put("referer", "https://app.divvy.co/cards");
//        body = "{"operationName":"GetVirtualCardEdit","variables":{"cardId":"Q2FyZDo0MDQ5NDQ=","dateStart":1567296000,"dateEnd":1569887999,"tagValueCount":9999999},"query":"query GetVirtualCardEdit($cardId: ID!, $dateStart: Time, $dateEnd: Time, $tagValueCount: Int = 1000) {\n  node(id: $cardId) {\n    ... on Card {\n      id\n      name\n      cardType\n      budget {\n        currentGoal\n        id\n        name\n        recurringAmount\n        shareBudgetFunds\n        totalDivviedForBudgetPeriod\n        totalCleared\n        totalPending\n        totalAllocatedForNextMonth\n        type\n        company {\n          id\n          __typename\n        }\n        __typename\n      }\n      assignedTagValues(first: $tagValueCount) {\n        edges {\n          node {\n            id\n            tagType {\n              id\n              __typename\n            }\n            tagValue {\n              id\n              value\n              __typename\n            }\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      user {\n        avatarUrl\n        displayName\n        id\n        __typename\n      }\n      userAllocation {\n        id\n        balance\n        budget {\n          id\n          currentGoal\n          name\n          totalDivviedForBudgetPeriod\n          __typename\n        }\n        expiresAt\n        initialAmount\n        recurringAmount\n        type\n        totalCleared(clearedDateStart: $dateStart, clearedDateEnd: $dateEnd)\n        totalPending(occurredDateStart: $dateStart, occurredDateEnd: $dateEnd)\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n}\n"}";
        body = "{\"operationName\":\"GetVirtualCardEdit\",\"variables\":{\"cardId\":\"%s\",\"dateStart\":%s,\"dateEnd\":%s,\"tagValueCount\":9999999},\"query\":\"query GetVirtualCardEdit($cardId: ID!, $dateStart: Time, $dateEnd: Time, $tagValueCount: Int = 1000) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      name\\n      cardType\\n      budget {\\n        currentGoal\\n        id\\n        name\\n        recurringAmount\\n        shareBudgetFunds\\n        totalDivviedForBudgetPeriod\\n        totalCleared\\n        totalPending\\n        totalAllocatedForNextMonth\\n        type\\n        company {\\n          id\\n          __typename\\n        }\\n        __typename\\n      }\\n      assignedTagValues(first: $tagValueCount) {\\n        edges {\\n          node {\\n            id\\n            tagType {\\n              id\\n              __typename\\n            }\\n            tagValue {\\n              id\\n              value\\n              __typename\\n            }\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      user {\\n        avatarUrl\\n        displayName\\n        id\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        balance\\n        budget {\\n          id\\n          currentGoal\\n          name\\n          totalDivviedForBudgetPeriod\\n          __typename\\n        }\\n        expiresAt\\n        initialAmount\\n        recurringAmount\\n        type\\n        totalCleared(clearedDateStart: $dateStart, clearedDateEnd: $dateEnd)\\n        totalPending(occurredDateStart: $dateStart, occurredDateEnd: $dateEnd)\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";


    }

    //返回 获取allocationId
    //{"data":{"node":{"users":{"edges":[{"role":"MANAGER","node":{"lastName":"SITK","id":"VXNlcjo1MTIzNg==","firstName":"MING","displayName":"MING SITK","avatarUrl":null,"allocations":{"edges":[{"node":{"type":"ONE_TIME","recurringAmount":null,"id":"VXNlckFsbG9jYXRpb246NDQxNTY3","balance":2700,"__typename":"UserAllocation"},"__typename":"UserAllocationEdge"}],"__typename":"UserAllocationConnection"},"__typename":"User"},"__typename":"BudgetToUsersEdge"}],"__typename":"BudgetToUsersConnection"},"type":"ONE_TIME","totalPendingForBudgetPeriod":0,"totalDivviedForBudgetPeriod":3703,"totalClearedForBudgetPeriod":0,"shareBudgetFunds":false,"recurringAmount":10000,"id":"QnVkZ2V0OjQ3MzAz","expiresAt":null,"currentGoal":10000,"allCards":{"edges":[{"node":{"userAllocation":{"type":"RECURRING","recurringAmount":1,"id":"VXNlckFsbG9jYXRpb246NDQ1NTgy","expiresAt":null,"balance":1,"__typename":"UserAllocation"},"name":"ralf","latestTransaction":null,"lastFour":"1173","id":"Q2FyZDozNDU1NTY=","cardType":"SUBSCRIPTION","__typename":"Card"},"__typename":"CardEdge"},{"node":{"userAllocation":{"type":"RECURRING","recurringAmount":1,"id":"VXNlckFsbG9jYXRpb246NDQ1NTM2","expiresAt":null,"balance":1,"__typename":"UserAllocation"},"name":"ralfbawg","latestTransaction":null,"lastFour":"0580","id":"Q2FyZDozNDU0OTg=","cardType":"SUBSCRIPTION","__typename":"Card"},"__typename":"CardEdge"},{"node":{"userAllocation":{"type":"RECURRING","recurringAmount":1,"id":"VXNlckFsbG9jYXRpb246NDQ1MzM4","expiresAt":null,"balance":1,"__typename":"UserAllocation"},"name":"ralfchen","latestTransaction":null,"lastFour":"9187","id":"Q2FyZDozNDUzNTc=","cardType":"SUBSCRIPTION","__typename":"Card"},"__typename":"CardEdge"},{"node":{"userAllocation":{"type":"ONE_TIME","recurringAmount":null,"id":"VXNlckFsbG9jYXRpb246NDYxNjgy","expiresAt":1565593200,"balance":1000,"__typename":"UserAllocation"},"name":"Terrence Bryant","latestTransaction":null,"lastFour":"9691","id":"Q2FyZDozNTg1MzQ=","cardType":"BURNER","__typename":"Card"},"__typename":"CardEdge"}],"__typename":"CardConnection"},"__typename":"Budget"}}}
    @Override
    public GetVirtualCardEditRsp persistent(String rsp) {
        // data.node.userAllocation.balance
//        data.node.userAllocation.initialAmount
//        data.node.userAllocation.id
        val jsonObject = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("node").getAsJsonObject();
        val id = jsonObject.get("userAllocation").getAsJsonObject().get("id").getAsString();
        val amount = jsonObject.get("userAllocation").getAsJsonObject().get("balance").getAsLong();
        val initialAmount = jsonObject.get("userAllocation").getAsJsonObject().get("initialAmount").getAsLong();
        val totalCleared = jsonObject.get("userAllocation").getAsJsonObject().get("totalCleared").getAsLong();
        new Thread(() -> {
            val cardInfo = new TblCardInfo();
            cardInfo.setCardId(bodyParams[0]);
            val list = tblCardInfoService.findList(cardInfo);
            if (list!=null&&list.size()==1){
                var result = list.get(0);
                result.setUserAllocationId(id);
                result.setCardSpendAmount(totalCleared);
                result.setCardAmount(amount);
                tblCardInfoService.update(result);
            }
        }).start();


        return new GetVirtualCardEditRsp(id,amount,initialAmount,totalCleared);
    }
}
