package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.TaskListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;
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

        List<OrderInfoDO> result = taskListDao.getTaskList(installerId,orderTypeCode);
        PageInfo<OrderInfoDO> resultList = new PageInfo<>(result);
        return PageUtils.getPageResponse(resultList);

    }
}
