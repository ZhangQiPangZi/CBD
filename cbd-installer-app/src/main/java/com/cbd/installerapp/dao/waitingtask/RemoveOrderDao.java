package com.cbd.installerapp.dao.waitingtask;

import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Monster
 * @date: 2020/4/22 11:16
 * @Description
 */
@Mapper
public interface RemoveOrderDao {
    /**
     * 安装工拆除设备
     * @param query
     * @return
     */
    int removeDev(RemoveQuery query);
}
