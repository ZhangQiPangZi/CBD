package com.cbd.cbdcontroller.controller.company;

import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.pojo.company.CompanyInfo;
import com.cbd.cbdcommoninterface.request.AddCpyRequest;
import com.cbd.cbdcommoninterface.request.CompanyIDRequest;
import com.cbd.cbdcommoninterface.request.CompanyNameRequest;
import com.cbd.cbdcommoninterface.response.CompanyListResponse;
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
    public Result<CpyLevelAndLocResponse> findCompanyLevelAndLoc(@RequestBody CompanyIDRequest companyIDRequest){
        return Result.success(companyService.findCompanyLevelAndLoc(companyIDRequest.getCompanyID()));
    }

    @ApiOperation("获取当前公司的子公司列表")
    @RequestMapping(value = "/getAllCompanyListByCompanyName", method = RequestMethod.POST)
    public Result<List<CompanyInfo>> getAllCompanyListByCompanyName(@RequestBody CompanyNameRequest companyNameRequest){
        return Result.success(companyService.getAllCompanyListByCompanyName(companyNameRequest.getCompanyName()));
    }

    @ApiOperation("获取分级完成后的子公司名称列表")
    @RequestMapping(value = "/getGradeCompanyList", method = RequestMethod.POST)
    public Result<CompanyListResponse> getGradeCompanyList(@RequestBody CompanyIDRequest companyIDRequest){
        return Result.success(companyService.getGradeCompanyList(companyIDRequest.getCompanyID()));
    }

    @ApiOperation("获取所有总公司名称列表，平台管理员访问")
    @RequestMapping(value = "/getHeadCpyList", method = RequestMethod.POST)
    public Result<List<String>> getHeadCpyList(@RequestBody CompanyIDRequest companyIDRequest){
        return Result.success(companyService.getHeadCpyList(companyIDRequest.getCompanyID()));
    }

    @ApiOperation("获取所有公司类别，平台管理员访问")
    @RequestMapping(value = "/getCompanyTypeList", method = RequestMethod.POST)
    public Result<List<String>> getCompanyTypeList(){
        return Result.success(companyService.getCompanyTypeList());
    }

    @ApiOperation("添加公司，平台管理员访问")
    @RequestMapping(value = "/addCompanyByCpyName", method = RequestMethod.POST)
    public Result<Boolean> addCompanyByCpyName(@RequestBody AddCpyRequest addCpyRequest){
        return Result.success(companyService.addCompanyByCpyName(addCpyRequest));
    }

    @ApiOperation("删除公司及下属子公司，平台管理员访问")
    @RequestMapping(value = "/delAllCompanyByCpyName", method = RequestMethod.POST)
    public Result<Boolean> delAllCompanyByCpyName(@RequestBody CompanyNameRequest companyNameRequest){
        return Result.success(companyService.delAllCompanyByCpyName(companyNameRequest.getCompanyName()));
    }

}
