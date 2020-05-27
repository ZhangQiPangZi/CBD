package com.cbd.cbdcommoninterface.response.leiVo;

import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/1 0:10
 * @Description:
 *     //返回值为role列表，每个role下属的权限，及权限的状态
 */
@Getter
@Setter
@ToString
public class RoleResponseVo implements Serializable {
    //roleID
    private int roleID;
    //角色名称
    private String roleName;
    //数据集合---权限名称+权限状态
    private List<power> data;
}
