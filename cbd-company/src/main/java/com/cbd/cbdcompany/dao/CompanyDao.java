package com.cbd.cbdcompany.dao;

import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CompanyDao {

    /**
     * 根据companyID获取公司信息
     * @param companyID
     * @return
     */
    CompanyInfo findCompanyInfoByCompanyID(String companyID);

    /**
     * 根据companyName获取公司信息
     * @param companyName
     * @return
     */
    CompanyInfo findCompanyInfoByCompanyName(String companyName);
}
