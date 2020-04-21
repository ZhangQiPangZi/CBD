package com.cbd.cbdcommoninterface.response.salesapp.workbench;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Monster
 * @date: 2020/4/20 22:43
 * @Description
 */
@Data
public class InstallerInfoVO implements Serializable {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 距离
     */
    private Double distance;
    /**
     * 能力等级
     */
    private Integer level;
}
