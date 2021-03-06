package com.cbd.cbdcontroller.controller.salesapp;

import com.cbd.cbdcommoninterface.cbd_interface.contract.ContractService;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.messagelist.MessageListService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.user.UserService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.AssignService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.GenerateService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.InputInfoService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.workbench.PayService;
import com.cbd.cbdcommoninterface.pojo.device.DeviceInfo;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.DevIdDO;
import com.cbd.cbdcommoninterface.pojo.salesapp.user.SalesInfoDO;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.AssignQuery;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.OrderInfoQuery;
import com.cbd.cbdcommoninterface.request.salesapp.workbench.PayLogQuery;
import com.cbd.cbdcommoninterface.response.ContractInfoResponse;
import com.cbd.cbdcommoninterface.response.DevInfoResponse;
import com.cbd.cbdcommoninterface.response.PageContractListResponse;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.response.salesapp.messagelist.OrderInfoVO;
import com.cbd.cbdcommoninterface.response.salesapp.workbench.GenerateVO;
import com.cbd.cbdcommoninterface.response.salesapp.workbench.InstallerInfoVO;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/5/10 9:46
 * @Description
 */
@RestController
@RequestMapping("/sales-app")
@Api("销售人员接口")
@Slf4j
@CrossOrigin
public class SalesappController {
    @Autowired
    private MessageListService messageListService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssignService assignService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private InputInfoService inputInfoService;
    @Autowired
    private GenerateService generateService;
    @Autowired
    private PayService payService;

    @RequestMapping(value = "/get-list",method = RequestMethod.POST)
    @ApiOperation("获取订单列表")
    @ResponseBody
    public Result<PageResponse> getList(@RequestParam(required = false) Integer orderTypeCode, @RequestBody PageRequest pageRequest,@RequestParam Integer salesId){
        return Result.success(messageListService.getList(orderTypeCode,pageRequest,salesId));
    }

    @RequestMapping(value = "/processed-count",method = RequestMethod.GET)
    @ApiOperation("已指派订单数量")
    @ResponseBody
    public int processedCount(@RequestParam Integer salesId){
        return messageListService.processedCount(salesId);
    }

    @RequestMapping(value = "/pending-count",method = RequestMethod.GET)
    @ApiOperation("待指派订单数量")
    @ResponseBody
    public int pendingCount(@RequestParam Integer salesId){
        return messageListService.pendingCount(salesId);
    }

    @RequestMapping(value = "/get-order-info",method = RequestMethod.GET)
    @ApiOperation("获取订单详细信息")
    @ResponseBody
    public OrderInfoVO getOrderInfo(@RequestParam Integer id){
        return messageListService.getOrderInfo(id);
    }

    @RequestMapping(value = "/reassign",method = RequestMethod.GET)
    @ApiOperation("重新指派工程师")
    @ResponseBody
    public DevIdDO reAssign(@RequestParam Integer id, @RequestParam Integer orderTypeCode){
        return messageListService.reAssign(id,orderTypeCode);
    }

    @RequestMapping(value = "/user-info",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("用户信息")
    public SalesInfoDO getUserInfo(@RequestParam String phoneNum){
        return userService.getUserInfo(phoneNum);
    }

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
    public int assignLater(@RequestParam Integer id,@RequestParam String devId,@RequestParam String simId){
        return assignService.assignLater(id,devId,simId);
    }

    /**
     *
     * @param companyID
     * @return
     */
    @RequestMapping(value = "/contract-list",method = RequestMethod.GET)
    @ApiOperation("返回合同列表")
    @ResponseBody
    public Result<List<PageContractListResponse>> findContractList(@RequestParam String companyID){
        //由于合同的分发由张祺负责，此处调用张祺的接口
        return Result.success(contractService.findUsingContractListByCompanyID(companyID));
    }

    /**
     * @Description 客户从合同列表中选择适合自己的合同 根据合同Id 查看该合同的详细信息
     * @param contractID
     * @return
     */
    @RequestMapping(value = "/contract-info",method = RequestMethod.GET)
    @ApiOperation("获取合同的详细信息")
    @ResponseBody
    public Result<ContractInfoResponse> findContractInfo(@RequestParam String contractID){
        return Result.success(contractService.findContractInfoByContractID(contractID));
    }

    /**
     * @Description 返回该合同可选的所有设备的list
     * @param devName
     * @param companyID
     * @return
     */
    @RequestMapping(value = "/find-devInfo-list",method = RequestMethod.GET)
    @ApiOperation("获取某合同可选的所有设备列表")
    @ResponseBody
    public Result<List<DevInfoResponse>> findDevInfoList(@RequestParam String devName, @RequestParam String companyID){
        return Result.success(deviceService.findDevInfoListByDevNameAndCompanyID(devName,companyID));
    }

    /**
     * 选择设备后设备的状态变为出库
     * @param devId
     * @return
     */
    @RequestMapping(value = "update-dev-status",method = RequestMethod.GET)
    @ApiOperation("改变设备状态")
    @ResponseBody
    public Boolean updateDevStatus(@RequestParam String devId){
        return deviceService.updateDevStatusByDevIDAndDevStatus(devId, DeviceInfo.DevStatus.OUT.ordinal());
    }

    /**
     * 录入信息后 将生成的订单id返回给前端 后期指派工程师会用到
     * @param query
     * @return
     */
    @RequestMapping(value = "confirm",method = RequestMethod.POST)
    @ApiOperation(value = "用户信息录入")
    @ResponseBody
    public int confirmInputInfo(@RequestBody OrderInfoQuery query){
        return inputInfoService.confirmInputInfo(query);
    }

    /**
     * 生成保单
     * @return
     */
    @RequestMapping(value = "generate",method = RequestMethod.GET)
    @ApiOperation(value = "生成保单")
    @ResponseBody
    public GenerateVO generateContract(){
        return generateService.generateContract();
    }

    /**
     * 支付成功后 将支付信息存入数据库
     * @return
     */
    @RequestMapping(value = "pay-log",method = RequestMethod.POST)
    @ApiOperation(value = "支付信息录入")
    @ResponseBody
    public int payLog(@RequestBody PayLogQuery query){
        return payService.payLog(query);
    }

    /**
     * 根据订单id获取数据库的orderId
     * @param id
     * @return
     */
    @RequestMapping(value = "get-uuid",method = RequestMethod.GET)
    @ApiOperation(value = "获取uuid")
    @ResponseBody
    public String getUUID(@RequestParam Integer id){
        return messageListService.getUUID(id);
    }


}
