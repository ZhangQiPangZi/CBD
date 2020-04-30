package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.TaskListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;
import com.cbd.installerapp.dao.waitingtask.TaskListDao;
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
    public List<OrderInfoDO> getTaskList(Integer installerId, Integer orderTypeCode){
        return taskListDao.getTaskList(installerId,orderTypeCode);
    }
}
