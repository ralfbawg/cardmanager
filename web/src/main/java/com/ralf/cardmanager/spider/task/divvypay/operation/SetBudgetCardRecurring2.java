package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;

public class SetBudgetCardRecurring2 extends BaseDivvyOperation {
    public SetBudgetCardRecurring2(DivvyPaySiteConfig config) {
        super(config);
        body = "{\"operationName\":\"UpdateUserAllocations\",\"variables\":{\"input\":{\"userAllocationUpdates\":[{\"userAllocationId\":\"%s\",\"allocationInterval\":\"MONTHLY\",\"type\":\"RECURRING\",\"recurringAmount\":%s,\"nextAllocation\":1567267200}],\"clientMutationId\":\"0\"}},\"query\":\"mutation UpdateUserAllocations($input: UpdateUserAllocationsInput!) {\\n  updateUserAllocations(input: $input) {\\n    budget {\\n      id\\n      ...budgetSelectorItem\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment budgetSelectorItem on Budget {\\n  id\\n  balance\\n  currentGoal\\n  name\\n  retired\\n  totalDivviedForBudgetPeriod\\n  userDivviedForBudgetPeriod\\n  userClearedForBudgetPeriod\\n  userPendingForBudgetPeriod\\n  __typename\\n}\\n\"}";
//        body = "{"operationName":"UpdateUserAllocations","variables":{"input":{"userAllocationUpdates":[{"userAllocationId":"VXNlckFsbG9jYXRpb246NDY0MzUw","allocationInterval":"MONTHLY","type":"RECURRING","recurringAmount":30,"nextAllocation":1567267200}],"clientMutationId":"0"}},"query":"mutation UpdateUserAllocations($input: UpdateUserAllocationsInput!) {\n  updateUserAllocations(input: $input) {\n    budget {\n      id\n      ...budgetSelectorItem\n      __typename\n    }\n    __typename\n  }\n}\n\nfragment budgetSelectorItem on Budget {\n  id\n  balance\n  currentGoal\n  name\n  retired\n  totalDivviedForBudgetPeriod\n  userDivviedForBudgetPeriod\n  userClearedForBudgetPeriod\n  userPendingForBudgetPeriod\n  __typename\n}\n"}";
        bodyParams = new String[]{"userAllocationId","amount"};
        String referer = String.format("https://app.divvy.co/budgets/%s/recurring-funds", config.getBudgetId());
        defaultHeader.put("referer", referer);
    }

    @Override
    public void persistent(String rsp) {

    }
}
