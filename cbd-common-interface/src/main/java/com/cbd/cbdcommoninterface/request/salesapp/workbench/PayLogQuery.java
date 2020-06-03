package com.cbd.cbdcommoninterface.request.salesapp.workbench;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/5/31 15:49
 * @Description
 */
@Data
public class PayLogQuery implements Serializable {
    @ApiModelProperty(value = "uuid",required = true)
    private String orderId;
    @ApiModelProperty(value = "支付时间",required = true)
    private String payTime;
    @ApiModelProperty(value = "支付结果",required = true)
    private String payResult;
    @ApiModelProperty(value = "服务开始时间",required = true)
    private String serviceStartTime;
    @ApiModelProperty(value = "服务年限",required = true)
    private String serviceLimitYear;
    @ApiModelProperty(value = "服务费用",required = true)
    private String orderPrice;
    @ApiModelProperty(value = "销售人员id",required = true)
    private Integer salesId;
    @ApiModelProperty(value = "合同id",required = true)
    private String contractId;
    @ApiModelProperty(value = "公司id",required = true)
    private String companyId;
}
