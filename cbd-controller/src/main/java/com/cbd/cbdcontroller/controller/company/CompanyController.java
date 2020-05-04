package com.cbd.cbdcontroller.controller.company;

import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.response.CpyLevelAndLocResponse;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@Api("公司接口")
@Slf4j
@CrossOrigin
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @ApiOperation("获取当前公司级别和所在区县的查询条件")
    @RequestMapping(value = "/findCompanyLevelAndLoc", method = RequestMethod.POST)
    public Result<CpyLevelAndLocResponse> findCompanyLevelAndLoc(@RequestParam(value = "companyID") String companyID){
        return Result.success(companyService.findCompanyLevelAndLoc(companyID));
    }

    @ApiOperation("获取当前公司的子公司列表")
    @RequestMapping(value = "/getAllCompanyListByCompanyName", method = RequestMethod.POST)
    public Result<List<CompanyInfo>> getAllCompanyListByCompanyName(@RequestParam(value = "companyName") String companyName){
        return Result.success(companyService.getAllCompanyListByCompanyName(companyName));
    }


}
