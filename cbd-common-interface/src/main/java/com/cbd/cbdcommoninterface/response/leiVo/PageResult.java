package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/28 23:08
 * @Description:
 */
@Getter
@Setter
@ToString
public class PageResult implements Serializable {

    //当前页码
    private int pageNum;
    //每页数量
    private int pageSize;
    //数据总量
    private long totalSize;
    //总页码
    private int totalPages;
    //数据集合
    private List<?> data;

}
