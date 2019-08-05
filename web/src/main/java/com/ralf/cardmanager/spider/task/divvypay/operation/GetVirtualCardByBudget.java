package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.jeesite.common.lang.StringUtils;
import com.ralf.cardmanager.spider.task.BaseOperation;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope("prototype")
public class GetVirtualCardByBudget extends BaseDivvyOperation {
    private String body = "{\"operationName\":\"GetBudgetVirtualCards\",\"variables\":{\"budgetId\":\"%s\",\"first\":30,\"types\":[\"BURNER\",\"SUBSCRIPTION\",\"RECURRING\"],\"sortColumn\":\"name\"},\"query\":\"query GetBudgetVirtualCards($budgetId: ID!, $first: String, $after: String, $types: String, $vendors: [String], $owners: String, $search: String, $sortColumn: String, $sortDirection: String) {\\n  node(id: $budgetId) {\\n    ... on Budget {\\n      id\\n      currentGoal\\n      name\\n      totalDivviedForBudgetPeriod\\n      type\\n      allCards(first: $first, after: $after, types: $types, merchants: $vendors, owners: $owners, search: $search, sortColumn: $sortColumn, sortDirection: $sortDirection) {\\n        totalCount\\n        pageInfo {\\n          hasNextPage\\n          endCursor\\n          __typename\\n        }\\n        edges {\\n          node {\\n            id\\n            ...VirtualCardsListCard\\n            ...VirtualCardsTableRow\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      company {\\n        id\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment VirtualCardsListCard on Card {\\n  id\\n  frozen\\n  lastFour\\n  name\\n  cardType\\n  user {\\n    id\\n    displayName\\n    firstName\\n    lastName\\n    avatarUrl\\n    __typename\\n  }\\n  userAllocation {\\n    id\\n    expiresAt\\n    type\\n    budget {\\n      id\\n      name\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment VirtualCardsTableRow on Card {\\n  id\\n  name\\n  cardType\\n  latestTransaction {\\n    id\\n    merchantName\\n    cleanedMerchantName\\n    merchantLogoUrl\\n    clearedAt\\n    occurredAt\\n    __typename\\n  }\\n  user {\\n    id\\n    displayName\\n    firstName\\n    lastName\\n    avatarUrl\\n    __typename\\n  }\\n  userAllocation {\\n    id\\n    budget {\\n      id\\n      name\\n      retired\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\"}";
    private String budgetId = "";

    @Autowired
    public GetVirtualCardByBudget(DivvyPaySiteConfig config) {
        super(config);
        String referer = String.format("https://app.divvy.co/budgets/%s/virtual-cards", budgetId);
        defaultHeader.put("referer", referer);
        client = postClient(StringUtils.isEmpty(url) ? defaultUrl : url);
    }

    public GetVirtualCardByBudget(DivvyPaySiteConfig config, String budgetId) {
        this(config);
        bodyParams = new String[]{budgetId};
        this.budgetId = budgetId;
    }


    @Override
    public Object execute() throws IOException {
        setBody(String.format(body, this.budgetId));
        setHeader(null);
        CloseableHttpResponse rsp = new DefaultHttpClient().execute(client);
        if (rsp.getStatusLine().getStatusCode() >= 200) {
            EntityUtils.toByteArray(rsp.getEntity());
        }


        return null;
    }

    @Override
    public void persistent(String rsp) {

    }

    @Override
    public String getUrl() {
        return null;
    }


}

