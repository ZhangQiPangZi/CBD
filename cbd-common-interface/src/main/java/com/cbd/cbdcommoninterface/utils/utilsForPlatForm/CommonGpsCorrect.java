package com.cbd.cbdcommoninterface.utils.utilsForPlatForm;

/**
 * 坐标转换
 * 
 * @author wcj
 *
 */
public class CommonGpsCorrect {
	public static CommonGpsCorrect daoInterface = new CommonGpsCorrect();

	private static final double pi = 3.14159265358979324;
	private static final double a  = 6378245.0;
	private static final double ee = 0.00669342162296594323;

	public static class GPSPoint {
		public GPSPoint() {
			lat = 0;
			lon = 0;
		}

		public GPSPoint(double nlat, double nlon) {
			lat = nlat;
			lon = nlon;
		}

		public double lat;
		public double lon;
	}

	/**
	 * 坐标转换，腾讯地图转换成百度地图坐标
	 * 
	 * @param lat
	 *            腾讯纬度
	 * @param lon
	 *            腾讯经度
	 * @return 返回结果：经度,纬度
	 */
	public GPSPoint map_tx2bd(GPSPoint point) {
		double bd_lat;
		double bd_lon;
		double x_pi = 3.14159265358979324 * 16.6666666666666667;
		double x = point.lon, y = point.lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		bd_lon = z * Math.cos(theta) + 0.0065;
		bd_lat = z * Math.sin(theta) + 0.006;

		point.lat = bd_lat;
		point.lon = bd_lon;
		return point;
	}

	/**
	 * 坐标转换，百度地图坐标转换成腾讯地图坐标
	 * 
	 * @param lat
	 *            百度坐标纬度
	 * @param lon
	 *            百度坐标经度
	 * @return 返回结果：纬度,经度
	 */
	public GPSPoint map_bd2tx(GPSPoint point) {
		double tx_lat;
		double tx_lon;
		double x_pi = 3.14159265358979324 * 16.6666666666666667;
		double x = point.lon - 0.0065, y = point.lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		tx_lon = z * Math.cos(theta);
		tx_lat = z * Math.sin(theta);
		point.lat = tx_lat;
		point.lon = tx_lon;
		return point;
	}

	// 判断对象是否有效
	public boolean isValid(GPSPoint oLonLat) {
		if (oLonLat == null) {
			return false;
		}
		boolean isValid = true;
		double nLon = oLonLat.lon;
		double nLat = oLonLat.lat;

		if (nLon < -180 || nLon > 180) {
			oLonLat.lon = 0;
			oLonLat.lat = 0;
			isValid = false;
		}

		if (nLat < -90 || nLat > 90) {
			oLonLat.lon = 0;
			oLonLat.lat = 0;
			isValid = false;
		}

		return isValid;
	}

	// --------------------------------------
	//
	private double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	// --------------------------------------
	//
	private double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}

	// ------------------------------------
	// 纠正单个点, isReverse/反向纠偏
	// 一般误差在2.3米, 函数实时算法纠偏, 精度高速度快.
	// 正向纠偏, 真实经纬度在地图上显示定位图标用. 设备定位数据转地图坐标 isReverse=false;
	// 逆向纠偏, 从地图获取经纬度, 要返回给程序操作, 比如鼠标点击拖动地图获取电子围栏的坐标点,
	// 逆向解析成真实经纬度保存. 地图坐标转设备定位数据

	public GPSPoint BdTransform(GPSPoint oLonLat, Boolean isReverse) {

		if (isReverse) { // 反向纠偏，先把百度地图坐标转换为腾讯地图坐标， 再用腾讯地图坐标转换为真是设备GPS坐标
			oLonLat = map_bd2tx(oLonLat);
		}

		if (oLonLat == null || isValid(oLonLat) == false) {
			return oLonLat;
		}
		double wgLat = oLonLat.lat;
		double wgLon = oLonLat.lon;

		if (isValidCorrect(oLonLat) == false) {
			return oLonLat;
		}

		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat / 180.0 * pi;
		double magic = Math.sin(radLat);

		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);

		if (isReverse == false) {
			oLonLat.lat = wgLat + dLat;
			oLonLat.lon = wgLon + dLon;
			oLonLat = map_tx2bd(oLonLat); // 把腾讯地图二次转换为百度坐标
		} else {
			oLonLat.lat = wgLat - dLat;
			oLonLat.lon = wgLon - dLon;
		}

		return oLonLat;
	}

	// ------------------------------------
	// 纠正单个点, isReverse/反向纠偏
	// 一般误差在2.3米, 函数实时算法纠偏, 精度高速度快.
	// 正向纠偏, 真实经纬度在地图上显示定位图标用. 设备定位数据转地图坐标 isReverse=false;
	// 逆向纠偏, 从地图获取经纬度, 要返回给程序操作, 比如鼠标点击拖动地图获取电子围栏的坐标点,
	// 逆向解析成真实经纬度保存. 地图坐标转设备定位数据

	public GPSPoint TxTransform(GPSPoint oLonLat, boolean isReverse) {

		if (oLonLat == null || isValid(oLonLat) == false) {
			return oLonLat;
		}
		double wgLat = oLonLat.lat;
		double wgLon = oLonLat.lon;

		if (isValidCorrect(oLonLat) == false) {
			return oLonLat;
		}

		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat / 180.0 * pi;
		double magic = Math.sin(radLat);

		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);

		if (isReverse == false) {
			oLonLat.lat = wgLat + dLat;
			oLonLat.lon = wgLon + dLon;
		} else {
			oLonLat.lat = wgLat - dLat;
			oLonLat.lon = wgLon - dLon;
		}

		return oLonLat;
	}

	// ------------------------------------------
	// 判断是否能纠偏, 用来检测是否在中国境内
	public Boolean isValidCorrect(GPSPoint gpsData) {
		if (gpsData == null || isValid(gpsData) == false) {
			return false;
		}

		if (gpsData.lon < 72.004 || gpsData.lon > 137.8347 || gpsData.lat < 0.8293 || gpsData.lat > 55.8271) {
			return false;
		}

		return true;
	}

	// 求经纬度两点间的弧线距离, 单位 米
	public int GetLonLatDistance(GPSPoint SrcData, GPSPoint DstData) {
		return GetLonLatDistance(SrcData.lon, SrcData.lat, DstData.lon, DstData.lat);
	}

	// 求经纬度两点间的弧线距离, 单位 米
	public int GetLonLatDistance(double dbLon, double dbLat, double dbLonOther, double dbLatOther) {
		if (dbLon < -180 || dbLon > 180 || dbLat < -90 || dbLat > 90) {
			return 0;
		}

		if (dbLonOther < -180 || dbLonOther > 180 || dbLatOther < -90 || dbLatOther > 90) {
			return 0;
		}

		double dbPI = 3.14159265358979323846;

		dbLon = dbLon * dbPI / 180;
		dbLat = dbLat * dbPI / 180;
		dbLonOther = dbLonOther * dbPI / 180;
		dbLatOther = dbLatOther * dbPI / 180;

		double nDistance = 10000 * 637.8137 * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((dbLat - dbLatOther) / 2), 2)
				+ Math.cos(dbLat) * Math.cos(dbLatOther) * Math.pow(Math.sin((dbLon - dbLonOther) / 2), 2)));

		return (int) nDistance;
	}

}
