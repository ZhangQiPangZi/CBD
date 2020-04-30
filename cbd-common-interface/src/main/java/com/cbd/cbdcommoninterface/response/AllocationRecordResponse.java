package com.cbd.cbdcommoninterface.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AllocationRecordResponse {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date optTime;
    private String srcCompanyName;
    private String dstCompanyName;
    private String srcManagerName;

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getSrcCompanyName() {
        return srcCompanyName;
    }

    public void setSrcCompanyName(String srcCompanyName) {
        this.srcCompanyName = srcCompanyName;
    }

    public String getDstCompanyName() {
        return dstCompanyName;
    }

    public void setDstCompanyName(String dstCompanyName) {
        this.dstCompanyName = dstCompanyName;
    }

    public String getSrcManagerName() {
        return srcManagerName;
    }

    public void setSrcManagerName(String srcManagerName) {
        this.srcManagerName = srcManagerName;
    }
}
