package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/18 11:07
 * @Description:
 */
@Getter
@Setter
@ToString
public class company_info implements Serializable {
    private Integer id;

    private String companyID;

    private String parentID;

    private Integer lft;

    private Integer rgt;

    private String companyName;

    private String companyPhone;

    private String companyAddress;

    private Integer companyType;

    private String companyManagerID;
}
