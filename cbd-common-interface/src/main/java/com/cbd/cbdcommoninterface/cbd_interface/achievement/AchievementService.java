package com.cbd.cbdcommoninterface.cbd_interface.achievement;

import com.cbd.cbdcommoninterface.request.PageCpyAchConditionRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;

public interface AchievementService {

    /**
     * 传入销售员id和所完成的这单使用的合同ID来增加销售额
     * @param salersID
     * @param contractID
     * @return
     */
    Boolean addAchievement(String salersID, String contractID, String companyID);

    /**
     * 根据指定条件获取下级公司销售列表
     * @param pageCpyAchConditionRequest
     * @return
     */
    PageResponse findCompanyAchievementByCondition(PageCpyAchConditionRequest pageCpyAchConditionRequest);
}
