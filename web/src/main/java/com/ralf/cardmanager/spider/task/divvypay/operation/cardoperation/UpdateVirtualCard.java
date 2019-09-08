package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope("prototype")
public class UpdateVirtualCard extends BaseDivvyOperation<UpdateVirtualCardRsp> {
    public UpdateVirtualCard init(Long amount, String cardId, String cardName) throws Exception {
        super.init(new String[]{String.valueOf(amount), config.getBudgetId(), cardId, cardName,String.valueOf(new DateTime().plusYears(3).getMillis() / 1000)});
        return this;
    }

    public UpdateVirtualCard(DivvyPaySiteConfig config) {
        super(config);
        body = "{\"operationName\":\"UpdateVirtualCard\",\"variables\":{\"input\":{\"amount\":%s,\"budgetId\":\"%s\",\"cardId\":\"%s\",\"name\":\"%s\",\"clientMutationId\":\"0\",\"recurringAmount\":0,\"expiresAt\":%s,\"selectedTags\":[{\"tagTypeId\":\"VGFnVHlwZTo3OTQw\",\"tagValueIds\":[\"VGFnVmFsdWU6NDA5NTI5\"]}]}},\"query\":\"mutation UpdateVirtualCard($input: UpdateCardInput!) {\\n  updateCard(input: $input) {\\n    card {\\n      id\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
//        body = "{"operationName":"UpdateVirtualCard","variables":{"input":{"amount":80,"budgetId":"QnVkZ2V0OjQ3MzAz","cardId":"Q2FyZDo0MDQ5NDQ=","name":"ceshiforcreate","clientMutationId":"0","recurringAmount":0,"expiresAt":1662047999,"selectedTags":[{"tagTypeId":"VGFnVHlwZTo3OTQw","tagValueIds":["VGFnVmFsdWU6NDA5NTI5"]}]}},"query":"mutation UpdateVirtualCard($input: UpdateCardInput!) {\n  updateCard(input: $input) {\n    card {\n      id\n      __typename\n    }\n    __typename\n  }\n}\n"}";
        defaultHeader.put("referer", "https://app.divvy.co/cards");
    }

    //返回 获取allocationId
    //{"data":{"updateCard":{"card":{"id":"Q2FyZDo0MTc1MjM=","__typename":"Card"},"__typename":"UpdateCardPayload"}}}
    @Override
    public UpdateVirtualCardRsp persistent(String rsp) {
        //data.updateCard.card.id
        val jsonObject = new JsonParser().parse(rsp).getAsJsonObject();
        String updateCardId = jsonObject.get("data").getAsJsonObject().get("updateCard").getAsJsonObject().get("card").getAsJsonObject().get("id").getAsString();
        return new UpdateVirtualCardRsp(updateCardId);

    }
}
