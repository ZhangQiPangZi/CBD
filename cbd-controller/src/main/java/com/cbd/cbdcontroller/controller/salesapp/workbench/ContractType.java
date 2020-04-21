package com.cbd.cbdcontroller.controller.salesapp.workbench;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Monster
 * @date: 2020/4/21 15:07
 * @Description
 */
@RestController
@Slf4j
@RequestMapping(value = "/contractType")
@Api(value = "选择合同类别")
@CrossOrigin
public class ContractType {
    //TODO 调用张祺的接口 返回合同类型列表


    //TODO 查看合同的简略信息
}
