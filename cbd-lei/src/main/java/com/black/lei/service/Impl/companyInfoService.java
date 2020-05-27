package com.black.lei.service.Impl;



import com.black.lei.dao.car_infoDao;
import com.black.lei.dao.company_infoDao;
import com.black.lei.dao.userDao;

import com.cbd.cbdcommoninterface.cbd_interface.user.ICompanyInfoService;
import com.cbd.cbdcommoninterface.pojo.leipojo.company_info;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import com.cbd.cbdcommoninterface.response.leiVo.LftAndRgtVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/22 10:12
 * @Description:
 */
@Service
public class companyInfoService implements ICompanyInfoService {

    //根据companyID查找公司信息
    @Resource
    private com.black.lei.dao.company_infoDao company_infoDao;

    @Resource
    private userDao userDao;

    @Resource
    private car_infoDao car_infoDao;

    public company_info findById(String companyID) {
        company_info findCompany = company_infoDao.findById(companyID);
        return findCompany;
    }

    public int getCompanyTypeByID(String companyID) {return 0;}

    public List<Map<String,Object>>  getCompanyInfoByparentID(String companyID, String strTableList,String strFieldList,String strWhere) {

        return null;
    }

    @Override
    public int findUserByPhoneNum(String phoneNum) {
        user userInfo = userDao.findByPhone(phoneNum);
        if(userInfo != null)//大于一，说明已经有同手机号的员工
            return 1;
        else//否则可以正常添加员工
            return 0;
    }


    @Override
    public List<String> getParentCompanyByCompanyID(String companyID) {
        //1.获取当前公司的左值和右值
        LftAndRgtVo lftAndRgtVo = car_infoDao.getLftAndRgt(companyID);
        String lft = lftAndRgtVo.getLft();
        String rgt = lftAndRgtVo.getRgt();
        //获取当前公司的上级公司信息
        List<String> companyNameList = company_infoDao.getParentCompanyByCompanyID(lft,rgt);
        return null;
    }
}
