package com.cbd.cbdcommoninterface.cbd_interface.user;

import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/22 12:46
 * @Description:
 */
@Component
public interface IPowerService {


    List<power> getPowerList();

    List<power> findLikelyPower(String key);

    Integer addPower(power power);

    Integer updatePower(power power);

    Integer deletePower(power power);



    /**
         * 给角色分配权限
         * @author wcj
         * @param nPowerID
         * @param nRoleID
         * @param strCompanyID
         * @param method
         */
    void setupPowerForRole(int nPowerID, int nRoleID, String strCompanyID, String method);

    List<power> getPowerListByUserID(Integer userID);


}
