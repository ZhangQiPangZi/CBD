package com.cbd.cbdachievement.dao;

import com.cbd.cbdcommoninterface.dto.*;
import com.cbd.cbdcommoninterface.pojo.achievement.AchievementCompanyInfo;
import com.cbd.cbdcommoninterface.pojo.achievement.AchievementDeviceInfo;
import com.cbd.cbdcommoninterface.pojo.achievement.AchievementInfo;
import com.cbd.cbdcommoninterface.request.QueryUserAndCpyRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AchievementDao {
    /**
     * 根据条件获取公司列表
     * @param cpyConditionDto
     * @return
     */
    List<AchievementCompanyInfo> findAchCpyInfoByCondition(CpyConditionDto cpyConditionDto);

    /**
     * 获取员工指定日期业绩
     * @param salersID
     * @param month
     * @param year
     * @return
     */
    @Select("select achievement from achievementPersonInfo where salersID = #{salersID} and month = #{month} and year = #{year}")
    Float findPersonAchievement(@Param("salersID") Integer salersID, @Param("month") int month, @Param("year") int year);

    /**
     * 更新或新增员工业绩
     * @param month
     * @param year
     * @param curAchievement
     * @param salersID
     * @param companyID
     */
    void replacePersonAchievement(@Param("month") int month, @Param("year") int year, @Param("curAchievement") Float curAchievement, @Param("salersID") Integer salersID, @Param("companyID") String companyID);

    /**
     * 获取合同指定日期完成数量
     * @param contractID
     * @param month
     * @param year
     * @return
     */
    @Select("select contractCount from achievementContractInfo where contractID = #{contractID} and month = #{month} and year = #{year} and salersID = #{salersID} and companyID  =#{companyID}")
    Integer findContractCount(@Param("contractID") String contractID, @Param("month") int month, @Param("year") int year, @Param("salersID") Integer salersID, @Param("companyID") String companyID);

    /**
     * 更新或新增合同业绩
     * @param month
     * @param year
     * @param salersID
     * @param companyID
     * @param contractID
     * @param curContractCount
     */
    void replaceContractAchievement(@Param("month") int month, @Param("year") int year, @Param("salersID") Integer salersID, @Param("companyID") String companyID, @Param("contractID") String contractID, @Param("curContractCount") Integer curContractCount, @Param("contractTypeName") String contractTypeName);

    /**
     * 获取指定日期公司业绩
     * @param parentCpyID
     * @param month
     * @param year
     * @return
     */
    @Select("select achievement from achievementCompanyInfo where companyID = #{parentCpyID} and month = #{month} and year = #{year}")
    Float findCpyAchievement(@Param("parentCpyID") String parentCpyID, @Param("month") int month, @Param("year") int year);

    /**
     * 更新或新增公司业绩
     * @param month
     * @param year
     * @param curCompanyAchievement
     * @param parentCpyID
     */
    void replaceCompanyAchievement(@Param("month") int month, @Param("year") int year, @Param("curCompanyAchievement") Float curCompanyAchievement, @Param("parentCpyID") String parentCpyID);

    /**
     * 获取公司统计业绩信息
     * @param parentCpyID
     * @return
     */
    AchievementCompanyInfo findStaticsCpyAchievement(@Param("parentCpyID") String parentCpyID);

    /**
     * 更新或删除公司统计业绩信息
     * @param parentCpyID
     * @param curAchievementCount
     * @param salersCount
     * @param contractCount
     */
    void replaceCompanyStatistics(@Param("parentCpyID") String parentCpyID, @Param("curAchievementCount") Float curAchievementCount, @Param("salersCount") Integer salersCount, @Param("contractCount") Integer contractCount);

    /**
     * 获取设备指定日期销售数量
     * @param devTypeID
     * @param parentCpyID
     * @param month
     * @param year
     * @return
     */
    @Select("select salesNums from achievementDeviceInfo where companyID = #{parentCpyID} and devTypeID = #{devTypeID} and month = #{month} and year = #{year}")
    Integer findDevAchievement(@Param("devTypeID") Integer devTypeID, @Param("parentCpyID") String parentCpyID, @Param("month") int month, @Param("year") int year);

    /**
     * 更新或删除设备业绩信息
     * @param devTypeID
     * @param parentCpyID
     * @param curSalesNums
     * @param month
     * @param year
     */
    void replaceDeviceAchievement(@Param("devTypeID") Integer devTypeID, @Param("parentCpyID")String parentCpyID, @Param("curSalesNums") Integer curSalesNums, @Param("month") int month, @Param("year") int year);

    /**
     * 获取所有子公司销售人员ID列表
     * @param lft
     * @param rgt
     * @return
     */
    List<String> findPersonIDByCompanyID(@Param("lft") Integer lft, @Param("rgt") Integer rgt);

    /**
     * 获取指定员工的销售额统计
     * @param salersID
     * @return
     */
    Integer getPersonAchievementByPersonID(@Param("salersID") String salersID);

    /**
     * 获取指定员工的完成合同统计
     * @param salersID
     * @return
     */
    Integer getPersonContractCountByPersonID(@Param("salersID") String salersID, @Param("companyID") String companyID);

    /**
     * 获取当前公司下有销售记录的设备类型ID列表
     * @param lft
     * @param rgt
     * @return
     */
    List<String> getDevNameByCompanyID(@Param("lft") Integer lft, @Param("rgt") Integer rgt);


    String findDevNameByID(@Param("devTypeID") String devTypeID);

    /**
     * 根据条件获取设备业绩信息列表
     * @param devAchConditionDto
     * @return
     */
    List<AchievementDeviceInfo> findDevAchievementByCondition(DevAchConditionDto devAchConditionDto);

    /**
     * 根据条件获取指定员工的业绩信息
     * @param personConditionDto
     * @return
     */
    List<AchievementInfo> findPersonAchievementByCondition(PersonConditionDto personConditionDto);

    /**
     * 根据条件获取指定公司的业绩信息
     * @param cpyAchConditionDto
     * @return
     */
    List<AchievementInfo> findCpyAchievementByCondition(CpyAchConditionDto cpyAchConditionDto);

    List<CpyNameIDDto> queryCpyIDByKey(QueryUserAndCpyRequest queryUserAndCpyRequest);

    List<UserNameIDDto> queryUserIDByKey(QueryUserAndCpyRequest queryUserAndCpyRequest);

    @Select("select distinct contractTypeName from achievementContractInfo where salersID = #{salersID}")
    List<String> getContractTypeNameBySalersID(@Param("salersID") String id);

    @Select("select distinct contractTypeName from achievementContractInfo where companyID = #{companyID}")
    List<String> getContractTypeNameByCpyID(@Param("companyID") String id);

    /**
     * 获取下属4s店销售业绩列表
     * @param pieCpyAchDto
     * @return
     */
    List<PieCpyAchResultDto> findPieCpyAchievementByDate(PieCpyAchDto pieCpyAchDto);

    /**
     * 获取所有销售车型的统计列表
     * @param pieCpyAchDto
     * @return
     */
    List<String> getAllCarType(PieCpyAchDto pieCpyAchDto);

    /**
     * 获取当前公司指定年份的合同销售列表
     * @param lieCpyDto
     * @return
     */
    List<LieCpyAchResultDto> findLieCpyAchievementByYear(LieCpyDto lieCpyDto);
}
