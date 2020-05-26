package com.black.lei.Utils.utilsForPlatForm;

/**
 * 定义通用的系统常量
 * @author wcj
 *
 */

public final class CommonConst {

	//当前系统主要版本
	public final static int MajorVersion = 2;
		
	//当前系统次版本
	public final static int MinjorVersion = 0;
		
	//当前系统补丁号
	public final static int PatchVersion = 0;
		
		
	/**
	 * 分页大小
	 */
	public final static int SYS_PAGEROWSIZE = 10;
		
	/**
	 * 即将到期和超期订单自动导入天数，可全局配置
	 */
	public final static int AUTOIMPORT_DAY=30;
		
	/**
	 * 上传安装图片的物理位置,用于上传图片,采用相对路径这样好配置
	 */
	public final static String TOMCAT_serverType="tomcat";
	public final static String JBOSS_serverType="jboss";
	   
    public static String TOMCAT_IMAGE_PATH="/installImages/";
    public static String TOMCAT_DOWNLOAD_PATH="/downLoad/";
    public static String TOMCAT_PUSHIMAGE_PATH="/pushPictures/";
  
    public static String jBOSS_PUSHIMAGE_PATH_COPY="d:/pushPictures/pushPictures.war/";
   
    public static String JBOSS_IMAGE_PATH="/installImages.war/";
    public static String JBOSS_DOWNLOAD_PATH="d:/downLoad//downLoad.war/";
    public static String JBOSS_PUSHIMAGE_PATH="/pushPictures.war/";
	   
    /**
	 * 上传安装图片的显示位置采用相对路径
	 */
	public static String IMAGE_PATH_SHOW="/installImages/";
	public static String DOWNLOAD_PATH_SHOW="/downLoad/";
	public static String IMAGE_PUSHPATH_SHOW="/pushPictures/";
	public static String IMAGE_DYNAMIC_SHOW="/dynamicPicture/";
	public static String IMAGE_CAR_ICON="/carpictures/allcar/";
	/*********************************************************************************************
	 * 短信网关-短信模板编码--通用与客服通知，客户报警，保养提示，指派安装工等场合 
	 **********************************************************************************************/
	
	//公司类别(0:平台公司，1：4S店，2：担保公司，3：车辆运营企业，4：其他
	public static final int COMPANYTYPE_ALL = -1;
	public static final int COMPANYTYPE_PLATFORM = 0;
	public static final int COMPANYTYPE_CARSHOP = 1;
	public static final int COMPANYTYPE_WARRANT = 2;
	public static final int COMPANYTYPE_BUSINESS = 3;
	public static final int COMPANYTYPE_OTHER = 4;
		
	//员工类型（0：车主，1：公司经理，2：客服，3：库管，4：安装工，5：销售员，6：财务，7：管理员，8:其他 , 9:项目经理, 10:部门经理,11: 分公司经理
	public final static int PERSONTYPE_CARHOST 	= 0; //车主
	public final static int PERSONTYPE_MANAGE = 1; //公司经理
	public final  static int PERSONTYPE_CUSTUMSER = 2; //客服
	public final static int PERSONTYPE_STOREADMIN = 3; //库管
	public final  static int PERSONTYPE_INSTALL	= 4; //安装工
	public final static int PERSONTYPE_SALER = 5; //销售员、业务
	public final static int PERSONTYPE_FINANCE = 6; //财务
	public final static int PERSONTYPE_ADMIN = 7; //系统管理员
	public final static int PERSONTYPE_OTHER = 8; //其他
	public final static int PERSONTYPE_ITEMMANAGE = 9; //项目经理
	public final static int PERSONTYPE_DEPARTMANAGE	= 10; //部门经理
	public final static int PERSONTYPE_SUBMANAGE = 11; //分公司经理
	
	
	//判断设备是否长时间离线--秒数
	public final static int NlONGTIME_OFFLINE = 259200;
	
	//判断无线设备是否离线--秒数
	public final static int NTIME_OFFLINE = 86400;
	
	/**
	 * 订单状态（0：新建   1：已经确认  2：正在处理   3：处理完毕  4：付款提车，5; //撤销）
	 * 
	 */
	public final static byte ORDERTYPE_MAINTAIN = 0;//保养单
	public final static byte ORDERTYPE_REPAIR =1;//维修单
	public final static int ORDERSTATES_NEW  = 0; //新建
	public final static int ORDERSTATES_SURE  = 1; //已经确认  
	public final static int ORDERSTATES_HANDLING  =2;  //正在处理
	public final static int ORDERSTATES_HANDLED  = 3;  //处理完毕
	public final static int ORDERSTATES_PAYED_GETCAR  = 4; //付款提车
	public final static int ORDERSTATES_CANCEL  = 5; //撤销
	
	/**
	 * 正常
	 */
	public final static int CARSTATE_NORMAL	= 0; //正常
	
	/**
	 * 待保养
	 */
	public final static int CARSTATE_NEEDMAINTAIN = 1; //待保养
	
	/**
	 * 维修
	 */
	public final static int CARSTATE_REPAIRING = 2; //维修
	
	/**
	 * 逾期
	 */
	public final static int CARSTATE_EXPIRETIME	= 3; //逾期
	
	/**
	 * 被盗
	 */
	public final static int CARSTATE_STEALED = 4; //被盗
	
	/**
	 * 扣留
	 */
	public final static  int CARSTATE_DETAIN = 5; //扣留
	
	/**
	 * 报废
	 */
	public final static int CARSTATE_DISCARD = 6; //报废
	
	//关注和车辆在线离线
	/**
	 * 在线
	 */
	public final static int CARSTATE_ONLINE	= 10; //在线
	/**
	 * 离线
	 */
	public final static int CARSTATE_OFFLINE = 11; //离线
	/**
	 * 贷后关注
	 */
	public final static int CAR_AFTERLOANCARE = 2; //贷后关注
	/**
	 * 逾期关注
	 */
	public final static int CAR_OVERRUDECARE = 3; //逾期关注
	/**
	 * 重点关注
	 */
	public final static int CAR_KEYNODECARE	 =  4; //重点关注
	
	/**
	 * 按揭到期
	 */
	public final static int CAR_MORTAGEEXPRIRE = 5; //按揭到期
	
	/**
	 * 告警状态值
	 */
	public final static int  ALARMTYPE_CIRCUITCOFF 	= 0x4000;  //断电报警
	public final static int  ALARMTYPE_CUSTOM3	= 0x80; //非法拆除报警
}
