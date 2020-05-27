package com.cbd.cbdcommoninterface.response.leiVo;

import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/26 9:39
 * @Description:
 */

@Getter
@Setter
@ToString
public class UserBaseInfoAndPowerInfoVo extends UserResponseVo implements Serializable {

    private List<role> RoleList;

}
