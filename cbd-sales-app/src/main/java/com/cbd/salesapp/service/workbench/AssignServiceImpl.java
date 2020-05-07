package com.cbd.salesapp.service.workbench;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.AssignService;
import com.cbd.cbdcommoninterface.pojo.salesapp.workbench.InstallerInfoDO;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.AssignQuery;
import com.cbd.cbdcommoninterface.response.salesapp.workbench.InstallerInfoVO;
import com.cbd.cbdcommoninterface.utils.LocationUtils;
import com.cbd.salesapp.dao.workbench.AssignDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/20 22:27
 * @Description
 */
public class AssignServiceImpl implements AssignService {
    @Autowired
    private AssignDao assignDao;

    @Override
    public List<InstallerInfoVO> orderByLevel(AssignQuery query){
        List<InstallerInfoDO> temp = assignDao.orderByLevel(query);
        List<InstallerInfoVO> result = new ArrayList<>();

        temp.stream().forEach(inn ->{
            InstallerInfoVO info = new InstallerInfoVO();
            Double distance;
            info.setId(inn.getId());
            info.setName(inn.getName());
            info.setLevel(inn.getLevel());
            distance = LocationUtils.getDistance(query.getLongitude(),query.getLatitude(),inn.getLongitude(),inn.getLatitude())/1000;
            info.setDistance(distance);
            result.add(info);
        });

        return result;
    }

    @Override
    public List<InstallerInfoVO> orderByDistance(AssignQuery query){
        List<InstallerInfoDO> list = assignDao.orderByDistance(query);
        List<InstallerInfoVO> resultList = new ArrayList<>();
        list.stream().forEach(inn ->{
            InstallerInfoVO info = new InstallerInfoVO();
            Double distance;
            info.setId(inn.getId());
            info.setName(inn.getName());
            info.setLevel(inn.getLevel());
            distance = LocationUtils.getDistance(query.getLongitude(),query.getLatitude(),inn.getLongitude(),inn.getLatitude())/1000;
            info.setDistance(distance);
            resultList.add(info);
        });

        if(query.getFlag()){
            //根据距离进行升序排序
            Collections.sort(resultList, new Comparator<InstallerInfoVO>() {

                @Override
                public int compare(InstallerInfoVO info1, InstallerInfoVO info2) {
                    // 按照距离进行升序排序
                    if (info1.getDistance() > info2.getDistance()) {
                        return 1;
                    }
                    if (info1.getDistance().equals(info2.getDistance())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }else {
            //按照距离进行降序排序
            Collections.sort(resultList, new Comparator<InstallerInfoVO>() {

                @Override
                public int compare(InstallerInfoVO info1, InstallerInfoVO info2) {

                    // 按照学生的年龄进行降序排列
                    if (info1.getDistance() > info2.getDistance()) {
                        return -1;
                    }
                    if (info1.getDistance().equals(info2.getDistance())) {
                        return 0;
                    }
                    return 1;
                }
            });
        }
        return resultList;
    }

    @Override
    public int assignInstaller(AssignQuery query){
        //指派后加入到安装工的任务列表
        assignDao.insertToTaskList(query);
        //除拆除订单外 工程师都持有新设备
        if (query.getOrderTypeCode()!=3){
            assignDao.installerHasDev(query.getId(),query.getDevId(),query.getSimId(),query.getInstallerId());
        }
        return assignDao.assignInstaller(query);
    }

    @Override
    public int assignLater(Integer id){
        return assignDao.assignLater(id);
    }
}
