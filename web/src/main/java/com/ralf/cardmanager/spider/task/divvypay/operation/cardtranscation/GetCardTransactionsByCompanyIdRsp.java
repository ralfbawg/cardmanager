package com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation;

        import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
        import lombok.AllArgsConstructor;
        import lombok.Data;

        import java.util.List;

@Data
@AllArgsConstructor
public class GetCardTransactionsByCompanyIdRsp extends BaseDivvyOpertionResp {
    private boolean hasNextPage;
    private String endCursor;//解码后
    private List<GetCardTransactionsByCompanyIdRspDetail> list;

}
