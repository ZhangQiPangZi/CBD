package com.cbd.cbdcommoninterface.cbd_interface.achievement;

import com.cbd.cbdcommoninterface.request.*;
import com.cbd.cbdcommoninterface.response.PageResponse;

import java.util.List;
import java.util.Map;

public interface AchievementService {

    /**
     * 传入销售员id和所完成的这单使用的合同ID来增加销售额
     * @param salersID
     * @param contractID
     * @return
     */
    Boolean addAchievement(Integer salersID, String contractID, String companyID);

    /**
     * 根据指定条件获取下级公司销售列表
     * @param pageCpyConditionRequest
     * @return
     */
    PageResponse findCompanyAchievementByCondition(PageCpyConditionRequest pageCpyConditionRequest);

    /**
     * 根据当前公司ID获取下级公司业绩统计列表
     * @param cpyIDRequest
     * @return
     */
    PageResponse findCompanyStaticsAchievementByCompanyID(PageCpyIDRequest cpyIDRequest);

    /**
     * 根据当前公司ID获取下级4s店销售员统计列表
     * @param cpyIDRequest
     * @return
     */
    PageResponse findPersonStaticsAchievementByCompanyID(PageCpyIDRequest cpyIDRequest);

    /**
     * 获取当前公司下设备名列表
     * @param companyID
     * @return
     */
    List<String> getDevNameByCompanyID(String companyID);

    /**
     * 根据指定条件获取设备业绩信息
     * @param devAchConditionRequest
     * @return
     */
    PageResponse findDevAchievementByCondition(PageDevAchConditionRequest devAchConditionRequest);

    /**
     * 根据指定条件获取指定销售员业绩列表
     * @param achConditionRequest
     * @return
     */
    PageResponse findPersonAchievementByCondition(PagePersonAchConditionRequest achConditionRequest);

    /**
     * 获取指定月份与员工获取合同完成具体情况
     * @param personRequest
     * @return
     */
    Map<String, Integer> findSpecificPersonAchievement(SpecifiPersonRequest personRequest);

    /**
     * 根据指定条件获取指定公司业绩列表
     * @param cpyAchConditionRequest
     * @return
     */
    PageResponse findCpyAchievementByCondition(PageCpyAchConditionRequest cpyAchConditionRequest);

    /**
     * 根据指定月份和公司获取合同完成具体情况
     * @param specifiCpyRequest
     * @return
     */
    Map<String, Integer> findSpecificCpyAchievement(SpecifiCpyRequest specifiCpyRequest);
}
