package com.black.lei.service.Impl;


import com.black.lei.dao.role_powerDao;
import com.black.lei.dao.role_userDao;
import com.black.lei.dao.userDao;

import com.cbd.cbdcommoninterface.cbd_interface.user.IRoleDefineService;
import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.response.leiVo.RoleResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author shy_black
 * @date 2020/4/22 12:45
 * @Description:100
 */

@Slf4j
@Service
public class roleDefineService implements IRoleDefineService {

    @Resource
    private com.black.lei.dao.roleDao roleDao;

    @Resource
    private role_userDao role_userDao;

    @Resource
    private com.black.lei.dao.powerDao powerDao;

    @Resource
    private role_powerDao role_powerDao;


    //获取所有角色及其拥有的权限及状态
    @Override
    public List<RoleResponseVo> getRoleDefines() {

        //获得所有角色
        //返回roleID，roleName
        List<role> roleList = roleDao.getAllRoles();

        log.info("获得的角色id和名称为：" + roleList.toString());

        List<RoleResponseVo> resList = new ArrayList<>();
        Iterator<role> it = roleList.iterator();
        while (it.hasNext()) {
            //执行过程中会执行数据锁定，性能稍差，若在循环过程中要去掉某个元素只能调用iter.remove()方法。
            //新建一个resList的RoleResponseVo对象，取出roleID，roleName，放入resList中
            role tmpRole = it.next();

            RoleResponseVo rrv = new RoleResponseVo();
            rrv.setRoleID(tmpRole.getRoleID());
            rrv.setRoleName(tmpRole.getRoleName());
            rrv.setData(roleDao.getPowerByRoleID(tmpRole.getRoleID()));

            if (rrv.getData().size() == 0) {
                List<power> emptPower = new ArrayList<>();
                power tmpPower = new power();
                tmpPower.setPowerName("");
                emptPower.add(tmpPower);
                rrv.setData(emptPower);
            }
            resList.add(rrv);

        }

        log.info("得到的角色->权限数据结构为" + resList.toString());

        //得到了所有的角色和权限，且数据结构为角色->权限：一对多
        return resList;

        //遍历角色，根据角色id找权限
        //返回属性：List<>roleID，roleName，powerID，powerName，status-----data
    }

    @Override
    @Transactional
    public List<role> getUserRoleByID(Integer ID) {
        List<role> roleList = role_userDao.getRolesByUserID(ID);
        if (roleList == null) {
            log.info("该用户未拥有任何角色");
            return null;
        }

        return roleList;

    }

    //添加角色
    //@Transactional(rollbackOn = { Exception.class })
    @Transactional
    public Integer addRole(String roleName, String remark, List<Integer> powerIDList) {
        Integer success1 = 0;

        //将新增角色存入角色表
        success1 = roleDao.createRole(roleName, remark);
        //查出角色的ID，用来向role_power中添加信息
        Integer curRoleID = roleDao.getRoleIDByRoleName(roleName);
        Iterator<Integer> it = powerIDList.iterator();
        while (it.hasNext()) {
            Integer success2 = 0;
            Integer curPowerID = it.next();
            success2 = role_powerDao.addPowerByRoleID(curRoleID,curPowerID,1);
            if (success2 == 0) {
                //TODO 添加失败，回滚
                return 0;
            }

        }
        return success1;
    }


    //更新角色
    @Override
    public Integer updateRole(int roleID, int powerID, int status) {

        return role_powerDao.updateRolePowerStatus(roleID, powerID, status);

    }

    //删除指定角色
    @Override
    public void deleteRole(int nRoleID) {
        roleDao.deleteRole(nRoleID);
    }

    //获取该公司类别所有权限
    @Override
    public List<power> getPowerListByCompanyType(int companyType) {

        return powerDao.getPowerListByCompanyType(companyType);
    }

    @Override
    public Integer addRolePower(Integer roleID, Integer powerID) {

        return role_powerDao.addPowerByRoleID(roleID, powerID, 1);
    }
}
