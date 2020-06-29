package com.black.lei.service.Impl;

import com.black.lei.dao.powerDao;
import com.black.lei.dao.role_powerDao;
import com.cbd.cbdcommoninterface.cbd_interface.user.IPowerService;
import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/22 12:46
 * @Description:
 */
@Service
public class PowerService implements IPowerService {

    @Resource
    private powerDao powerDao;

    @Resource
    private role_powerDao role_powerDao;

    public List<Integer> getRolerOfPower(int i, String j) {
        return null;
    }

    @Override
    public List<power> getPowerList() {
        List<power> resList = powerDao.getAllPowerList();
        if(resList == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return resList;
    }

    @Override
    public List<power> findLikelyPower(String key) {
        return powerDao.findLikelyPower(key);
    }

    @Override
    public Integer addPower(power power) {
        return powerDao.addPower(power);

    }
    @Override
    public Integer updatePower(power power) {
        //当修改权限表时，角色——权限表的状态也需要改变
        //update为开启->关闭时，全部关闭
        //关闭->开启时，无须改变角色-权限表的状态
        Integer curPowerID = power.getPowerID();
        if(power.getStatus() == 0) {
            //改角色-权限的状态
            role_powerDao.updateRolePowerWithPowerTableByPowerID(curPowerID);
        }
        //更新权限信息
        return powerDao.updatePower(power);

    }
    @Override
    public Integer deletePower(power power) {
        return powerDao.deletePower(power);
    }


    //nPowerID,Integer.parseInt(nRoleID),strCompanyID,method
    public void setupPowerForRole(int powerID,int roleID,String companyID, String method) {

        return;
    }

    public List<power> getPowerListByUserID(Integer userID){
        return powerDao.getPowerListByUserID(userID);
    }

    @Override
    public List<power> getPowerListByRoleID(Integer roleID) {
        //根据roleID查powerName
        List<Integer> powerIDList = role_powerDao.getPowerByRoleID(roleID);

        //拿到了powerID，去取power信息
        Iterator<Integer> it = powerIDList.iterator();

        List<power> powerList = new ArrayList<>();

        while(it.hasNext()) {

            Integer curPowerID = it.next();
            powerList.add(powerDao.getPowerByPowerID(curPowerID));

        }

        return powerList;
    }


}
