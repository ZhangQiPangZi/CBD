package com.cbd.cbdcompany.dao;

import com.cbd.cbdcommoninterface.dto.AddCpyDto;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CompanyDao {

    /**
     * 根据companyID获取公司信息
     * @param companyID
     * @return
     */
    CompanyInfo findCompanyInfoByCompanyID(@Param("companyID") String companyID);

    /**
     * 根据companyName获取公司信息
     * @param companyName
     * @return
     */
    CompanyInfo findCompanyInfoByCompanyName(@Param("companyName") String companyName);

    List<String> findAllCompanyIDByCompanyID(@Param("lft") Integer lft, @Param("rgt") Integer rgt);

    List<String> getUpCompanyIDByCompanyID(@Param("lft") Integer lft, @Param("rgt") Integer rgt);

    @Select("select Id from china where Name = #{name} and Pid = #{pid}")
    String getLocValue(@Param("name") String name, @Param("pid") String pid);

    @Select("select companyName from companyInfo where companyID != #{companyID} and companyLevel = 3")
    List<String> getHeadCpyList(@Param("companyID") String companyID);

    /**
     * 根据父公司ID查出所有子公司信息
     * @param lft
     * @param rgt
     * @return
     */
    List<CompanyInfo> getGradeCompanyList(@Param("lft") Integer lft, @Param("rgt") Integer rgt);

    @Update("update companyInfo set rgt = rgt+2 where rgt >= #{rgt}")
    void updateAddLft(@Param("rgt") Integer parentRgt);

    @Update("update companyInfo set lft = lft+2 where lft >= #{rgt}")
    void updateAddRgt(@Param("rgt") Integer parentRgt);

    @Insert("insert into companyInfo(companyID, companyName, companyPhone, companyCode, companyMail, companyTypeID" +
            ", companyProvince, companyCity, companyDistrict, companylevel, lft, rgt)" +
            "values(#{companyID}, #{companyName}, #{companyPhone}, #{companyCode}, #{companyMail}, #{companyTypeID}" +
            ", #{companyProvince}, #{companyCity}, #{companyDistrict}, #{companylevel}, #{lft}, #{rgt})")
    void insertCompany(AddCpyDto addCpyDto);

    @Select("select MAX(rgt) from companyInfo")
    Integer getMaxRgt();

    @Select("select companyTypeName from companyType")
    List<String> getCompanyTypeList();

    @Delete("delete from companyInfo where lft >= #{lft} and rgt <= #{rgt}")
    void delAllCompanyName(@Param("lft") Integer lft, @Param("rgt") Integer rgt);

    @Update("update companyInfo set lft = lft - (#{rgt} - #{lft} + 1) where lft >= #{lft}")
    void updateDelLft(@Param("lft") Integer lft, @Param("rgt") Integer rgt);

    @Update("update companyInfo set rgt = rgt - (#{rgt} - #{lft} + 1) where rgt >= #{rgt}")
    void updateDelRgt(@Param("lft") Integer lft, @Param("rgt") Integer rgt);
}