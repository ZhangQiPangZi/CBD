package com.black.lei.service.Impl;



import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ITrackLastService;
import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public List<TrackLast> getTrackInfoByTEID(String devID,int startTime,int endTime) {

        return TrackLastDao.getTrackInfoByTEID(devID,startTime,endTime);
    }

    @Override
    public Map<String, Object> getTrackInfoByTEIDForLogin(String devID) {
        return null;
    }

    public TrackLast getRealTrackByTEID(String devID) {

        return TrackLastDao.getRealTrackByTEID(devID);
    }



}
