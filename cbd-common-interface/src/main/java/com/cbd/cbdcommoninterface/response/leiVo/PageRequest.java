package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shy_black
 * @date 2020/4/28 23:06
 * @Description:
 */
@Getter
@Setter
@ToString
public class PageRequest {


    private int pageNum;

    private int pageSize;

    private String companyID;
}
