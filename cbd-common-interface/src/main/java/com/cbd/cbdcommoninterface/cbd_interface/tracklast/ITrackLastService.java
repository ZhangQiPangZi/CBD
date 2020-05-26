package com.cbd.cbdcommoninterface.cbd_interface.tracklast;



import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;

import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/23 16:08
 * @Description:
 */
public interface ITrackLastService {

    TrackLast findByTEID(String devID);

    /**
     * 根据设备号查询轨迹信息
     * @author wcj
     * @param
     * @return
     */
    List<TrackLast>  getTrackInfoByTEID(String devID, int startTime, int endTime);

    /**
     * 根据devID和最大时间戳查找定位数据
     * @param devID
     * @return
     */
    TrackLast getRealTrackByTEID(String devID);


    Map<String, Object> getTrackInfoByTEIDForLogin(String devID);
}
