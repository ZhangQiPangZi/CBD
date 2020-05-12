package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.RepairOrderService;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.DevIdDO;
import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import com.cbd.installerapp.dao.waitingtask.InstallOrderDao;
import com.cbd.installerapp.dao.waitingtask.RemoveOrderDao;
import com.cbd.installerapp.dao.waitingtask.RepairOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private DeviceService deviceService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int changeDev(RemoveQuery query){
        //拆除前获取要拆除的devId
        String devId = removeOrderDao.getDevId(query.getPhoneNumber());

        //将车辆与设备解绑(安装单)
        repairOrderDao.changeDev(query.getPhoneNumber());

        //拆除设备的去向
        if("入库".equals(query.getRemoveResult())){
            deviceService.updateDevStatusByDevIDAndDevStatus(devId, DeviceInfo.DevStatus.IN.ordinal());
        }else {
            deviceService.updateDevStatusByDevIDAndDevStatus(devId, DeviceInfo.DevStatus.RETURN.ordinal());
        }

        query.setDevId(devId);
        //调用拆除接口
        return removeOrderDao.removeDev(query);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int orderComplete(Integer flag,Integer orderId,String phoneNumber,Integer installerId){

        //先查找出还未使用的设备信息(新设备)
        DevIdDO temp = installOrderDao.findDevId(installerId,orderId);
        String devId = temp.getDevId();
        String simId = temp.getSimId();
        Integer id = temp.getId();

        //设备拆除成功
        if(flag==1){
            //再次绑定
            repairOrderDao.reInstall(devId,simId,phoneNumber);
            //工程师带的新设备状态为使用
            deviceService.updateDevStatusByDevIDAndDevStatus(devId, DeviceInfo.DevStatus.USE.ordinal());
            //工程师拥有的新设备使用状态改变
            installOrderDao.updateState(id);
        }else {
            //修好旧设备 新设备没有使用 状态变为入库
            deviceService.updateDevStatusByDevIDAndDevStatus(devId, DeviceInfo.DevStatus.IN.ordinal());
        }

        return installOrderDao.complete(orderId);
    }
}
