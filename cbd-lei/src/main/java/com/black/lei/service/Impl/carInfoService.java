package com.black.lei.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.black.lei.beans.car_info;
import com.black.lei.dao.TrackLastDao;
import com.black.lei.dao.car_infoDao;
import com.black.lei.dao.userDao;
import com.black.lei.service.ICarInfoService;
import com.black.lei.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/23 14:50
 * @Description:
 */
@Slf4j
@Service("carInfoService")
public class carInfoService implements ICarInfoService {

    @Resource
    private car_infoDao carInfoDao;

    @Resource
    private TrackLastDao trackLastDao;

    @Resource
    private userDao userInfoDao;



    public Integer save(car_info saveCarInfo) {
        Integer res = carInfoDao.insert(saveCarInfo);
        return res;
    }

    @Transactional
    @Override
    public List<CompanyAndCarInfoResponse> getCompanyAndCarInfo(String companyID) {
        //1.查询当前公司的左值与右值
        LftAndRgtVo lftAndRgt = carInfoDao.getLftAndRgt(companyID);
        String lft = lftAndRgt.getLft();
        String rgt = lftAndRgt.getRgt();
        //2.查询当前公司及其子公司的信息及层级
        List<CompanyInfoVo> companyInfoVoList = carInfoDao.getCompanyTreeList(lft,rgt);

        //拿到的level信息：如果当前输入的公司ID的level是2，那么level的信息就是2，不会从0开始计算层级
        //log.info("拿到的公司信息为："+JSONObject.toJSON(companyInfoVoList));
        //3.逐条取公司信息
        List<CompanyAndCarInfoResponse> companyAndCarInfoResponseList = new ArrayList<>();

        Iterator<CompanyInfoVo> it = companyInfoVoList.iterator();
        while(it.hasNext()) {
            CompanyInfoVo tmpCompany = it.next();//公司信息
            //新建一个临时的返回的一个公司元素
            CompanyAndCarInfoResponse tmpCompanyAndCarInfoResponse = new CompanyAndCarInfoResponse();
            //将公司信息填入临时companyResponse
            tmpCompanyAndCarInfoResponse.setCompanyInfoVo(tmpCompany);
            String curCompanyID = tmpCompany.getCompanyID();
            //查询公司下属车辆信息--根据公司id查询车辆信息及人员信息
            //c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat,MAX(t.nTime) AS nTime
            List<CarForTreeVo> carForTreeVoList = carInfoDao.getUserForCarVo(curCompanyID);

            log.info("查询到的车辆信息为："+carForTreeVoList.toString());

            //获得了该公司旗下的车辆及人员信息列表，填入tmpCompanyResponse中
            tmpCompanyAndCarInfoResponse.setCarForTreeVo(carForTreeVoList);

            //信息填入完毕，循环，填入下一个公司及其旗下车辆信息
            companyAndCarInfoResponseList.add(tmpCompanyAndCarInfoResponse);
        }

        //log.info("companyAndCarInfoResponseList信息："+companyAndCarInfoResponseList.toString());
        return companyAndCarInfoResponseList;
    }


    public car_info findById(String strCarUUID) {

        return carInfoDao.findById(strCarUUID);
    }
    //1.在car_info里通过devID找到owerID
    //2.在userID中通过owerID找出用户基础信息
    //3.将用户基础信息放入json里，返回
    public Map<String,Object> findUserInfoByDevID(String devID) {
        JSONObject json = new JSONObject();

        String owerID = carInfoDao.findOwerIDByDevID(devID);

        log.info("owerID = "+ owerID);

        Map<String,Object> userBaseInfo = userInfoDao.findUserBaseInfoByOwerID(owerID);
        userBaseInfo.put("carPlateNum",carInfoDao.getCarPlateNumByOwerID(owerID));
        log.info("baseUserInfo = " + userBaseInfo.toString());
        return userBaseInfo;
    }

    @Override
    public List<findCarVo> findCarByOwner(String ompanyID, String serarchKey) {
        return null;
    }

    public boolean update(car_info saveCarInfo) {
        carInfoDao.update(saveCarInfo);
        return true;
    }

    public List<Map<String, String>> findCarListBystrTEID(String strTEID) {
        return carInfoDao.findCarListBystrTEID(strTEID);
    }

    public car_info findCarbyOwnerID(String strPersonID) {
        return carInfoDao.findCarbyOwnerID(strPersonID);
    }

//    public List<CarForTreeVo> findCarByOwner(String strCompanyID, String serarchKey) {
//        String whereString = " AND ( d.strName like '%" + serarchKey + "%' or b.strTEID like '%" + serarchKey
//                + "%' or date_format(td.nInstallTime,'%Y-%m-%d') like '%" + serarchKey + "%' or d.strPhone like '%"
//                + serarchKey + "%' or d.strCarrVIN like '%" + serarchKey + "%')";
//        Map<String, String> map = new HashMap<>();
//        map.put("strCompanyID", strCompanyID);
//        map.put("strTableList",
//                " TempCompany AS a , Tlb_CarAndDeviceRefer AS b,  Tlb_CarInfo AS d  ,Tlb_DeviceInstallLog td ");
//        map.put("strFieldList",
//                "d.strName, a.strCompanyName , d.strPhone, d.strCarPlateNum , d.nInitMileage ,b.nDevType, b.nCarSizeType,b.nServiceState, b.strTEID,b.strCompanyID,date_format(td.nInstallTime,'%Y-%m-%d') as nInstallTime ");
//        map.put("strWhere", " a.strCompanyID = b.strCompanyID AND b.strCarUUID=d.strCarUUID and td.strTEID = b.strTEID "
//                + whereString);
//        List<Map<String, Object>> tmpList = commonCallProcDao.getDbList(map);
//        if (tmpList == null)
//            return null;
//
//        // 获取最后轨迹信息
//        List<TrackLast> trackLastList = trackLastDao.getTrackLastInfo();
//
//        if (tmpList != null) {
//            for (Map<String, Object> devListMap : tmpList) {
//                String strTEID = devListMap.get("strTEID").toString();
//                // boolean flag = false;
//                // 遍历最后轨迹表来判断设备状态
//                for (int i = 0; i < trackLastList.size(); i++) {
//                    TrackLast trackLastInfo = trackLastList.get(i);
//                    int nTime = trackLastInfo.getNTime();
//                    Short nSpeed = trackLastInfo.getSpeed();
//                    int nLineLastTime = CommonFunction.daoInterface.getCurrentUtcTime() - nTime;
//                    // 判断设备是否有轨迹，没有则长时间离线
//                    if (strTEID.equals(trackLastInfo.getDevID())) {
//                        devListMap.put("nSpeed", nSpeed);
//                        devListMap.put("nLineLastTime", nLineLastTime);
//                    }
//                }
//            }
//        }
//
//        return tmpList;
//    }


}

