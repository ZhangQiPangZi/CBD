package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.TaskListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfosDO;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.utils.PageUtils;
import com.cbd.installerapp.dao.waitingtask.TaskListDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 23:16
 * @Description
 */
public class TaskListServiceImpl implements TaskListService {
    @Autowired
    private TaskListDao taskListDao;
    @Override
    public PageResponse getTaskList(Integer installerId, Integer orderTypeCode, PageRequest pageRequest){

        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        //获取该工程师所有待处理订单 按订单类型进行搜索
        List<OrderInfosDO> result = taskListDao.getTaskList(installerId,orderTypeCode);
        PageInfo<OrderInfosDO> resultList = new PageInfo<>(result);
        return PageUtils.getPageResponse(resultList);

    }
}
