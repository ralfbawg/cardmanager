package com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation;

import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.CreateCardStep2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @program: cardmanager
 * @description: 获取流水总数
 * @author: Ralf Chen
 * @create: 2019-09-04 17:03
 **/
@Service
@Scope("prototype")
public class GetCompanyTransactionsTotalCount extends BaseDivvyOperation<GetCompanyTransactionsTotalCountRsp> {
    public GetCompanyTransactionsTotalCount init() throws Exception {
        super.init(new String[]{config.getCompanyId()});
        return this;
    }

    public GetCompanyTransactionsTotalCount(DivvyPaySiteConfig config) {
        super(config);
        this.body = "{\"operationName\":\"GetCompanyTransactionsTotalCount\",\"variables\":{\"sortDirection\":\"desc\",\"companyId\":\"%s\",\"sortColumn\":\"date\",\"types\":null,\"budgetId\":null,\"cardId\":null,\"userId\":null,\"status\":null,\"after\":null,\"amountMax\":null,\"amountMin\":null,\"before\":null,\"dateEnd\":null,\"dateStart\":null,\"filterTagValues\":null,\"first\":30,\"last\":null,\"merchantName\":null,\"search\":null,\"isReviewed\":null},\"query\":\"query GetCompanyTransactionsTotalCount($companyId: ID!, $search: String, $userId: [ID], $budgetId: [ID], $cardId: [ID], $merchantName: String, $filterTagValues: [ID], $amountMin: Int, $amountMax: Int, $dateStart: Time, $dateEnd: Time, $sortDirection: String = \\\"desc\\\", $sortColumn: String, $reconciled: Boolean, $isReviewed: Boolean, $isLocked: Boolean, $syncStatus: [SyncStatus], $hasAllRequiredFieldsCompleted: Boolean, $sortTagType: ID, $types: [TransactionType], $status: [TransactionStatus]) {\\n  node(id: $companyId) {\\n    ... on Company {\\n      id\\n      transactionsTotalCount(search: $search, userId: $userId, cardId: $cardId, budgetId: $budgetId, merchantName: $merchantName, filterTagValues: $filterTagValues, amountMin: $amountMin, amountMax: $amountMax, dateStart: $dateStart, dateEnd: $dateEnd, sortDirection: $sortDirection, sortColumn: $sortColumn, reconciled: $reconciled, isReviewed: $isReviewed, isLocked: $isLocked, syncStatus: $syncStatus, hasAllRequiredFieldsCompleted: $hasAllRequiredFieldsCompleted, sortTagType: $sortTagType, types: $types, status: $status)\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        defaultHeader.put("referer", " https://app.divvy.co/home");
    }

    @Override
    public GetCompanyTransactionsTotalCountRsp persistent(String rsp) throws IOException {
        val jsonObject = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("node").getAsJsonObject();
        return new GetCompanyTransactionsTotalCountRsp(jsonObject.get("transactionsTotalCount").getAsLong());
    }
}

