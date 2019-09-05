package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ralf.cardmanager.spider.task.divvypay.config.DivvyPaySiteConfig;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOperation;
import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class GetCardinfo extends BaseDivvyOperation<GetCardinfoRsp> {

    public GetCardinfo init(String url) throws Exception{
        super.url = url;
        defaultHeader.put("path", url.replace("https://app.divvy.co", ""));
        method = "get";
        super.init();
        return this;
    }

    public GetCardinfo(DivvyPaySiteConfig config) {
        super(config);
        defaultHeader.put("referer", "https://app.divvy.co/cards");
        defaultHeader.put("method", "GET");

    }

    //{"expirationDate":"202409","cvv":"973","cardNumber":"5532320978866265"}
    @Override
    public GetCardinfoRsp persistent(String rsp) {
        JsonObject jsonObject = new JsonParser().parse(rsp).getAsJsonObject();
        String exp = jsonObject.get("expirationDate").getAsString();
        String cvv = jsonObject.get("cvv").getAsString();
        String cardNo = jsonObject.get("cardNumber").getAsString();
        return new GetCardinfoRsp(cvv, exp, cardNo);
    }
}

