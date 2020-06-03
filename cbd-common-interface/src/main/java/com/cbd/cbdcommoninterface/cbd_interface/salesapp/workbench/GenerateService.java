package com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench;

import com.cbd.cbdcommoninterface.response.salesapp.workbench.GenerateVO;

/**
 * @author: Monster
 * @date: 2020/5/31 13:32
 * @Description
 */
public interface GenerateService {
    /**
     * 生成保单
     * @return
     */
    GenerateVO generateContract();
}
