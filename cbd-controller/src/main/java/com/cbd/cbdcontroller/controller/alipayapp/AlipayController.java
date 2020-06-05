package com.cbd.cbdcontroller.controller.alipayapp;

import com.alibaba.fastjson.JSON;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cbd.cbdcommoninterface.cbd_interface.alipay.IAlipayService;
import com.cbd.cbdcommoninterface.cbd_interface.contract.ContractService;
import com.cbd.cbdcommoninterface.cbd_interface.redis.RedisService;
import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ICarInfoService;
import com.cbd.cbdcommoninterface.enums.OrderTypeEnum;
import com.cbd.cbdcommoninterface.keys.OrderTypeKey;
import com.cbd.cbdcommoninterface.keys.RenewKey;
import com.cbd.cbdcommoninterface.request.PayRequest;
import com.cbd.cbdcommoninterface.response.leiVo.AlipayVo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.cbd.cbdcommoninterface.utils.AlipayConfig.*;

/**
 * 支付宝后台接口
 * 支付调用，回调，return
 */
@Slf4j
@Api("支付宝APP端调用")
@RequestMapping("alipay")
@RestController
public class AlipayController {


    Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private IAlipayService alipayService;

    @Autowired
    private ICarInfoService carInfoService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ContractService contractService;

    @ApiOperation(value = "app支付调用页面", httpMethod = "POST")
    @RequestMapping("/wappay")
    public void webAppPay(  @RequestBody PayRequest payRequest,
                            HttpServletResponse response) throws AlipayApiException, IOException {
        String form = gotopayPage(payRequest.getOrderID(), payRequest.getOrderSubject(), payRequest.getOrderPrice(), payRequest.getOrderType(), "QUICK_MSECURITY_PAY");
        response.setContentType("text/html;charset=" + charset);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();


    }

    @ApiOperation(value = "web支付调用页面,orderID为合同ID", httpMethod = "POST")
    @RequestMapping("/webAddPay")
    public void webAddPay(  @RequestBody PayRequest payRequest,
                            HttpServletResponse response) throws AlipayApiException, IOException {
        String form = gotopayPage(payRequest.getOrderID(), payRequest.getOrderSubject(), payRequest.getOrderPrice(), payRequest.getOrderType(),"FAST_INSTANT_TRADE_PAY");
        response.setContentType("text/html;charset=" + charset);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();

    }

    @ApiOperation(value = "web支付调用页面,orderID为合同ID", httpMethod = "POST")
    @RequestMapping("/webRenewPay")
    public void webRenewPay(
                       @RequestBody PayRequest payRequest,
                       HttpServletResponse response) throws AlipayApiException, IOException {
        //将续费年限放入缓存
        redisService.set(RenewKey.RENEW_TIME, payRequest.getOrderID(), payRequest.getRenewYears());
        String form = gotopayPage(payRequest.getOrderID(), payRequest.getOrderSubject(), payRequest.getOrderPrice(), payRequest.getOrderType(),"FAST_INSTANT_TRADE_PAY");
        response.setContentType("text/html;charset=" + charset);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 支付宝返回一个form表单，并自动提交，跳转到支付宝页面
     * @param orderID
     * @param orderSubject
     * @param orderPrice
     * @param orderType
     * @return
     * @throws AlipayApiException
     * @throws IOException
     */
    private String gotopayPage(String orderID, String orderSubject, String orderPrice, Integer orderType, String productCode) throws AlipayApiException, IOException {
        // 订单模型
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();

        //订单ID
        model.setOutTradeNo(orderID);

        //将此订单类型放入Redis
        redisService.set(OrderTypeKey.ORDER_TYPE, orderID, orderType);
        String realType = "";
        if (orderType.equals(OrderTypeEnum.APPTYPE.ordinal())){
            realType = "app";
        }else if (orderType.equals(OrderTypeEnum.WEBADDTYPE.ordinal())){
            realType = "AddContract";
        }else {
            realType = "renewContract";
        }

        log.info("OrderID:{}, OrderType:{}", orderID, realType);

        //订单信息--标题
        model.setSubject(orderSubject);
        //订单金额
        model.setTotalAmount(orderPrice);

        //支付最长等待时间
        model.setTimeoutExpress("5m");
        model.setProductCode(productCode);

        AlipayTradeWapPayRequest wapPayRequest = new AlipayTradeWapPayRequest();
        wapPayRequest.setReturnUrl(return_url);
        wapPayRequest.setNotifyUrl(notify_url);
        wapPayRequest.setBizModel(model);

        // 调用SDK生成表单, 并直接将完整的表单html输出到页面
        AlipayClient alipayClient = newAlipayClient();
        String form = alipayClient.pageExecute(wapPayRequest).getBody();
        System.out.println(form);

        return form;
    }



    /**
     * @param out_trade_no 商户订单号
     * @param trade_no     支付宝交易凭证号
     * @param trade_status 交易状态
     * @return String
     * @throws AlipayApiException
     * @Description: 支付宝回调接口
     */
    @ApiOperation(value = "支付宝回调页面，异步")
    @PostMapping("notify")
    private String alipayNotify(HttpServletRequest request, String out_trade_no, String trade_no, String trade_status)
            throws AlipayApiException {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            map.put(name, valueStr);
        }
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(map, alipay_public_key, charset, sign_type);
        } catch (com.alipay.api.AlipayApiException e) {
            logger.info("notify 验证失败", e);
            // 验签发生异常,则直接返回失败
            return ("failed");
        }
        if (signVerified) {
            //处理你的业务逻辑，更新订单状态等
            log.info("验签成功，开始执行业务代码");

            //获取订单类型，根据订单类型处理不同业务
            Integer orderType = redisService.get(OrderTypeKey.ORDER_TYPE, out_trade_no, Integer.class);

            if (orderType.equals(OrderTypeEnum.APPTYPE.ordinal())){
                Integer success = alipayService.handleOrderMsg(out_trade_no);
            }else if (orderType.equals(OrderTypeEnum.WEBADDTYPE.ordinal())){
                //out_trade_no就是ContractID
                contractService.addOrder(out_trade_no);
            }else {
                contractService.renewContract(out_trade_no);
            }
            //清除redis的orderType
            redisService.delete(OrderTypeKey.ORDER_TYPE, out_trade_no);
            return ("success");
        } else {
            logger.info("验证失败,不去更新状态");
            return ("failed");
        }
    }

    /**
     * @param out_trade_no 商户订单号
     * @param trade_no     支付宝交易凭证号
     * @return String
     * @throws AlipayApiException
     * @Description: 支付宝同步跳转接口
     */
    @ApiOperation(value = "支付宝回调页面，同步",  httpMethod = "GET")
    @GetMapping("return")
    private Result<String> alipayReturn(HttpServletRequest request, String out_trade_no, String trade_no, String orderPrice)
            throws AlipayApiException {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                System.out.println(valueStr);
            }
            map.put(name, valueStr);
        }
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(map, alipay_public_key, charset, sign_type);
        } catch (AlipayApiException e) {
            logger.info("支付宝回调异常", e);
            // 验签发生异常,则直接返回失败
            return Result.error(CodeMsg.PAY_ERROR);
        }
        if (signVerified) {
            return Result.success("支付成功，即将调回主页面");
        } else {
            return Result.error(CodeMsg.SIGNVERIFIED_ERROR);
        }
    }
    @ApiOperation(value = "前端回调接口",  httpMethod = "POST")
    @RequestMapping(value = "/check",method = RequestMethod.POST)
    public Result<String> check(@RequestParam("orderID")  String orderID){

        Integer res = carInfoService.hasOrderID(orderID);
        if(res == 1) {
            return Result.success("支付验证成功");
        }else {
            return Result.error(CodeMsg.PAY_ERROR);
        }
    }



    public AlipayClient newAlipayClient() {
        return new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type);
    }


}