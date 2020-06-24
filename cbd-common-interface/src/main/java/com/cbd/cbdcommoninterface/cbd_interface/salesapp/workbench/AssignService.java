package com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench;

import com.cbd.cbdcommoninterface.request.salesapp.workbench.AssignQuery;
import com.cbd.cbdcommoninterface.response.salesapp.workbench.InstallerInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/20 22:25
 * @Description
 */
public interface AssignService {

    /**
     * 挑选出固定时间段的空闲的工程师，并根据能力等级进行排序
     * 根据关键字对名字进行模糊搜索
     * @param query
     * @return
     */
    List<InstallerInfoVO> orderByLevel(AssignQuery query);
    /**
     * 根据距离排序
     * @param query
     * @return
     */
    List<InstallerInfoVO> orderByDistance(AssignQuery query);

    /**
     * 指派工程师
     * @param query
     * @return
     */
    int assignInstaller(AssignQuery query);

    /**
     * 稍后指派工程师
     * @param id
     * @param devId
     * @param simId
     * @return
     */
    int assignLater(Integer id,String devId,String simId);
}
