package com.cbd.cbdcommoninterface.cbd_interface.user;

import com.cbd.cbdcommoninterface.pojo.leipojo.company_info;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/22 10:11
 * @Description: 500行
 */
@Component
public interface ICompanyInfoService {

     company_info findById(String companyID);

     int getCompanyTypeByID(String companyID);

     List<Map<String,Object>> getCompanyInfoByparentID(String companyID, String strTableList, String strFieldList, String strWhere) ;

    int findUserByPhoneNum(String phoneNum);

     List<String> getParentCompanyByCompanyID(String companyID) ;

}