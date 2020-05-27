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
    List<SimpleGrantedAuthority> getUserRoleByID(Integer ID);


    /**
     * 添加角色
     * @author wcj
     * @param addroleDefine
     * @return
     */
    boolean createRole(role addroleDefine);

    /**
     * 更新角色
     * @author wcj
     * @param
     */
    Integer updateRole(int roleID, int powerID, int status);

    /**
     * 删除指定角色
     * @author wcj
     * @param nRoleID
     */
    void deleteRole(int nRoleID);

    /**
     * 获取该公司类别所有权限
     * @author wcj
     * @param companyType
     * @return
     */
    List<power> getPowerListByCompanyType(int companyType);

}
