package com.cbd.cbdcommoninterface.utils;

import com.cbd.cbdcommoninterface.response.PageResponse;
import com.github.pagehelper.PageInfo;

public class PageUtils {

    /**
     * 将分页信息封装到统一的接口
     * @param pageInfo
     * @return
     */
    public static PageResponse getPageResponse(PageInfo<?> pageInfo) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setPageNum(pageInfo.getPageNum());
        pageResponse.setPageSize(pageInfo.getPageSize());
        pageResponse.setTotalSize(pageInfo.getTotal());
        pageResponse.setTotalPages(pageInfo.getPages());
        pageResponse.setContent(pageInfo.getList());
        return pageResponse;
    }
}
