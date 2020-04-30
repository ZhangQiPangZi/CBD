package com.cbd.cbdcompany.service;

import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcompany.dao.CompanyDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyDao companyDao;

    @Override
    public CompanyInfo findCompanyInfoByCompanyID(String companyID) {
        return companyDao.findCompanyInfoByCompanyID(companyID);
    }

    @Override
    public CompanyInfo findCompanyInfoByCompanyName(String companyName) {
        return companyDao.findCompanyInfoByCompanyName(companyName);
    }
}
