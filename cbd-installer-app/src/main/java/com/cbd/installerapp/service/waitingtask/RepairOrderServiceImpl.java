package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.RepairOrderService;
import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import com.cbd.installerapp.dao.waitingtask.InstallOrderDao;
import com.cbd.installerapp.dao.waitingtask.RemoveOrderDao;
import com.cbd.installerapp.dao.waitingtask.RepairOrderDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Monster
 * @date: 2020/4/22 11:15
 * @Description
 */
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RemoveOrderDao removeOrderDao;
    @Autowired
    private RepairOrderDao repairOrderDao;
    @Autowired
    private InstallOrderDao installOrderDao;

    @Override
    public int changeDev(RemoveQuery query){
        //将车辆与设备解绑(安装单)
        repairOrderDao.changeDev(query.getPhoneNumber());
        //调用拆除接口
        return removeOrderDao.removeDev(query);
    }

    @Override
    public int orderComplete(Integer devId,Integer simId,Integer orderId,String phoneNumber){
        if (devId!=null&&simId!=null){
            repairOrderDao.reInstall(devId,simId,phoneNumber);
        }

        return installOrderDao.complete(orderId);
    }
}
