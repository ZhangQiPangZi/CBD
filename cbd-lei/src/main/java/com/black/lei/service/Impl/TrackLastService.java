package com.black.lei.service.Impl;



import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ITrackLastService;
import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;
import com.cbd.cbdcommoninterface.response.leiVo.RealTrackVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author shy_black
 * @date 2020/4/23 19:18
 * @Description:
 */
@Service
public class TrackLastService implements ITrackLastService {
    @Resource
    private com.black.lei.dao.TrackLastDao TrackLastDao;

    @Override
    public TrackLast findByTEID(String devID) {

        return TrackLastDao.findByTEID(devID);
    }

    @Override
    public List<RealTrackVo> getTrackInfoByTEID(String devID,long startTime,long endTime) {

        return TrackLastDao.getTrackInfoByTEID(devID,startTime,endTime);
    }

    @Override
    public Map<String, Object> getTrackInfoByTEIDForLogin(String devID) {
        return null;
    }


    public RealTrackVo getRealTrackByTEID(String key) {

        return TrackLastDao.getRealTrackByTEID(key);
    }

    @Override
    public void newTest() {
        return;
    }


}
