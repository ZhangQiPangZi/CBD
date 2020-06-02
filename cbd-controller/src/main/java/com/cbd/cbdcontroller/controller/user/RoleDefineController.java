package com.cbd.cbdcontroller.controller.user;


import com.cbd.cbdcommoninterface.cbd_interface.user.IPowerService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IRoleDefineService;
import com.cbd.cbdcommoninterface.pojo.leipojo.power;
import com.cbd.cbdcommoninterface.pojo.leipojo.role;
import com.cbd.cbdcommoninterface.response.leiVo.RoleResponseVo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shy_black
 * @date 2020/4/22 9:26
 * @Description: 功能定位：管理员，最高权限
 * 功能需求：显示所有角色
 * 角色管理：
 * 1.返回所有角色与其拥有的权限-----------------------√
 * 2.可选中某个角色，设置它的相关权限（按钮形式），赋权或取消权限
 * 根据获取的角色id，对角色对应的权限表中的权限状态进行更改-----√
 * 3.返回所有权限模块，可选中一个权限模块---------------------不做权限模块了，改为全局查找
 * 进行添加，修改，删除权限模块下权限的功能---------------√
 */
@Api("用户角色与权限相关控制器")
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleDefineController {

    @Autowired
    private IRoleDefineService roleDefineService;

    @Autowired
    private IPowerService powerService;

    //返回值为role列表，每个role下属的权限，及权限的状态
    //RespVo==
    //    private int roleID;
    //    private int roleName;
    //    private List<?> data;
    @ApiOperation(value = "管理员状态，获取DB中所有角色信息", httpMethod = "POST")
    @RequestMapping("/showRoleList")
    public Result<List<RoleResponseVo>> showRoleList(HttpServletRequest req) {

        List<RoleResponseVo> roleResponseVo = roleDefineService.getRoleDefines();
        if (roleResponseVo == null) {
            throw new GlobalException(CodeMsg.ROLE_MSG_GET_ERROR);
        }
        return Result.success(roleResponseVo);
    }

    //在角色页面选择开关，例：点到那个，就请求这个url，传的值为：roleID-powerID-status

    /**
     * 对role_power表直接更改，因为该表维护了角色与权限之间的关系
     * 一开始就直接把该角色需要的所有权限给到位，避免新增角色
     * //已在role_power表加status该字段代表前端页面的开关---角色是否有对应权限
     *
     * @param roleID--需要更新的roleID
     * @param powerID--需要更新的powerID
     * @param status--需要更新后的状态（目标状态）
     * @return
     */
    @ApiOperation(value = "更新角色的权限状态 开/关", httpMethod = "GET")
    @RequestMapping("/updateRolePower")
    public Result<String> updateRolePower(@RequestParam(value = "roleID") int roleID,
                                          @RequestParam(value = "powerID") int powerID,
                                          @RequestParam(value = "status") int status) {
        Integer res = roleDefineService.updateRole(roleID, powerID, status);

        log.info("res == " + res);
        if (res == 1) {
            return Result.success("更新成功");
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }

    @ApiOperation(value = "新增角色", httpMethod = "POST")
    @RequestMapping("/addRole")
    public Result<String> addRole(@RequestParam("roleName") String roleName,
                                  @RequestParam("remark") String remark,
                                  @RequestParam("powerList") List<Integer> powerID) {
        Integer res = 0;
        res = roleDefineService.addRole(roleName, remark, powerID);
        if (res == 1) {
            return Result.success("添加角色成功");
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 需要管理员权限可见--
     *
     * @return
     */
    @ApiOperation(value = "获取权限详情列表", httpMethod = "POST")
    @RequestMapping("/getPowerList")
    public Result<List<power>> showPowerList() {
        List<power> powerList = powerService.getPowerList();
        return Result.success(powerList);
    }

    @ApiOperation(value = "模糊查找权限列表", httpMethod = "GET")
    @RequestMapping("/findLikelyPower")
    public Result<List<power>> findLikelyPower(@RequestParam(value = "key") String key) {
        List<power> resList = powerService.findLikelyPower(key);
        if (resList.size() == 0) {
            return Result.error(CodeMsg.SERVER_ERROR);
        } else {
            return Result.success(resList);
        }
    }

    @ApiOperation(value = "添加权限", httpMethod = "POST")
    @RequestMapping("/addPower")
    public Result<String> addPower(@RequestBody power power) {

        Integer res = powerService.addPower(power);
        if (res == 1) {
            return Result.success("添加权限成功！");
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    //当关闭权限时，会自动关闭角色-权限表的状态
    @ApiOperation(value = "修改权限", httpMethod = "POST")
    @RequestMapping("/updatePower")
    public Result<String> updatePower(@RequestBody power power) {
        Integer res = powerService.updatePower(power);
        if (res == 1) {
            return Result.success("更新权限成功！");
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "删除权限", httpMethod = "POST")
    @RequestMapping("/deletePower")
    public Result<String> deletePower(@RequestBody power power) {
        Integer res = powerService.deletePower(power);
        if (res == 1) {
            return Result.success("更新权限成功！");
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }


}
