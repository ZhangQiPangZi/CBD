package com.cbd.cbdcommoninterface.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页请求
 */
public class PageRequest implements Serializable {
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private int pageNum;
    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量")
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
