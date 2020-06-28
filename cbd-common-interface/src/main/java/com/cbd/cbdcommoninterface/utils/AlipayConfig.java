package com.cbd.cbdcommoninterface.utils;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
@Component
public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102400748426";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQDB1YimoW3eNTkyBlro3dCJFV6WyfXzaSxc2ElbJGOGT3xkR7kZ8I9iiSPttNLj00q/sZq03etIYpnewL1WRcSYSHyycsE3jBJBGru2nMNDxJpNnBvrJe6OAP/Lm6s806NF4aU4DtMpK1iW44Y2nhClOhzO2YN/2wBiRNFXppoEQHl9XE1nQz+xmTXV4D36Sa6lw34kLEkaxTcj1DqUTueJBQeidPr85JoE/v1PLuBH63oQpLYzAX9MMT7RLaGvKnk//CIszHd/RcAqBHD/MnBVpcRZgVVQMHqxvE3bQGjDzaC6SYTgUWNlAmG1Mb7cUZtLK8W5Po7jYWkKfkcAkwzrAgMBAAECgf9+cc2y5ZvGWPH2sC6duhVPV6FcvGK1ZuaJqo4v5o0GENQUlrUpNsRSgyPs7QilljAs3K8PTbUugyPdK95XaSvWK3Ms9a8VjUpbLhVGCv1WN+pwtmWAAD6eZAh5yhLqmyTncUtWRlV5hc1EoSfseTBimIYyabJetX6KnFnz07qlCkVhrpDWq0RcJvdZZ6PnlNJy7rdLhZ2rgNBq/MyC82D+NigRyZ8rTzym0u1yTMjXyLD20HvG4Op0q0ylT3kREUax6/cmuPlUbS5/TY38O/4Hd4u6hwghWjnM886YRNFWPTJ2NAiKU34iELyTNQSuH6tNFDj9DCb1hw/AWcI8LkkCgYEA++js2/kch6zVhFiztR1WEj1KNO07cJeLuKY0NAcM/sHW1vQ5WRZCMOMMGDxdOb58cRCW3TuzjFKFFHL9/fsKPIq0EzDZzlDUiYH9JIukSkhD184LEzFwck7f4FyGxw6xrRO63wC8M7r9MHwYJ/O3PmfHzVT1Tf928r+IxWAV7I8CgYEAxPs2ziQ14uV2i+4K04CiyNEW7OPMMSDvK9VOcqAJW9WazJvsZKZbRe9EyRDv9H9dWqHFHQqtdkuN4dZaYTWY0DvgVuuQXHRX5w+Gc92mtb5/s4bANNkt27JM7vSlEjdSUZ5HX8Bfe5teTV36BXPh4BHDvglt1Aq66NXS//Zs/+UCgYEAzEUhpHKJjv84mLww5UGnpNysSuTAGVnaA95KYJIDGKXoZ5/czP++ptPF3/U7B3MOh8STAUzDGJtlrKJekdA6icNTqGgphiKHZgXRVwwhKdcVpG5BDsw9SYPqCI4fAFVLAHUKSIDZG/2aB/HoyKTZR7JyE2+8BwaL1uY3qw1yWcMCgYBDXySUhuADVbYhD+6V/kYu5wyvQw5gs0H6PP+SxtfMmBdU6fOFTTykg7yEBI39kt5MFE5Fq5Q+hILnfhf1bY5xkjkdoX4DkStLdQzobiQJvzkQHwN4T9uWa7pSdExNGazHC5qfzj0aGr67OWw9FzBnIcxQIAt3XaAl6JQ9RXxsXQKBgGeVuPkzmMMPrVqK67zZiqCs8XacLusleJV2EtSIvqB9BoJrUlfTZjB0fq9fktcu3qtAT0Jk29grAsY0jNdfZyzBr5VxSa437xbz7H4fRIyJ0bs5HforM7JfuojxOaPIdVN4jVxf4KXFAZZAGNj2ciOm07SFoT6DW15FFbUlhTrN";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwnA8MJH6TahbOQisJBXLIPb887bjHnFzONMI/+z9/difAchg+2zavrjGoBfc4N81Nkhdko5wi8UMedU0Ab4JMdtnLgXQgpuSIgZpz1uUXeX7rbDYZYVNsrjzSZl1a7WaF8E8TTvGUnNAYCbN+UZkpdxHNrufmgn/R6CRAZ7ZAoSDhrHMcwiFaSAWqz22qDKhchYGMoFaN16MsL28svd+VbLOhbVB0HtBvu8aNENzrQYcO7r4bhtBgFGFXXY9JjI4Z3lgFr/rePjlw3jsQ9sXmoLXAQKj/QuXEilkLnlhL3u+BuMpKAVqVN+lBklDqAXSZDAvl9CknwU1V7m/L4JuyQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

	public static String notify_url = "http://8px46h.natappfree.cc/alipay/notify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://8px46h.natappfree.cc/alipay/return";
//	public static String add_contract_return_url = "http://6qjgn5.natappfree.cc/#/contract/contractCreate";
//	public static String renew_contract_return_url = "http://6qjgn5.natappfree.cc/#/contractRenewal";



	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";




//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

