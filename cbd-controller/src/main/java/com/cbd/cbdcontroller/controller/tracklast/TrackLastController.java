package com.cbd.cbdcontroller.controller.tracklast;



import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ICarInfoService;
import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ITrackLastService;
import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;
import com.cbd.cbdcommoninterface.response.leiVo.RealTrackVo;
import com.cbd.cbdcommoninterface.response.leiVo.StartToEndTrackVo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public Result<List<RealTrackVo>> getGPSInfoByTEID(@RequestBody StartToEndTrackVo s) {

        log.info("开始获取历史轨迹数据，时间段为"+s.getStartTime(),"---到---"+s.getEndTime());
        boolean success = false;
        //获取选定设备号
        if(carInfoService.hasDevID(s.getKey()) == 0) {
            log.info("-----无该设备---");
            return Result.error(CodeMsg.EMPTY_DEVID_ERROR);//1024
        }

        log.info("开始获取设备定位：", s.getKey());
        List<RealTrackVo> trackInfo = TrackLastService.getTrackInfoByTEID(s.getKey(),s.getStartTime(),s.getEndTime());
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
     * @param key
     * @return
     */
    @ApiOperation(value = "(精确查找)根据 设备ID/手机号 查询车辆的实时定位",httpMethod = "GET")
    @RequestMapping("/realTrack")
    public Result<RealTrackVo> getRealTrack(@RequestParam(value = "key") String key) {

        //获取实时定位
        RealTrackVo realTrack = TrackLastService.getRealTrackByTEID(key);

        if(realTrack.getDevID() == null) {
            log.info("获取实时位置失败，请检查devID/手机号");
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        log.info("获取实时定位成功,定位信息："+realTrack.toString());

        return Result.success(realTrack);
    }

}
