package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Title: AlipayVo.java
 * @Package cn.trmap.tdcloud.pay.vo
 * @Description: 支付请求参数
 * @date 2018年3月23日 上午9:00:02
 */
@Getter
@Setter
@ToString
public class AlipayVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单名称
     */
    private String subject;
    /**
     * 商户网站唯一订单号
     */
    private String out_trade_no;
    /**
     * 该笔订单允许的最晚付款时间
     */
    private String timeout_express;
    /**
     * 付款金额
     */
    private String total_amount;
    /**
     * 销售产品码，与支付宝签约的产品码名称
     */
    private String product_code;


}