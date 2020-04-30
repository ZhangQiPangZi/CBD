package com.cbd.cbdcommoninterface.cbd_interface.salesapp.messagelist;

import com.cbd.cbdcommoninterface.pojo.salesapp.messagelist.OrderInfoDO;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.response.salesapp.messagelist.OrderInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 15:52
 * @Description
 */
public interface MessageListService {
    /**
     * 获取所有订单列表 根据订单类型搜索
     * @param orderTypeCode
     * @param pageRequest
     * @return
     */
    PageResponse getList(Integer orderTypeCode, PageRequest pageRequest);

    /**
     * 统计已指派订单的数量
     * @return
     */
    int processedCount();

    /**
     * 统计未指派订单的数量
     * @return
     */
    int pendingCount();

    /**
     * 查看订单的详细信息
     * @param id
     * @return
     */
    OrderInfoVO getOrderInfo(Integer id);

    /**
     * 重新指派工程师
     * @param id
     * @return
     */
    int reAssign(Integer id);
}
