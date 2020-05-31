package com.cbd.cbdcontroller.controller.salesapp.workbench;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Monster
 * @date: 2020/4/21 15:06
 * @Description
 */
@RestController
@Slf4j
@RequestMapping(value = "/generate")
@Api(value = "生成详细合同")
@CrossOrigin
public class GenerateContractController {
    //TODO 此处生成详细的合同条例 一段合同的条款 返回一段str
}
