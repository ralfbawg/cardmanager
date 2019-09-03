package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.jeesite.common.lang.StringUtils;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope("prototype")
public class GetVirtualCardByBudget extends BaseDivvyOperation<GetVCardsByBudgetIdRsp> {
    private String body = "{\"operationName\":\"GetBudgetVirtualCards\",\"variables\":{\"budgetId\":\"%s\",\"first\":30,\"types\":[\"BURNER\",\"SUBSCRIPTION\",\"RECURRING\"],\"sortColumn\":\"name\"},\"query\":\"query GetBudgetVirtualCards($budgetId: ID!, $first: String, $after: String, $types: String, $vendors: [String], $owners: String, $search: String, $sortColumn: String, $sortDirection: String) {\\n  node(id: $budgetId) {\\n    ... on Budget {\\n      id\\n      currentGoal\\n      name\\n      totalDivviedForBudgetPeriod\\n      type\\n      allCards(first: $first, after: $after, types: $types, merchants: $vendors, owners: $owners, search: $search, sortColumn: $sortColumn, sortDirection: $sortDirection) {\\n        totalCount\\n        pageInfo {\\n          hasNextPage\\n          endCursor\\n          __typename\\n        }\\n        edges {\\n          node {\\n            id\\n            ...VirtualCardsListCard\\n            ...VirtualCardsTableRow\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      company {\\n        id\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment VirtualCardsListCard on Card {\\n  id\\n  frozen\\n  lastFour\\n  name\\n  cardType\\n  user {\\n    id\\n    displayName\\n    firstName\\n    lastName\\n    avatarUrl\\n    __typename\\n  }\\n  userAllocation {\\n    id\\n    expiresAt\\n    type\\n    budget {\\n      id\\n      name\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment VirtualCardsTableRow on Card {\\n  id\\n  name\\n  cardType\\n  latestTransaction {\\n    id\\n    merchantName\\n    cleanedMerchantName\\n    merchantLogoUrl\\n    clearedAt\\n    occurredAt\\n    __typename\\n  }\\n  user {\\n    id\\n    displayName\\n    firstName\\n    lastName\\n    avatarUrl\\n    __typename\\n  }\\n  userAllocation {\\n    id\\n    budget {\\n      id\\n      name\\n      retired\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\"}";


    @Autowired
    public GetVirtualCardByBudget(DivvyPaySiteConfig config) {
        super(config);
        String referer = String.format("https://app.divvy.co/budgets/%s/virtual-cards", config.getBudgetId());
        bodyParams = new String[]{config.getBudgetId()};
        defaultHeader.put("referer", referer);

    }


    @Override
    public GetVCardsByBudgetIdRsp persistent(String rsp) {
        return null;
    }


}

class GetVCardsByBudgetIdRsp extends BaseDivvyOpertionResp {

}
