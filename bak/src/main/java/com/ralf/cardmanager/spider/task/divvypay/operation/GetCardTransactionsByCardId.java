package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;

public class GetCardTransactionsByCardId extends BaseDivvyOperation {
    public GetCardTransactionsByCardId(DivvyPaySiteConfig config) {

        super(config);
        defaultHeader.put("referer", "https://app.divvy.co/virtual-cards");
//        body="{\"operationName\":\"GetVirtualCardDetailsTransactions\",\"variables\":{\"cardId\":\"Q2FyZDozNjA2ODY=\"},\"query\":\"query GetVirtualCardDetailsTransactions($cardId: ID!, $after: String) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      cardType\\n      transactions(first: 15, after: $after) {\\n        pageInfo {\\n          hasNextPage\\n          endCursor\\n          __typename\\n        }\\n        totalCount\\n        edges {\\n          node {\\n            id\\n            amount\\n            fees\\n            clearedAt\\n            isReconciled\\n            merchantName\\n            cleanedMerchantName\\n            merchantLogoUrl\\n            occurredAt\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        type\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        body = "{\"operationName\":\"GetVirtualCardDetailsTransactions\",\"variables\":{\"cardId\":\"Q2FyZDozNjA2ODY=\"},\"query\":\"query GetVirtualCardDetailsTransactions($cardId: ID!, $after: String) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      cardType\\n      transactions(first: 15, after: $after) {\\n        pageInfo {\\n          hasNextPage\\n          endCursor\\n          __typename\\n        }\\n        totalCount\\n        edges {\\n          node {\\n            id\\n            amount\\n            fees\\n            clearedAt\\n            isReconciled\\n            merchantName\\n            cleanedMerchantName\\n            merchantLogoUrl\\n            occurredAt\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        type\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        bodyParams = new String[]{
                "cardId"
        };
    }

    //返回 {"data":{"node":{"userAllocation":{"type":"RECURRING","id":"VXNlckFsbG9jYXRpb246NDY0MzUw","__typename":"UserAllocation"},"transactions":{"totalCount":0,"pageInfo":{"hasNextPage":false,"endCursor":null,"__typename":"PageInfo"},"edges":[],"__typename":"TransactionConnection"},"id":"Q2FyZDozNjA2ODY=","cardType":"SUBSCRIPTION","__typename":"Card"}}}
    @Override
    public void persistent(String rsp) {

    }
}
