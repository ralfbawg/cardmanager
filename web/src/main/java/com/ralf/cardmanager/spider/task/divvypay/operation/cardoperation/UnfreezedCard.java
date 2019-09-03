package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @program: cardmanager
 * @description: 卡片解冻
 * @author: Ralf Chen
 * @create: 2019-08-29 11:17
 **/
@Service
@Scope("prototype")
public class UnfreezedCard extends BaseDivvyOperation<UnfreezedCardRsp> {
    public UnfreezedCard(DivvyPaySiteConfig config) {
        super(config);
//        body="{\"operationName\":\"UnfreezeCard\",\"variables\":{\"input\":{\"cardId\":\"Q2FyZDozNjMzMDU=\",\"clientMutationId\":\"0\"}},\"query\":\"mutation UnfreezeCard($input: UnfreezeCardInput!) {\\n  unfreezeCard(input: $input) {\\n    card {\\n      id\\n      ...BankCardInfo\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment BankCardInfo on Card {\\n  id\\n  activationStatus\\n  brand\\n  cardType\\n  expirationDate\\n  frozen\\n  lastFour\\n  name\\n  token\\n  __typename\\n}\\n\"}";
        body = "{\"operationName\":\"UnfreezeCard\",\"variables\":{\"input\":{\"cardId\":\"%s\",\"clientMutationId\":\"0\"}},\"query\":\"mutation UnfreezeCard($input: UnfreezeCardInput!) {\\n  unfreezeCard(input: $input) {\\n    card {\\n      id\\n      ...BankCardInfo\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment BankCardInfo on Card {\\n  id\\n  activationStatus\\n  brand\\n  cardType\\n  expirationDate\\n  frozen\\n  lastFour\\n  name\\n  token\\n  __typename\\n}\\n\"}";
        bodyParams = new String[]{"cardId"};
        String referer = String.format("https://app.divvy.co/budgets/%s/recurring-funds", config.getBudgetId());
        defaultHeader.put("referer", referer);
    }

    @Override
    public UnfreezedCardRsp persistent(String rsp) {
        return null;
    }
}
class UnfreezedCardRsp extends BaseDivvyOpertionResp{

}