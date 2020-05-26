package com.black.lei.Utils.utilsForPlatForm;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class CommonTrackLast {
	
//	@Resource
//	private IDeviceStoreService deviceStoreService;
	
	private static CommonTrackLast commonTrackLast;
	
	@PostConstruct
	public void init() {
		commonTrackLast = this;
	}


	/**
	 * 获取定位信息时,更新设备状态
	 * @author wcj
	 * @param oTrack
	 */
	public static void updateTEState(Map<String,Object> oTrack){
		//速度漂移纠正，时差超过1分钟没有更新的，速度可以设置为0
		if( oTrack != null ) {
			short nSpeed = Short.parseShort(oTrack.get("nSpeed").toString());
			int nTEState = oTrack.get("nTEState") == null ? 0 : Integer.parseInt(oTrack.get("nTEState").toString());
			int nTime = Integer.parseInt(oTrack.get("nTime").toString());
			int	nStopTime = Integer.parseInt(oTrack.get("nStopTime").toString());
			//int isStopStatus = m_TrackList.get(strTEID).get("isStopStatus") == null ? 0 : Integer.parseInt(m_TrackList.get(strTEID).get("isStopStatus").toString());
			oTrack.put("isStopStatus", 0);
			if( nSpeed != 0 ) {	
				//超过60秒，没有更新，速度设置为0
				if((System.currentTimeMillis() / 1000) - nTime >= 60 ) {
					if(  nSpeed > 0 ) {
						oTrack.put("nSpeed", (short)0);
						if(  nStopTime  == 0 ) {
							oTrack.put("nStopTime", nTime );
							oTrack.put("isStopStatus", 1);
						}
					}						
				} else {
					oTrack.put("isStopStatus", 0);
					oTrack.put("nStopTime", nTime);
				}
			} else {
				if( nStopTime == 0) {
					oTrack.put("nStopTime", nTime );
					oTrack.put("isStopStatus", 0);
				} else {
					if ((System.currentTimeMillis() / 1000) - nStopTime >= 120) {
						oTrack.put("nStopTime", nStopTime);
						oTrack.put("isStopStatus", 1);
					} else {
						oTrack.put("nStopTime", nTime );
						oTrack.put("isStopStatus", 0);
					}
				}
			}
			
			//超过10分钟（600秒）没有更新数据，则设备离线，设备离线采用状态码：(nTEState | (0x01000000)) 
//			if (  !CommonTrackAlarm.IsDeviceOFFLine(nTEState) ) {
//				int nDevType = commonTrackLast.deviceStoreService.findFirst(oTrack.get("strTEID").toString()).getnDevType();
//				if(nDevType == 0 && (System.currentTimeMillis() / 1000) - nTime >  600 ) {
//					nTEState = CommonTrackAlarm.SetDeviceOFFLine(nTEState);
//				} else if ( nDevType == 1 && (System.currentTimeMillis() / 1000) - nTime > 180 ) {
//					nTEState = CommonTrackAlarm.SetDeviceOFFLine(nTEState);
//				}
//				oTrack.put("nTEState", nTEState);
//				oTrack.put("nDevType", nDevType);
//			}
		}
	}
	


    //纠正速度漂移和误差
	@SuppressWarnings("unused")
	private  short UpdateSpeed(short DstSpeed,  int CacheTime, int newTime, double CacheLon,double CacheLat,double NewLon,  double NewLat) {
		 if( DstSpeed > 0 && DstSpeed <= 50  ) {
			 //连续两次时间不同， 坐标相同，速度默认为0, 这里应该计算两点之间距离，如果10秒内移动距离不超过15米，则速度为0
			 if( (CacheTime -  newTime) >= 10 ||  (newTime  - CacheTime) >= 10  ) {
				 int distance = CommonGpsCorrect.daoInterface.GetLonLatDistance(CacheLon  , CacheLat,  NewLon, NewLat);
				 if( distance < 20 ) {
					 return 0;
				 } else {
					 return DstSpeed;
				 }
			 }
		 }
		return DstSpeed;
	}
		
	
		
	/**
	 * 根据角度0~360度，解析字符串
	 * @param nAngle
	 * @return
	 */
	public  String GetDirectionString(short nAngle){

		
		if( ( nAngle >=350 &&  nAngle <= 360 )  ||  ( nAngle>= 0 && nAngle <= 10) ){
			return "正北方";
		}
		else if ( nAngle > 10 && nAngle < 80 ){
			return "东北方";
		}
		else if ( nAngle >= 80 && nAngle <= 100 ){
			return "正东方";
		}
		else if ( nAngle > 100 && nAngle < 170 ){
			return "东南方";
		}
		else if ( nAngle >= 170 || nAngle <=190 ){
			return "正南方";
		}
		else if ( nAngle > 190 && nAngle <= 260 ){
			return "西南方";
		}
		else if ( nAngle > 260 && nAngle < 280 ){
			return "正西方";
		}
		else if ( nAngle >= 280 && nAngle <350 ){
			return "西北方";
		}
		else {
			return "未知";
		}
		
	}

}
