package com.cbd.cbdcontroller.controller.salesapp.workbench;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.AssignService;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.AssignQuery;
import com.cbd.cbdcommoninterface.response.salesapp.workbench.InstallerInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/20 22:24
 * @Description
 */
@RestController
@Slf4j
@RequestMapping(value = "/assign")
@Api(value = "指派工程师")
@CrossOrigin
public class Assigncontroller {
    @Autowired
    private AssignService assignService;

    /**
     * 根据能力等级排序
     * @return
     */
    @RequestMapping(value = "/order-by-level",method = RequestMethod.POST)
    @ApiOperation("按照能力等级排序")
    @ResponseBody
    public List<InstallerInfoVO> orderByLevel(@RequestBody AssignQuery query){
        return assignService.orderByLevel(query);
    }
    /**
     * 根据距离进行排序
     * @return
     */
    @RequestMapping(value = "/order-by-distance",method = RequestMethod.POST)
    @ApiOperation("按照距离进行排序")
    @ResponseBody
    public List<InstallerInfoVO> orderByDistance(@RequestBody AssignQuery query){
        return assignService.orderByDistance(query);
    }

    /**
     * 指派工程师
     */
    @RequestMapping(value = "/assign-installer",method = RequestMethod.POST)
    @ApiOperation("指派工程师")
    @ResponseBody
    public int assignInstaller(@RequestBody AssignQuery query){
        return assignService.assignInstaller(query);
    }

    /**
     * 该时间段暂无工程师 稍后指派
     * @param id
     * @return
     */
    @RequestMapping(value = "/assign-later",method = RequestMethod.GET)
    @ApiOperation("稍后指派")
    @ResponseBody
    public int assignLater(@RequestParam Integer id){
        return assignService.assignLater(id);
    }
}
