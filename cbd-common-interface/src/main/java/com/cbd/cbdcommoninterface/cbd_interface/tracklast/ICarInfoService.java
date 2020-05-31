package com.cbd.cbdcommoninterface.cbd_interface.tracklast;


import com.cbd.cbdcommoninterface.pojo.leipojo.car_info;
import com.cbd.cbdcommoninterface.response.leiVo.CarForTreeVo;
import com.cbd.cbdcommoninterface.response.leiVo.CompanyAndCarInfoResponse;
import com.cbd.cbdcommoninterface.response.leiVo.findCarVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/23 14:49
 * @Description:
 */
@Component
public interface ICarInfoService {
    Integer save(car_info saveCarInfo);

    List<CompanyAndCarInfoResponse> getCompanyAndCarInfo(String companyID);

    car_info findById(String strCarUUID);

    boolean update(car_info saveCarInfo);

    Map<String,Object> findUserInfoByDevID(String devID);


    /**搜索车辆*/
    List<CarForTreeVo> findLikelyCarOwnerBySearchKey(String companyID, String searchKey);

    void function();

}
