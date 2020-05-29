package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author shy_black
 * @date 2020/5/29 14:40
 * @Description:
 */
@Data
public class BaseOrderInfoVo {

    private String orderId;

    private Integer salesId;

    private String carOwnerName;

    private String phoneNumber;

    private String licenseNumber;

    private String companyID;

    private String companyName;

    private Integer status;

    //private Integer orderTypeCode;

}
