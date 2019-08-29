package com.ralf.cardmanager.spider.task.divvypay.operation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @program: cardmanager
 * @description: 获取公司帐户
 * @author: Ralf Chen
 * @create: 2019-08-29 12:19
 **/
@Service
@Scope("prototype")
public class GetCompanyBudgets extends BaseDivvyOperation {
    public GetCompanyBudgets(DivvyPaySiteConfig config) {
        super(config);
//        this.body = "{\"operationName\":\"GetCompanyBudgets\",\"variables\":{\"companyId\":\"Q29tcGFueTozMDI3\",\"first\":20,\"retired\":false},\"query\":\"query GetCompanyBudgets($after: String, $companyId: ID!, $retired: Boolean, $first: Int, $search: String, $role: String, $sortColumn: String, $sortDirection: String, $type: String) {\\n  node(id: $companyId) {\\n    ... on Company {\\n      id\\n      budgets(first: $first, search: $search, after: $after, role: $role, retired: $retired, sortColumn: $sortColumn, sortDirection: $sortDirection, type: $type) {\\n        pageInfo {\\n          hasNextPage\\n          endCursor\\n          __typename\\n        }\\n        edges {\\n          cursor\\n          node {\\n            id\\n            balance\\n            currentGoal\\n            expiresAt\\n            name\\n            retired\\n            shareBudgetFunds\\n            totalClearedForBudgetPeriod\\n            totalDivviedForBudgetPeriod\\n            totalPendingForBudgetPeriod\\n            totalSpentForBudgetPeriod\\n            availableAmountForBudgetPeriod\\n            totalAvailableToSpend\\n            type\\n            userClearedForBudgetPeriod\\n            userDivviedForBudgetPeriod\\n            userPendingForBudgetPeriod\\n            userSpentForBudgetPeriod\\n            userAvailableToSpend\\n            userRole\\n            totalAllocatedForNextMonth\\n            recurringAmount\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        this.body = "{\"operationName\":\"GetCompanyBudgets\",\"variables\":{\"companyId\":\"%s\",\"first\":20,\"retired\":false},\"query\":\"query GetCompanyBudgets($after: String, $companyId: ID!, $retired: Boolean, $first: Int, $search: String, $role: String, $sortColumn: String, $sortDirection: String, $type: String) {\\n  node(id: $companyId) {\\n    ... on Company {\\n      id\\n      budgets(first: $first, search: $search, after: $after, role: $role, retired: $retired, sortColumn: $sortColumn, sortDirection: $sortDirection, type: $type) {\\n        pageInfo {\\n          hasNextPage\\n          endCursor\\n          __typename\\n        }\\n        edges {\\n          cursor\\n          node {\\n            id\\n            balance\\n            currentGoal\\n            expiresAt\\n            name\\n            retired\\n            shareBudgetFunds\\n            totalClearedForBudgetPeriod\\n            totalDivviedForBudgetPeriod\\n            totalPendingForBudgetPeriod\\n            totalSpentForBudgetPeriod\\n            availableAmountForBudgetPeriod\\n            totalAvailableToSpend\\n            type\\n            userClearedForBudgetPeriod\\n            userDivviedForBudgetPeriod\\n            userPendingForBudgetPeriod\\n            userSpentForBudgetPeriod\\n            userAvailableToSpend\\n            userRole\\n            totalAllocatedForNextMonth\\n            recurringAmount\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
        this.bodyParams = new String[]{
                "companyId"
        };
    }


    //返回 {"data":{"node":{"id":"Q29tcGFueTozMDI3","budgets":{"pageInfo":{"hasNextPage":false,"endCursor":"YXJyYXljb25uZWN0aW9uOjA=","__typename":"PageInfo"},"edges":[{"node":{"userSpentForBudgetPeriod":0,"userRole":"MANAGER","userPendingForBudgetPeriod":0,"userDivviedForBudgetPeriod":4403,"userClearedForBudgetPeriod":0,"userAvailableToSpend":4403,"type":"ONE_TIME","totalSpentForBudgetPeriod":0,"totalPendingForBudgetPeriod":0,"totalDivviedForBudgetPeriod":4403,"totalClearedForBudgetPeriod":0,"totalAvailableToSpend":4403,"totalAllocatedForNextMonth":0,"shareBudgetFunds":false,"retired":false,"recurringAmount":10000,"name":"ceshi","id":"QnVkZ2V0OjQ3MzAz","expiresAt":null,"currentGoal":10000,"balance":5597,"availableAmountForBudgetPeriod":5597,"__typename":"Budget"},"cursor":"YXJyYXljb25uZWN0aW9uOjA=","__typename":"BudgetEdge"}],"__typename":"BudgetConnection"},"__typename":"Company"}}}
    //需要获取 data.node.budgets.edges[0].node
    //内容
    @Override
    public void persistent(String rsp) {

    }
}
