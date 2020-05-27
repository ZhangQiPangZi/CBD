package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/5/3 12:03
 * @Description:
 */
@Getter
@Setter
@ToString
public class LftAndRgtVo implements Serializable {
    private String lft;
    private String rgt;
}
