package com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.CreateCardStep2;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @program: cardmanager
 * @description: 获取交易流水详细信息
 * @author: Ralf Chen
 * @create: 2019-09-04 18:12
 **/
@Service
@Scope("prototype")
public class GetTransaction_Details_Merchant extends BaseDivvyOperation<GetTransaction_Details_MerchantRsp> {
    public GetTransaction_Details_Merchant init(String transactionId) {
        super.init(new String[]{transactionId});
        return this;
    }

    public GetTransaction_Details_Merchant(DivvyPaySiteConfig config) {
        super(config);
        this.body = "{\"operationName\":\"GetTransaction_Details_Merchant\",\"variables\":{\"transactionId\":\"VHJhbnNhY3Rpb246ODY1YmVjZGUtYjA5Mi00YjE5LTk0ZDUtMTA5OTdiYjc4ZDYy\"},\"query\":\"query GetTransaction_Details_Merchant($transactionId: ID!, $includeDeleted: Boolean = true) {\\n  node(id: $transactionId) {\\n    ... on Transaction {\\n      id\\n      amount\\n      transactedAmount\\n      fees\\n      status\\n      clearedAt\\n      occurredAt\\n      type\\n      isParent\\n      parentTransactionId\\n      isReconciled\\n      merchantName\\n      declineReason\\n      budget {\\n        id\\n        name\\n        __typename\\n      }\\n      accountingIntegrationTransactions {\\n        id\\n        syncStatus\\n        syncMessage\\n        integrationType\\n        __typename\\n      }\\n      ...TransactionReviews\\n      card {\\n        id\\n        lastFour\\n        deleted\\n        cardType\\n        name\\n        user {\\n          id\\n          avatarUrl\\n          displayName\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    ...ChildTransactions\\n    __typename\\n  }\\n}\\n\\nfragment ChildTransactions on Transaction {\\n  childTransactions(first: 100) {\\n    totalCount\\n    edges {\\n      node {\\n        id\\n        status\\n        accountingIntegrationTransactions {\\n          id\\n          syncStatus\\n          syncMessage\\n          integrationType\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment TransactionReviews on Transaction {\\n  reviews(includeDeleted: $includeDeleted) {\\n    id\\n    insertedAt\\n    deletedAt\\n    reviewer {\\n      id\\n      displayName\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\"}";
    }
    //{"data":{"node":{"type":"AUTHORIZATION","transactedAmount":-100,"status":"OPEN","reviews":[],"parentTransactionId":null,"occurredAt":1567591592,"merchantName":"FACEBK QF2O","isReconciled":false,"isParent":false,"id":"VHJhbnNhY3Rpb246ODY1YmVjZGUtYjA5Mi00YjE5LTk0ZDUtMTA5OTdiYjc4ZDYy","fees":0,"declineReason":null,"clearedAt":1567591592,"childTransactions":{"totalCount":0,"edges":[],"__typename":"TransactionConnection"},"card":{"user":{"id":"VXNlcjo2MjgzMA==","displayName":"Steven Wong","avatarUrl":null,"__typename":"User"},"name":"B9-4zzg1","lastFour":"0094","id":"Q2FyZDo0MDkyMzU=","deleted":false,"cardType":"SUBSCRIPTION","__typename":"Card"},"budget":{"name":"mystery0804","id":"QnVkZ2V0OjQ4OTEy","__typename":"Budget"},"amount":-100,"accountingIntegrationTransactions":null,"__typename":"Transaction"}}}

    @Override
    public CreateCardStep2.CreateCardStep2Resp persistent(String rsp) throws IOException {
        JsonObject t = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("node").getAsJsonObject();
        Long occurredAt = t.getAsJsonObject().get("occurredAt").getAsLong();
        Long amount = t.getAsJsonObject().get("amount").getAsLong();
        Long clearedAt = t.getAsJsonObject().get("clearedAt").getAsLong();
        String transactionId = t.getAsJsonObject().get("id").getAsString();
        String status = t.getAsJsonObject().get("status").getAsString();
        String type = t.getAsJsonObject().get("type").getAsString();
        String merchantName = t.getAsJsonObject().get("merchantName").getAsString();
        return new GetTransaction_Details_MerchantRsp(transactionId,type,status,merchantName,amount,new Date(clearedAt * 1000), new Date(occurredAt * 1000));
    }
}

@Data
@AllArgsConstructor
class GetTransaction_Details_MerchantRsp extends BaseDivvyOpertionResp {
    private String transactionId;
    private String type;
    private String status;
    private String merchantName;
    private Long amount;
    private Date clearDate;
    private Date occurredDate;
}