package com.black.lei.service.Impl;


import com.black.lei.beans.power;
import com.black.lei.beans.role;
import com.black.lei.dao.role_powerDao;
import com.black.lei.dao.role_userDao;
import com.black.lei.dao.userDao;
import com.black.lei.service.IRoleDefineService;
import com.black.lei.vo.RoleResponseVo;
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
    private userDao userDao;

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

        log.info("获得的角色id和名称为："+roleList.toString());

        List<RoleResponseVo> resList = new ArrayList<RoleResponseVo>();
        Iterator<role> it = roleList.iterator();
        while(it.hasNext()){
            //执行过程中会执行数据锁定，性能稍差，若在循环过程中要去掉某个元素只能调用iter.remove()方法。
            //新建一个resList的RoleResponseVo对象，取出roleID，roleName，放入resList中
            role tmpRole = it.next();

            RoleResponseVo rrv = new RoleResponseVo();
            rrv.setRoleID(tmpRole.getRoleID());
            rrv.setRoleName(tmpRole.getRoleName());
            rrv.setData(roleDao.getPowerByRoleID(tmpRole.getRoleID()));

            resList.add(rrv);

        }

        log.info("得到的角色->权限数据结构为"+resList.toString());

        //得到了所有的角色和权限，且数据结构为角色->权限：一对多
        return resList;

        //遍历角色，根据角色id找权限
        //返回属性：List<>roleID，roleName，powerID，powerName，status-----data
    }

    @Override
    @Transactional
    public List<SimpleGrantedAuthority> getUserRoleByID(Integer ID) {
        List<Integer> roleID = role_userDao.getRolesByUserID(ID);
        if(roleID == null) {
            log.info("该用户未拥有任何角色");
            return null;
        }
        //List<String> tmp = new ArrayList<>();
        List<SimpleGrantedAuthority> sga = new ArrayList<>();
        for(Integer i : roleID) {
            sga.add(roleDao.getRoleNameByRoleID(i));
        }

        return sga;

    }

    //添加角色
    //@Transactional(rollbackOn = { Exception.class })
    public boolean createRole(role addroleDefine) {
        boolean success = false;

        try {
            //将新增角色存入数据库
            roleDao.createRole(addroleDefine);
            success = true;
        } catch (Exception e) {
            System.out.println("Exception e = " + e);
            //TODO
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            success = false;
        }

        return success;
    }

    //更新角色
    @Override
    public Integer updateRole(int roleID,int powerID,int status) {

            return role_powerDao.updateRolePowerStatus(roleID,powerID,status);

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
}
