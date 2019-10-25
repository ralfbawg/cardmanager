package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import lombok.val;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @program: cardmanager
 * @description: 一次性搞定余额与状态
 * @author: Ralf Chen
 * @create: 2019-10-25 11:04
 **/
@Service
@Scope("prototype")
public class GetVirtualCardDrawer extends BaseDivvyOperation<GetVirtualCardDrawerRsp> {
    public GetVirtualCardDrawer(DivvyPaySiteConfig config) {
        super(config);
        defaultHeader.put("referer", String.format("https://app.divvy.co/companies/%s/cards",config.getCompanyId()));
        body = "{\"operationName\":\"GetVirtualCardDrawer\",\"variables\":{\"cardId\":\"%s\",\"first\":9999999},\"query\":\"query GetVirtualCardDrawer($cardId: ID!, $first: Int) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      name\\n      cardType\\n      token\\n      expirationDate\\n      latestTransaction {\\n        id\\n        merchantName\\n        cleanedMerchantName\\n        merchantLogoUrl\\n        clearedAt\\n        occurredAt\\n        __typename\\n      }\\n      user {\\n        id\\n        displayName\\n        firstName\\n        lastName\\n        avatarUrl\\n        activeUserAllocation {\\n          id\\n          expiresAt\\n          type\\n          totalCleared\\n          totalPending\\n          availableFunds\\n          recurringAmount\\n          budget {\\n            id\\n            name\\n            balance\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        budget {\\n          id\\n          name\\n          retired\\n          balance\\n          __typename\\n        }\\n        expiresAt\\n        type\\n        totalCleared\\n        totalPending\\n        availableFunds\\n        recurringAmount\\n        __typename\\n      }\\n      frozen\\n      lastFour\\n      activationStatus\\n      blocked\\n      budget {\\n        budgetOwners: users(first: $first, role: MANAGER) {\\n          edges {\\n            node {\\n              id\\n              __typename\\n            }\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
    }

    public GetVirtualCardDrawer init(String cardId) throws Exception {
        super.init(new String[]{cardId});
        return this;
    }

    //{"data":{"node":{"userAllocation":{"type":"ONE_TIME","totalPending":529,"totalCleared":0,"recurringAmount":0,"id":"VXNlckFsbG9jYXRpb246NjUzMjQ2","expiresAt":1666635152,"budget":{"retired":false,"name":"ceshi2","id":"QnVkZ2V0OjQ5MTQ2","balance":257565,"__typename":"Budget"},"availableFunds":1473,"__typename":"UserAllocation"},"user":{"lastName":"Gao","id":"VXNlcjo1MTE5OA==","firstName":"Xiaoya","displayName":"Xiaoya Gao","avatarUrl":null,"activeUserAllocation":{"type":"ONE_TIME","totalPending":0,"totalCleared":0,"recurringAmount":null,"id":"VXNlckFsbG9jYXRpb246NDYxNjgz","expiresAt":null,"budget":{"name":"Cavy0613","id":"QnVkZ2V0OjM5NzA0","balance":30502,"__typename":"Budget"},"availableFunds":0,"__typename":"UserAllocation"},"__typename":"User"},"token":"001.R.20191021073059001386","name":"Cavy的帐户,1571632191691","latestTransaction":{"occurredAt":1571970585,"merchantName":"PAYPAL *EBAY EBAY INC","merchantLogoUrl":"https://s3.amazonaws.com/MD_Assets/merchant_logos/ebay.png","id":"VHJhbnNhY3Rpb246NWVjMjE4ZGEtYzM5My00MTFjLWE5MmMtYzkzMjlhOTk3NTBi","clearedAt":1571970585,"cleanedMerchantName":"Ebay","__typename":"Transaction"},"lastFour":"7772","id":"Q2FyZDo1MjUyOTA=","frozen":false,"expirationDate":"10/22","cardType":"BURNER","budget":{"budgetOwners":{"edges":[{"node":{"id":"VXNlcjo1MTE5OA==","__typename":"User"},"__typename":"BudgetToUsersEdge"}],"__typename":"BudgetToUsersConnection"},"__typename":"Budget"},"blocked":false,"activationStatus":"ACTIVATED","__typename":"Card"}}}
    @Override
    public GetVirtualCardDrawerRsp persistent(String rsp) throws Exception {
        JsonObject jsonObject = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("node").getAsJsonObject();
        val aStatus = jsonObject.get("activationStatus").getAsString();
        val blocked = jsonObject.get("blocked").getAsBoolean();
        val frozen = jsonObject.get("frozen").getAsBoolean();
        val amount = jsonObject.get("userAllocation").getAsJsonObject().get("availableFunds").getAsLong();
        val allocationId = jsonObject.get("userAllocation").getAsJsonObject().get("id").getAsString();
        val totalCleared = jsonObject.get("userAllocation").getAsJsonObject().get("totalCleared").getAsLong();
        val totalPending = jsonObject.get("userAllocation").getAsJsonObject().get("totalPending").getAsLong();
        return new GetVirtualCardDrawerRsp(aStatus,allocationId,blocked,frozen,totalCleared,amount,totalPending);
    }
}
