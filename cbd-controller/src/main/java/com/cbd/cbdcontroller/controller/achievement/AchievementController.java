package com.cbd.cbdcontroller.controller.achievement;

import com.cbd.cbdcommoninterface.cbd_interface.achievement.AchievementService;
import com.cbd.cbdcommoninterface.request.PageCpyAchConditionRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/achievement")
@Api("业绩接口")
@Slf4j
@CrossOrigin
public class AchievementController {
    @Autowired
    AchievementService achievementService;

    @ApiOperation("根据指定条件获取下级公司销售列表")
    @RequestMapping(value = "/findCompanyAchievementByCondition", method = RequestMethod.POST)
    public Result<PageResponse> findCompanyAchievementByCondition(@RequestBody PageCpyAchConditionRequest pageCpyAchConditionRequest){
        return Result.success(achievementService.findCompanyAchievementByCondition(pageCpyAchConditionRequest));
    }
}
