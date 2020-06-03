package com.cbd.cbdcontroller.controller.achievement;

import com.cbd.cbdcommoninterface.cbd_interface.achievement.AchievementService;
import com.cbd.cbdcommoninterface.request.*;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/achievement")
@Api("业绩接口")
@Slf4j
@CrossOrigin
public class AchievementController {
    @Autowired
    AchievementService achievementService;

    @ApiOperation("(测试用，上线删除)传入销售员id和所完成的这单使用的合同ID来增加销售额")
    @RequestMapping(value = "/addAchievement", method = RequestMethod.POST)
    public Result<Boolean> addAchievement(@RequestParam(value = "salersID", required = false) Integer salersID, @RequestParam(value = "contractID", required = false) String contractID, @RequestParam(value = "companyID", required = false) String companyID){
        return Result.success(achievementService.addAchievement(salersID, contractID, companyID));
    }

    @ApiOperation("根据指定条件获取下级公司销售列表")
    @RequestMapping(value = "/findCompanyAchievementByCondition", method = RequestMethod.POST)
    public Result<PageResponse> findCompanyAchievementByCondition(@RequestBody PageCpyConditionRequest pageCpyConditionRequest){
        return Result.success(achievementService.findCompanyAchievementByCondition(pageCpyConditionRequest));
    }

    @ApiOperation("根据当前公司ID获取下级公司业绩统计列表")
    @RequestMapping(value = "/findCompanyStaticsAchievementByCompanyID", method = RequestMethod.POST)
    public Result<PageResponse> findCompanyStaticsAchievementByCompanyID(@RequestBody PageCpyIDRequest cpyIDRequest){
        return Result.success(achievementService.findCompanyStaticsAchievementByCompanyID(cpyIDRequest));
    }

    @ApiOperation("根据当前公司ID获取下级4s店销售员统计列表")
    @RequestMapping(value = "/findPersonStaticsAchievementByCompanyID", method = RequestMethod.POST)
    public Result<PageResponse> findPersonStaticsAchievementByCompanyID(@RequestBody PageCpyIDRequest cpyIDRequest){
        return Result.success(achievementService.findPersonStaticsAchievementByCompanyID(cpyIDRequest));
    }

    @ApiOperation("获取当前公司下设备名列表")
    @RequestMapping(value = "/getDevNameByCompanyID", method = RequestMethod.POST)
    public Result<List<String>> getDevNameByCompanyID(@RequestBody CompanyIDRequest companyIDRequest){
        return Result.success(achievementService.getDevNameByCompanyID(companyIDRequest.getCompanyID()));
    }

    @ApiOperation("根据指定条件获取设备业绩信息")
    @RequestMapping(value = "/findDevAchievementByCondition", method = RequestMethod.POST)
    public Result<PageResponse> findDevAchievementByCondition(@RequestBody PageDevAchConditionRequest devAchConditionRequest){
        return Result.success(achievementService.findDevAchievementByCondition(devAchConditionRequest));
    }

    @ApiOperation("根据指定条件获取指定销售员业绩列表")
    @RequestMapping(value = "/findPersonAchievementByCondition", method = RequestMethod.POST)
    public Result<PageResponse> findPersonAchievementByCondition(@RequestBody PagePersonAchConditionRequest achConditionRequest){
        return Result.success(achievementService.findPersonAchievementByCondition(achConditionRequest));
    }

    @ApiOperation("获取指定月份与员工获取合同完成具体情况")
    @RequestMapping(value = "/findSpecificPersonAchievement", method = RequestMethod.POST)
    public Result<Map<String, Integer>> findSpecificPersonAchievement(@RequestBody SpecifiPersonRequest personRequest){
        return Result.success(achievementService.findSpecificPersonAchievement(personRequest));
    }

    @ApiOperation("根据指定条件获取指定公司业绩列表")
    @RequestMapping(value = "/findCpyAchievementByCondition", method = RequestMethod.POST)
    public Result<PageResponse> findCpyAchievementByCondition(@RequestBody PageCpyAchConditionRequest cpyAchConditionRequest){
        return Result.success(achievementService.findCpyAchievementByCondition(cpyAchConditionRequest));
    }

    @ApiOperation("根据指定月份和公司获取合同完成具体情况")
    @RequestMapping(value = "/findSpecificCpyAchievement", method = RequestMethod.POST)
    public Result<Map<String, Integer>> findSpecificCpyAchievement(@RequestBody SpecifiCpyRequest specifiCpyRequest){
        return Result.success(achievementService.findSpecificCpyAchievement(specifiCpyRequest));
    }


}
