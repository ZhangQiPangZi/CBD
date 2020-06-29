package com.cbd.cbdcommoninterface.cbd_interface.tracklast;



import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;
import com.cbd.cbdcommoninterface.response.leiVo.RealTrackVo;

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
    List<RealTrackVo>  getTrackInfoByTEID(String devID, long startTime, long endTime);

    /**
     * 根据devID和最大时间戳查找定位数据
     * @param key
     * @return
     */
    RealTrackVo getRealTrackByTEID(String key);


    Map<String, Object> getTrackInfoByTEIDForLogin(String devID);

    void newTest();
}
