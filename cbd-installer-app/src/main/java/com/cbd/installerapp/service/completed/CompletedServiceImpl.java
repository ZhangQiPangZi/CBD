package com.cbd.installerapp.service.completed;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.completed.CompletedListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfosDO;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.utils.PageUtils;
import com.cbd.installerapp.dao.completed.CompletedListDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/22 16:09
 * @Description
 */
public class CompletedServiceImpl implements CompletedListService {

    @Autowired
    private CompletedListDao completedListDao;

    @Override
    public PageResponse getList(Integer installerId, Integer orderTypeCode, PageRequest pageRequest){

        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<OrderInfosDO> result = completedListDao.getList(installerId,orderTypeCode);
        PageInfo<OrderInfosDO> resultList = new PageInfo<>(result);
        return PageUtils.getPageResponse(resultList);
    }
}
