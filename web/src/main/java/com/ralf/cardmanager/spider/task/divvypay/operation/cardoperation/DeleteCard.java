package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class DeleteCard extends BaseDivvyOperation<DeleteCardRsp> {
    public DeleteCard init(String cardId) throws Exception {
        super.init(new String[]{cardId});
        return this;
    }

    public DeleteCard(DivvyPaySiteConfig config) {
        super(config);
        defaultHeader.put("referer", "https://app.divvy.co/cards");
        body = "{\"operationName\":\"DeleteVirtualCard\",\"variables\":{\"input\":{\"cardId\":\"%s\",\"clientMutationId\":\"0\"}},\"query\":\"mutation DeleteVirtualCard($input: DeleteCardInput) {\\n  deleteCard(input: $input) {\\n    deletedCardId\\n    __typename\\n  }\\n}\\n\"}";
    }

    @Override
    public DeleteCardRsp persistent(String rsp) throws Exception {
        //data.deleteCard.deletedCardId
        val jsonObject = new JsonParser().parse(rsp).getAsJsonObject();
        val deleteCardId = jsonObject.get("data").getAsJsonObject().get("deleteCard").getAsJsonObject().get("deletedCardId").getAsString();
        return new DeleteCardRsp(deleteCardId);
    }
}
