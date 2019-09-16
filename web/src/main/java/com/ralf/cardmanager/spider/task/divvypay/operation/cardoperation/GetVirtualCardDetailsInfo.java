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

import java.io.IOException;

/**
 * @program: cardmanager
 * @description: 获取卡详细信息及状态
 * @author: Ralf Chen
 * @create: 2019-09-04 10:59
 **/
@Service
@Scope("prototype")
public class GetVirtualCardDetailsInfo extends BaseDivvyOperation<GetVirtualCardDetailsInfoRsp> {
    public GetVirtualCardDetailsInfo init(String cardId) throws Exception {
        super.init(new String[]{cardId});
        return this;
    }

    public GetVirtualCardDetailsInfo(DivvyPaySiteConfig config) {
        super(config);
        defaultHeader.put("referer", "https://app.divvy.co/cards");
        body = "{\"operationName\":\"GetVirtualCardDetailsInfo\",\"variables\":{\"cardId\":\"%s\"},\"query\":\"query GetVirtualCardDetailsInfo($cardId: ID!) {\\n  node(id: $cardId) {\\n    ... on Card {\\n      id\\n      frozen\\n      name\\n      deleted\\n      budget {\\n        id\\n        name\\n        __typename\\n      }\\n      assignedTagValues(first: 1000) {\\n        edges {\\n          node {\\n            id\\n            tagType {\\n              deleted\\n              id\\n              name\\n              __typename\\n            }\\n            tagValue {\\n              id\\n              value\\n              __typename\\n            }\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      userAllocation {\\n        id\\n        budget {\\n          name\\n          id\\n          __typename\\n        }\\n        __typename\\n      }\\n      user {\\n        id\\n        displayName\\n        avatarUrl\\n        company {\\n          id\\n          address {\\n            city\\n            state\\n            street1\\n            street2\\n            zipCode\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
    }

    //返回 {"data":{"node":{"userAllocation":{"id":"VXNlckFsbG9jYXRpb246NDY3ODcz","budget":{"name":"ceshi","id":"QnVkZ2V0OjQ3MzAz","__typename":"Budget"},"__typename":"UserAllocation"},"user":{"id":"VXNlcjo1MTIzNg==","displayName":"MING SITK","company":{"id":"Q29tcGFueTozMDI3","address":{"zipCode":"91701","street2":null,"street1":"7029 Novara Pl","state":"CA","city":"Rancho Cucamonga","__typename":"Address"},"__typename":"Company"},"avatarUrl":null,"__typename":"User"},"name":"cavy","id":"Q2FyZDozNjMzMDU=","frozen":false,"deleted":false,"budget":{"name":"ceshi","id":"QnVkZ2V0OjQ3MzAz","__typename":"Budget"},"assignedTagValues":{"edges":[{"node":{"tagValue":{"value":"Entertainment","id":"VGFnVmFsdWU6NDA5NTMw","__typename":"TagValue"},"tagType":{"name":"Category","id":"VGFnVHlwZTo3OTQw","deleted":false,"__typename":"TagType"},"id":"Q2FyZFRhZ1ZhbHVlQXNzaWdubWVudDozNzI0Mjg=","__typename":"CardTagValueAssignment"},"__typename":"CardTagValueAssignmentEdge"}],"__typename":"CardTagValueAssignmentConnection"},"__typename":"Card"}}}
    @Override
    public GetVirtualCardDetailsInfoRsp persistent(String rsp) {
        JsonObject jsonObject = new JsonParser().parse(rsp).getAsJsonObject().get("data").getAsJsonObject().get("node").getAsJsonObject();
        boolean deleted = jsonObject.get("deleted").getAsBoolean();
        boolean frozen = jsonObject.get("frozen").getAsBoolean();
        String allocationId = jsonObject.get("userAllocation").getAsJsonObject().get("id").getAsString();
        return new GetVirtualCardDetailsInfoRsp(frozen, deleted, allocationId);
    }
}

