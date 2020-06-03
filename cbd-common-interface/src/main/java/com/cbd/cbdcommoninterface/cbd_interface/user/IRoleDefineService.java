package com.cbd.cbdcommoninterface.cbd_interface.user;



import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.response.leiVo.RoleResponseVo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/22 12:44
 * @Description:
 */
@Component
public interface IRoleDefineService {
    /**
     * 获取所有角色
     * @return
     */
    List<RoleResponseVo> getRoleDefines();

    /**
     * 获取当前用户的角色
     * @return
     */
    List<role> getUserRoleByID(Integer ID);


    Integer addRole(String roleName ,String remark,List<Integer> powerID);


    Integer updateRole(int roleID, int powerID, int status);


    Integer deleteRole(Integer roleID);


    List<power> getPowerListByCompanyType(int companyType);

    /**
     * 添加角色中的权限
     * @param roleID
     * @param powerID
     * @return
     */
    Integer addRolePower(Integer roleID,Integer powerID);

    Integer updateRole(Integer roleID,String roleName);

}
