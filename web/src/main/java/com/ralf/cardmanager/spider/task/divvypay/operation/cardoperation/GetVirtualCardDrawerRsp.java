package com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation;

import com.ralf.cardmanager.spider.task.divvypay.operation.base.BaseDivvyOpertionResp;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: cardmanager
 * @description: 响应
 * @author: Ralf Chen
 * @create: 2019-10-25 11:05
 **/
@Data
@AllArgsConstructor
public class GetVirtualCardDrawerRsp extends BaseDivvyOpertionResp {
    String activationStatus;
    String userAllocationId;
    boolean blocked;
    boolean frozen;
    Long totalCleared;
    Long availableFunds;
    Long totalPending;

}
