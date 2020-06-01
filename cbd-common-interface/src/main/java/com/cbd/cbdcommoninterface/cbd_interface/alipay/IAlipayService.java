package com.cbd.cbdcommoninterface.cbd_interface.alipay;

/**
 * @author shy_black
 * @date 2020/5/29 13:25
 * @Description:
 */
public interface IAlipayService {

    //支付信息校验成功后执行，向car_info中添加车辆信息
    //向payrecord中添加信息
    Integer handleOrderMsg(String orderID);

}
