package com.black.lei.service.Impl;

import com.black.lei.dao.car_infoDao;
import com.black.lei.dao.orderInfoDao;
import com.black.lei.dao.userDao;
import com.cbd.cbdcommoninterface.cbd_interface.alipay.IAlipayService;
import com.cbd.cbdcommoninterface.response.leiVo.BaseOrderInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shy_black
 * @date 2020/5/29 13:44
 * @Description:
 */
@Slf4j
@Service
public class alipayService implements IAlipayService {

    @Resource
    private orderInfoDao orderInfoDao;

    @Resource
    private car_infoDao car_infoDao;

    @Resource
    private userDao userDao;

    @Override
    public Integer handleOrderMsg(String orderID) {

        //1.1从orderInfo中查orderID这条数据中的信息,找 saleID对应的companyID，
        //  订单id -- 从orderInfo找数据，orderID
        //  安装时间orderTime--
        //  订单类型：orderTypeCode-- 判断是否为1，--安装单，是1时才执行
        //  销售人员：salesid -- 和user联查找出companyID，
        //  车主姓名--carOwnerName
        //  车主电话--phoneNumber
        //  车牌号--licenseNumber

        //防止回调多次插入多次数据
        Integer flag = car_infoDao.isOrderIDRepeated(orderID);

        if(flag > 0) {
            log.info("防止支付宝多次回调机制，从数据库层面上阻止");
            return flag;
        }

        log.info("-----------------开始获取BaseOrderInfoVo---------");

        log.info(orderID);
        BaseOrderInfoVo orderInfoVo = orderInfoDao.findOrderInfoByOrderID(orderID);

        orderInfoVo.setOrderId(orderID);

        //1a707b1a-39f3-4787-8af6-152e6b313cdb
        log.info("成功获取BaseOrderVo："+orderInfoVo.toString());

        //根据salesId获取companyID和companyName
        String tmpCompanyID = userDao.getCompanyIDByUserID(orderInfoVo.getSalesId());
        orderInfoVo.setCompanyID(tmpCompanyID);
        log.info("公司id为：" + tmpCompanyID);

        String tmpCompanyName = userDao.getCompanyNameByCompanyID(orderInfoVo.getCompanyID());
        orderInfoVo.setCompanyName(tmpCompanyName);
        log.info("公司名为：" + tmpCompanyName);

        orderInfoVo.setStatus(-1);

        log.info("成功获取BaseOrderVo："+orderInfoVo.toString());


        //1.2需要插入的数据信息---car_info：
        //  UUID:车辆唯一码，devID-设备号，owerID-车主id，owerName：车主姓名；companyID-公司id；companyName；公司名
        //  carPlateNum:车牌号；安装时间：orderTime；serviceStartTime ：服务开始时间-获取到devID时的时间，serviceLimitYear：2/3年
        //  phoneNum : 手机号，carPrice status 1为可用，0为无devID，需要定时从orderinfo查出
        //--------------------------------------------------------

        //
        Integer success = car_infoDao.addCarInfo(orderInfoVo);

        log.info("success = " + success);

        if(success == 0) {
            return 0;
        }

        //2.将这些数据放入carInfo中

        //3.定时任务
        //3.1还剩devID--owerID--UUID未传
        //3.2 ------给track表中插入当前devID的一条历史轨迹----track表
        //造一行假的定位数据，devID为当前devID


        //4.如果成功,返回1，else，返回0
        return success;

    }
}
