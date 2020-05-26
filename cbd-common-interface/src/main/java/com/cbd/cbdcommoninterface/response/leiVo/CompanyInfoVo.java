package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/5/3 11:41
 * @Description:
 * D.id,D.companyID,D.companyName,D.parentID,D.lft,D.rgt,level
 */
@Getter
@Setter
@ToString
public class CompanyInfoVo implements Serializable {
    private String id;

    private String companyID;

    private String companyName;

    private String parentID;

    private String lft;

    private  String rgt;

    private String level;

}
