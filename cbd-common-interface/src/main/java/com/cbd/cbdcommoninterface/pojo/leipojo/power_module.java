package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shy_black
 * @date 2020/4/18 11:14
 * @Description:
 */
@Getter
@Setter
@ToString
public class power_module {
    private Integer powerModuleID;
    private String moduleName;
    private Integer parentID;
    private Integer level;
    private String remark;
}
