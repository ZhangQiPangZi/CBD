package com.cbd.cbdcontroller.controller.tracklast;



import com.cbd.cbdcommoninterface.cbd_interface.tracklast.ICarInfoService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.response.leiVo.CarForTreeVo;
import com.cbd.cbdcommoninterface.response.leiVo.CompanyAndCarInfoResponse;
import com.cbd.cbdcommoninterface.response.leiVo.findCarVo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shy_black
 * @date 2020/4/22 9:29
 * @Description:----------------实时定位+历史轨迹 carSearch和carPlatForm的控制器
 *
 * ----公司列表功能点：
 * 1.查询当前公司的一级公司，当前一级公司下的二级公司.....N级公司(做树状展示)---待做
 * 2.查询每个公司下属的车辆，以公司为单位包装定位信息传回----------实时定位
 * ------实现：使用公司id在car_Info中查找属于自己公司的车，再找出devID这一列
 *              然后在track表中使用devID查找所有对应的时间戳最近的坐标
 *
 * Vo顺序：当前公司id=第一级，找出公司的左右值 CUR_LEFT ,CUR_RIGHT
 * 找到当前公司的子公司信息
 * 一级公司：公司1（点击）
 *         二级公司：公司2
 *         二级公司：公司3（点击）
 *                  三级公司：公司5
 *         二级公司：公司4
 * 1.select  公司id ，公司名称，公司层级，parentID  from  companyInfo  where left<CUR_LEFT and right>CUR_RIGHT
 * 查询到的Vo--->companyLevelMsg---属性：companyID,companyName,companyLevel,parentID
 * 2.根据当前子公司的信息找出该公司下的所有车辆信息
 * 循环：
 *      select userName，phoneNum，devID from carInfo where companyID=#{companyID}，
 * 查询到的Vo--->sonCompanyCarsInfo---属性：userName,phoneNum，devID,
 * 3.根据devID从tarck表中查询当前车辆的定位信息
 * 循环：
 *      select nTime,dbLon,dbLat from track where devID=#{devID} (排序从小到大或从大到小 by nTime)
 *  查询到的Vo--->nTime,dbLon,dbLat
 *
 *
 * ----历史轨迹功能点：
 * 1.可根据设备号/手机号/车牌查询具体的车辆轨迹-----输入信息区分未完成，现在只能查devID
 * 2.可根据时间查询(起始时间，结束时间)-------------时间戳区间  √
 * 3.可按天查询（起始日期，结束日期）---------------同上        √
 * 4.同时还要传回车主姓名/电话/当前定位时间/当前位置，这些参数---车主信息统一根据devID在car_Info中查找
 * ----实时定位功能点：
 * 1.传回距现在最近时间的定位位置，------------------√
 * 2.传回车主信息（姓名，电话，定位时间，当前位置等参数）---同上
 */
@Api("公司树形结构与车辆信息控制器")
@Controller
@Slf4j
@RequestMapping("/CarPlatfrom")
public class CarPlatFormController {
    @Autowired
    private ICarInfoService carInfoService;


    @Autowired
    private IUserService userService;

    @ApiOperation(value = "公司树获取公司树结构及车辆信息及定位" ,httpMethod = "POST")
    @RequestMapping(value = "/getCarListInfo")
    @ResponseBody
    public Result<List<CompanyAndCarInfoResponse>> getCarInfo(@RequestParam String companyID) {
        log.info("开始获取车辆信息！！");
        List<CompanyAndCarInfoResponse> companyAndCarInfoResponse = new ArrayList<>();
        companyAndCarInfoResponse = carInfoService.getCompanyAndCarInfo(companyID);

        if(companyAndCarInfoResponse == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return Result.success(companyAndCarInfoResponse);

    }
    /**
     * 车辆搜索
     *
     * @author shy_black
     */
//    @ApiOperation(value = "模糊查询车辆信息--根据车辆devID/车主姓名/电话/车牌号",httpMethod = "POST")
//    @RequestMapping(value = "/findCar")
//    @ResponseBody
//    public Result<List<CarForTreeVo>> findCar(@RequestParam("companyID") String companyID,
//                                           @RequestParam("searchkey") String searchKey) {
//
//        List<CarForTreeVo> tmpList = carInfoService.findCarByOwner(companyID, searchKey);
//
//        return Result.success(tmpList);
//    }


}




//1.拿到公司id
//2.在公司id条件下找车辆信息列表--
//2.1-数据结构：
// 列表{ 一级公司id{
//                  car1info
//                  car2info,
//
//                  二级公司id{
//                          car4info，
//                          car5info
//                      },
//                  }
//      一级公司id{
//                  car6info，
//                  }
//2.2--=可以根据公司id找出下属一级、二级等公司id，
//      当前公司标记0，一级公司标记1，二级公司标记2，
//      存入ArrayList-companyList中(key=companyID,value=当前公司层级)
//    --新建一个map<companyID,carInfoList>
//    --遍历companyList的key值，遍历查询companyID旗下的车辆列表（car_info）
//    --在每个循环里新建一个carInfoList，把car_info存到carInfoList中，
//    --循环结束时将companyID与carInfoList建立映射
//      现在我有:
//    //公司id--映射--> 公司当前所在层级--取数据时：先从companyList中取得公司层级信息--展示公司树用
//    --公司id--映射-->公司对应的车辆列表----------然后从carInfoList中取公司下属车辆列表--展示公司对应下属车辆信息用

//    --存入ArrayList-companyCarList（key=companyID,value=car_info）
//3.返回该公司下所有车辆信息(姓名，电话，地图坐标)