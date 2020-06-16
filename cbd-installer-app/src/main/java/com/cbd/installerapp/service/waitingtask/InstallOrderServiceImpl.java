package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.InstallOrderService;
import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ICarInfoService;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.DevIdDO;
import com.cbd.installerapp.dao.waitingtask.InstallOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Monster
 * @date: 2020/4/22 16:54
 * @Description
 */
public class InstallOrderServiceImpl implements InstallOrderService {
    @Autowired
    private InstallOrderDao installOrderDao;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ICarInfoService iCarInfoService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int installOrderComplete(Integer installerId,Integer orderId){
        //先查找出安装工没有安装的设备 然后在改变orderinfo里的信息
        DevIdDO temp = installOrderDao.findDevId(installerId,orderId);

        int id = temp.getId();
        String devId = temp.getDevId();
        String simId = temp.getSimId();
        installOrderDao.installOrderComplete(devId,simId,orderId);

        //更新car_info的devId 像track中初始化一条数据
        iCarInfoService.function();
        installOrderDao.updateState(id);

        //调用张祺的设备状态更改 入参为devId 和devStatus
        deviceService.updateDevStatusByDevIDAndDevStatus(devId, DeviceInfo.DevStatus.USE.ordinal());

        return installOrderDao.complete(orderId);

    }

    @Override
    public int inputPicture(String url, Integer id) {
        return installOrderDao.inputPicture(url,id);
    }
}
