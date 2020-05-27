package com.cbd.cbdcontroller.controller.tracklast;



import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ICarInfoService;
import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ITrackLastService;
import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;
import com.cbd.cbdcommoninterface.response.leiVo.RealTrackVo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

//import BackManage.common.utils.utilsForPlatForm.CommonAlarmDict;

/**
 * @author shy_black
 * @date 2020/4/22 9:28
 * @Description:轨迹实时控制器 历史轨迹
 */
@Api("车辆实时定位与历史轨迹")
@Slf4j
@RestController
@RequestMapping("/track")
public class TrackLastController {

    @Resource
    private ITrackLastService TrackLastService;

    @Resource
    private ICarInfoService carInfoService;
    /**-------------------------------------------------------------------------------
     * 根据设备号获得车辆历史轨迹信息--------（单个车辆）------(地图显示车辆位置信息)
     * 前端默认传来的时间戳为X小时内--->现在
     *
     * 操作流程：
     * 1.前端通过模糊查询 / 公司树接口获得devID)
     * 2.devID传到这个接口，去查询历史轨迹
     *
     *
     * @author shy_black
     */
    @ApiOperation(value = "查询设备在指定时间段的历史轨迹",httpMethod = "POST")
    @RequestMapping(value = "/TrackLast", method = RequestMethod.POST)
    public Result<List<TrackLast>> getGPSInfoByTEID(@RequestParam(value = "devID",required = true) String devID,
                                                    @RequestParam(value = "startTime",required = false,defaultValue = "0") Integer startTime,
                                                    @RequestParam(value = "endTime",required = false,defaultValue = "0") Integer endTime) {

        log.info("开始获取历史轨迹数据，时间段为"+startTime,"---到---"+endTime);
        boolean success = false;
        //获取选定设备号
        if(devID == null) {
            log.info("-----无该设备号---");
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        log.info("开始获取设备定位：", devID);
        List<TrackLast> trackInfo = TrackLastService.getTrackInfoByTEID(devID,startTime,endTime);
        if(trackInfo == null) {
            log.info("-----轨迹信息为空---");

            return Result.error(CodeMsg.NULL_TRACK_MSG_ERROR);
        }
        log.info("获取定位成功");

        log.info("设备定位信息："+trackInfo);
        return Result.success(trackInfo);
    }

    /**
     * 查询车辆实时定位并返回姓名，电话，定位信息
     * @param devID
     * @return
     */
    @ApiOperation(value = "根据设备ID查询车辆的实时定位",httpMethod = "GET")
    @RequestMapping("/realTrack")
    public Result<RealTrackVo> getRealTrack(@RequestParam(value = "devID") String devID) {

        //获取实时定位
        TrackLast realTrack = TrackLastService.getRealTrackByTEID(devID);

        //根据车的owerID在user表中找出车主的主要信息--姓名，电话,车牌号
        Map<String,Object> baseInfo = carInfoService.findUserInfoByDevID(devID);

        RealTrackVo realTrackVo = new RealTrackVo();

        realTrackVo.setUserName(baseInfo.get("userName").toString());
        realTrackVo.setPhoneNum(baseInfo.get("phoneNum").toString());
        realTrackVo.setCarPlateNum(baseInfo.get("carPlateNum").toString());
        realTrackVo.setTrackLast(realTrack);

        if(realTrack == null) {
            log.info("获取实时位置失败，请检查devID");
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        log.info("获取实时定位成功,定位信息："+realTrack.toString());
        log.info("成功返回姓名-电话-定位信息"+baseInfo.toString());

        return Result.success(realTrackVo);
    }

}
