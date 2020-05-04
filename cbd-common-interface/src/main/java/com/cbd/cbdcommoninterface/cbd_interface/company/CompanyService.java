package com.cbd.cbdcommoninterface.cbd_interface.company;

import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.response.CpyLevelAndLocResponse;

import java.util.List;

public interface CompanyService {
    /**
     * 获取当前公司级别和所在区县的查询条件
     * @param companyID
     * @return
     */
    CpyLevelAndLocResponse findCompanyLevelAndLoc(String companyID);

    /**
     * 获取当前公司的子公司列表
     * @param companyName
     * @return
     */
    List<CompanyInfo> getAllCompanyListByCompanyName(String companyName);

    /**
     * 获取当前公司的父公司id列表
     * @param companyID
     * @return
     */
    List<String> getUpCompanyIDByCompanyID(String companyID);

    CompanyInfo findCompanyInfoByCompanyID(String companyID);
    CompanyInfo findCompanyInfoByCompanyName(String companyName);
}
