package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.RemoveOrderService;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import com.cbd.installerapp.dao.waitingtask.InstallOrderDao;
import com.cbd.installerapp.dao.waitingtask.RemoveOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Monster
 * @date: 2020/4/22 11:15
 * @Description
 */
public class RemoveOrderServiceImpl implements RemoveOrderService {
    @Autowired
    private RemoveOrderDao removeOrderDao;
    @Autowired
    private InstallOrderDao installOrderDao;
    @Autowired
    private DeviceService deviceService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int removeDev(RemoveQuery query){
        //拆除设备前先找到对应的设备id
        String devId = removeOrderDao.getDevId(query.getPhoneNumber());

        //拆除设备 改变设备状态
        if("入库".equals(query.getRemoveResult())){
            deviceService.updateDevStatusByDevIDAndDevStatus(devId, DeviceInfo.DevStatus.IN.ordinal());
        }else {
            deviceService.updateDevStatusByDevIDAndDevStatus(devId, DeviceInfo.DevStatus.RETURN.ordinal());
        }
        query.setDevId(devId);
        return removeOrderDao.removeDev(query);
    }

    @Override
    public int removeOrderComplete(Integer orderId){
        return installOrderDao.complete(orderId);
    }
}
