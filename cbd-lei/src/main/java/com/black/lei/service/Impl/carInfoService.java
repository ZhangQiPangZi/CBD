package com.black.lei.service.Impl;


import com.alibaba.fastjson.JSONObject;

import com.black.lei.dao.TrackLastDao;
import com.black.lei.dao.car_infoDao;
import com.black.lei.dao.orderInfoDao;
import com.black.lei.dao.userDao;
import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ICarInfoService;
import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;
import com.cbd.cbdcommoninterface.pojo.leipojo.car_info;
import com.cbd.cbdcommoninterface.response.leiVo.*;
import com.cbd.cbdcommoninterface.utils.RandomTrackLastUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
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
    private orderInfoDao orderInfoDao;

    @Resource
    private userDao userInfoDao;


    /**
     * 定时任务：每天每隔1小时执行一次
     * 1.orderinfo：从orderinfo表中查orderStateTypeCode字段，得到对应的orderID与devID
     * 2.car_info：根据orderID匹配对应的行，将devID填入，并将status改为1
     * 3.track：根据devID向track表中插入一条定位数据
     */


    @Override
    public Integer hasOrderID(String orderID) {
        return carInfoDao.hasOrderID(orderID);
    }

    @Override
    @Transactional
    //每秒执行一次------------ 测试用
    //@Scheduled(cron = "*  * * * ? *")
    //每天每隔1小时执行一次-----生产用
    @Scheduled(cron = "0 0 1-23 * * ? ")
    public void function() {
        List<OrderScheduledVo> orderIDAndDevIDList = orderInfoDao.findorderIDAndDevID();

        log.info("找到了"+orderIDAndDevIDList.size()+"条待更新的数据");
        RandomTrackLastUtil randomTrackLastUtil = new RandomTrackLastUtil();

        Iterator<OrderScheduledVo> OSVit = orderIDAndDevIDList.iterator();
        while (OSVit.hasNext()) {

            OrderScheduledVo tmpOSV = OSVit.next();

            Integer updateNumber = carInfoDao.updateDevIDAndStatusByOrderID(tmpOSV);

            log.info("car_info表成功更新了"+updateNumber+"条数据");

            TrackLast trackLast = randomTrackLastUtil.createTrackLast();
            trackLast.setDevID(tmpOSV.getDevID());

            Integer addNumber = trackLastDao.addTrackData(trackLast);
            log.info("向track表中成功添加了"+addNumber+"条数据");
        }

        return ;
    }


    @Override
    public Integer save(car_info saveCarInfo) {
        Integer res = carInfoDao.insert(saveCarInfo);
        return res;
    }


    /**
     * @param companyID
     * @return 1.获取公司左右值信息，查找到当前公司及子公司的信息
     * 2.创建List<CompanyAndCarInfoResponse>，存储公司及车辆信息
     * 3.外层循环--放入公司信息
     * 4.   内层循环--放入车辆信息
     * 5.返回companyAndCarInfoResponseList
     */

    @Transactional
    @Override
    public List<CompanyAndCarInfoResponse> getCompanyAndCarInfo(String companyID) {
        //1.查询当前公司的左值与右值
        LftAndRgtVo lftAndRgt = carInfoDao.getLftAndRgt(companyID);
        String lft = lftAndRgt.getLft();
        String rgt = lftAndRgt.getRgt();
        //2.查询当前公司及其子公司的信息及层级
        List<CompanyInfoVo> companyInfoVoList = carInfoDao.getCompanyTreeList(lft, rgt);

        //拿到的level信息：如果当前输入的公司ID的level是2，那么level的信息就是2，不会从0开始计算层级
        //log.info("拿到的公司信息为："+JSONObject.toJSON(companyInfoVoList));
        //3.逐条取公司信息
        List<CompanyAndCarInfoResponse> companyAndCarInfoResponseList = new ArrayList<>();

        Iterator<CompanyInfoVo> it = companyInfoVoList.iterator();
        while (it.hasNext()) {
            CompanyInfoVo tmpCompany = it.next();//公司信息
            //新建一个临时的返回的一个公司元素
            CompanyAndCarInfoResponse tmpCompanyAndCarInfoResponse = new CompanyAndCarInfoResponse();
            //将公司信息填入临时companyResponse
            tmpCompanyAndCarInfoResponse.setCompanyInfoVo(tmpCompany);
            String curCompanyID = tmpCompany.getCompanyID();

            //查询公司下属车辆信息--根据公司id查询车辆信息及人员信息

            //解决数据库err-1055问题，拆分sql，使groupby和Max函数分离

            //先获取devID-------------c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat,MAX(t.nTime) AS nTime
            //List<CarForTreeVo> carForTreeVoList = carInfoDao.getUserForCarVo(curCompanyID);

            //得到当前公司下设备的id列表
            List<String> devIDList = carInfoDao.getDevIDListByCompanyID(curCompanyID);

            List<CarForTreeVo> carForTreeVoList = new ArrayList<>();

            //循环取出devID，用devID找到该devID的MAX(nTime)时的经纬度
            Iterator<String> devIDListIt = devIDList.iterator();
            while (devIDListIt.hasNext()) {
                String tmpDevID = devIDListIt.next();
                //查除nTime的数据
                CarForTreeVo carForTreeVo = carInfoDao.findTrackLastAndCarInfo(tmpDevID);

                //查nTime

                //根据devID查找max nTime时经纬度,返回carForTreeVo

                carForTreeVoList.add(carForTreeVo);

            }

            log.info("查询到的车辆信息为：" + carForTreeVoList.toString());

            //获得了该公司旗下的车辆及人员信息列表，填入tmpCompanyResponse中
            tmpCompanyAndCarInfoResponse.setCarForTreeVo(carForTreeVoList);

            //信息填入完毕，循环，填入下一个公司及其旗下车辆信息
            companyAndCarInfoResponseList.add(tmpCompanyAndCarInfoResponse);
        }

        //log.info("companyAndCarInfoResponseList信息："+companyAndCarInfoResponseList.toString());
        return companyAndCarInfoResponseList;
    }


    @Override
    public car_info findById(String strCarUUID) {

        return carInfoDao.findById(strCarUUID);
    }

    //1.在car_info里通过devID找到owerID
    //2.在userID中通过owerID找出用户基础信息
    //3.将用户基础信息放入json里，返回
    @Override
    public Map<String, Object> findUserInfoByDevID(String devID) {
        JSONObject json = new JSONObject();

        String owerID = carInfoDao.findOwerIDByDevID(devID);

        log.info("owerID = " + owerID);

        Map<String, Object> userBaseInfo = userInfoDao.findUserBaseInfoByOwerID(owerID);
        userBaseInfo.put("carPlateNum", carInfoDao.getCarPlateNumByOwerID(owerID));
        log.info("baseUserInfo = " + userBaseInfo.toString());
        return userBaseInfo;
    }


    @Override
    public boolean update(car_info saveCarInfo) {
        carInfoDao.update(saveCarInfo);
        return true;
    }

    public List<Map<String, String>> findCarListBystrTEID(String strTEID) {
        return carInfoDao.findCarListBystrTEID(strTEID);
    }

    /**
     * //根据车辆devID/车主姓名/电话/车牌号
     *
     * @param companyID
     * @param searchKey
     * @return
     */
    @Override
    public List<CarForTreeVo> findLikelyCarOwnerBySearchKey(String companyID, String searchKey) {

        //1.查询当前公司的左值与右值
        LftAndRgtVo lftAndRgt = carInfoDao.getLftAndRgt(companyID);
        String lft = lftAndRgt.getLft();
        String rgt = lftAndRgt.getRgt();

        //2.根据模糊key获取相关的车辆dev列表
        List<String> devIDList = carInfoDao.findLikelyDevID(lft, rgt, searchKey);

        //3.根据devID获取车辆最后定位信息及车辆信息

        List<CarForTreeVo> carForTreeVoList = new ArrayList<>();

        //循环取出devID，用devID找到该devID的MAX(nTime)时的经纬度
        Iterator<String> devIDListIt = devIDList.iterator();
        while (devIDListIt.hasNext()) {
            String tmpDevID = devIDListIt.next();

            //查询车辆最后定位信息及车辆信息
            CarForTreeVo carForTreeVo = carInfoDao.findTrackLastAndCarInfo(tmpDevID);

            carForTreeVoList.add(carForTreeVo);

        }

        return carForTreeVoList;
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

