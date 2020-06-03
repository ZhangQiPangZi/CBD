package com.cbd.salesapp.service.workbench;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.InputInfoService;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.OrderInfoQuery;
import com.cbd.salesapp.dao.workbench.InputInfoDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author: Monster
 * @date: 2020/4/21 15:12
 * @Description
 */
public class InputInfoServiceImpl implements InputInfoService {
    @Autowired
    private InputInfoDao inputInfoDao;
    @Override
    public int confirmInputInfo(OrderInfoQuery query){
        query.setOrderId(UUID.randomUUID().toString());
        inputInfoDao.confirmInputInfo(query);
        return query.getId();
    }
}
