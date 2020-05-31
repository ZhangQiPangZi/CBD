package com.cbd.salesapp.dao.workbench;

import com.cbd.cbdcommoninterface.request.salesapp.workbench.OrderInfoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: Monster
 * @date: 2020/4/21 15:13
 * @Description
 */
@Mapper
@Repository
public interface InputInfoDao {
    /**
     * 录入用户信息
     * @param query
     * @return
     */
    int confirmInputInfo(OrderInfoQuery query);
}
