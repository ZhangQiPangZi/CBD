package com.cbd.cbdcontroller.controller.achievement;

import com.cbd.cbdcommoninterface.request.PageCpyAchConditionRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/achievement")
@Api("业绩接口")
@Slf4j
@CrossOrigin
public class AchievementController {

    @ApiOperation("根据指定条件获取下级公司销售列表")
    public Result<PageResponse> findCompanyAchievementByCondition(@RequestBody PageCpyAchConditionRequest pageCpyAchConditionRequest){
        return null;
    }
}
