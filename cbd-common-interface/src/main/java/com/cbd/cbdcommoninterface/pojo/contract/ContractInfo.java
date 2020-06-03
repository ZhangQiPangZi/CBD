package com.cbd.cbdcommoninterface.pojo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContractInfo implements Serializable {
    public enum ContractStatus{
        /**
         * 新建未支付
         */
        UNPAID,
        /**
         * 已支付生效中
         */
        PAID,
        /**
         * 合同已到期
         */
        EXPIRED
    }
    private String contractID;
    private String companyID;
    private String shopID;
    private Integer partyAPersonID;
    private Integer contractTypeID;
    private Float dellFee;
    private Float serverFee;
    private Float serverYears;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Integer devTypeID;
    private Integer devNums;
    private Integer contractStatus;


}
