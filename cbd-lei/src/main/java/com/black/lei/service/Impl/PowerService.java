package com.black.lei.service.Impl;

import com.black.lei.dao.powerDao;
import com.cbd.cbdcommoninterface.cbd_interface.user.IPowerService;
import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


}
