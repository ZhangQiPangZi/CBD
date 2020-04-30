package com.cbd.cbdcommoninterface.cbd_interface.company;

import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;

public interface CompanyService {
    CompanyInfo findCompanyInfoByCompanyID(String companyID);
    CompanyInfo findCompanyInfoByCompanyName(String companyName);
}
