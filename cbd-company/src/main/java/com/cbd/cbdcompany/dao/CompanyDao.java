package com.cbd.cbdcompany.dao;

import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
}
