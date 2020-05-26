package com.cbd.cbdcontroller.controller.alipayapp;

import com.alibaba.fastjson.JSON;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cbd.cbdcommoninterface.response.leiVo.AlipayVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import static com.cbd.cbdcommoninterface.utils.AlipayConfig.*;

/**
 * 支付宝后台接口
 * 支付调用，回调，return
 */
@Api("支付宝APP端调用")
@RequestMapping("alipay")
@RestController
public class AlipayController {


	Logger logger = LoggerFactory.getLogger(AlipayController.class);

	/**
	 * 去支付
	 *
	 * 支付宝返回一个form表单，并自动提交，跳转到支付宝页面
	 *
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "支付调用页面",httpMethod = "POST")
	@RequestMapping("/wappay")
	public void gotoPayPage(HttpServletResponse response) throws AlipayApiException, IOException {
		// 订单模型
		String productCode="QUICK_WAP_WAY";
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(UUID.randomUUID().toString());
		model.setSubject("这是WAP支付测试");
		model.setTotalAmount("0.01");
		model.setBody("支付测试，共0.01元");

		model.setTimeoutExpress("5m");
		model.setProductCode(productCode);

		AlipayTradeWapPayRequest wapPayRequest =new AlipayTradeWapPayRequest();
		wapPayRequest.setReturnUrl(return_url);
		wapPayRequest.setNotifyUrl(notify_url);
		wapPayRequest.setBizModel(model);

		// 调用SDK生成表单, 并直接将完整的表单html输出到页面
		AlipayClient alipayClient = newAlipayClient();
		String form = alipayClient.pageExecute(wapPayRequest).getBody();
		System.out.println(form);
		response.setContentType("text/html;charset=" + charset);
		response.getWriter().write(form);
		response.getWriter().flush();
		response.getWriter().close();
	}




	@ApiOperation(value = "测试支付信息",httpMethod = "POST")
	@GetMapping("pay")
	private String alipayFastPay(@RequestParam(value = "orderID",required = true) String orderID,
								 @RequestParam(value = "orderName",required = true) String orderName,
								 @RequestParam(value = "orderPrice",required = true)String orderPrice) throws AlipayApiException {
		//这个应该是从前端端传过来的，这里为了测试就从后台写死了
		AlipayVo vo = new AlipayVo();
		vo.setOut_trade_no(orderID);//订单id
		vo.setSubject(orderName);//商品名
		vo.setTotal_amount(orderPrice);//订单价格
		vo.setTimeout_express("5m");//订单最迟交易时间
		vo.setProduct_code("FAST_INSTANT_TRADE_PAY"); //这个是固定的
		String json = JSON.toJSONString(vo);
		logger.info("发起支付传参："+json);
		AlipayClient alipayClient = newAlipayClient();
		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(return_url);
		alipayRequest.setNotifyUrl(notify_url);
		alipayRequest.setBizContent(json);
		String result = null;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (Exception e) {
			logger.info("payerror" + result);
		}
		//这里生成一个表单，会自动提交
		return result;
	}

	/**
	 * @Description: 支付宝回调接口
	 * @param out_trade_no 商户订单号
	 * @param trade_no     支付宝交易凭证号
	 * @param trade_status 交易状态
	 * @return String
	 * @throws AlipayApiException
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
			logger.info("notify 验证失败",e);
			// 验签发生异常,则直接返回失败
			return ("--failed--");
		}
		if (signVerified) {
			//处理你的业务逻辑，更细订单状态等
			return ("--success--");
		} else {
			logger.info("验证失败,不去更新状态");
			return ("--failed--");
		}
	}

	/**
	 * @Description:            支付宝同步跳转接口
	 * @param out_trade_no      商户订单号
	 * @param trade_no          支付宝交易凭证号
	 * @return String
	 * @throws AlipayApiException
	 */
	@ApiOperation(value = "支付宝回调页面，同步",httpMethod = "GET")
	@GetMapping("return")
	private String alipayReturn(Map<String, String> params, HttpServletRequest request, String out_trade_no, String trade_no, String orderPrice)
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
			return ("--fail--");
		}
		if (signVerified) {
			return ("--success--");
		} else {
			return ("fail");
		}
	}



	public AlipayClient newAlipayClient(){
		return new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type);
	}


}