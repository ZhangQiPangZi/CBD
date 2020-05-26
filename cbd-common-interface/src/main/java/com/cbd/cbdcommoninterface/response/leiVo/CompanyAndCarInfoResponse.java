package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author shy_black
 * @date 2020/5/3 11:29
 * @Description:
 * 公司信息：id,companyName,companyID,parentID,lft,rgt,List<CarForTreeVo>
 */
@Getter
@Setter
@ToString
public class CompanyAndCarInfoResponse {

    private CompanyInfoVo companyInfoVo;

    private List<CarForTreeVo> carForTreeVo;


}
