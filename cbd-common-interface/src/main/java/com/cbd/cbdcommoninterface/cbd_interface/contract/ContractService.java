package com.cbd.cbdcommoninterface.cbd_interface.contract;

import com.cbd.cbdcommoninterface.request.AddContractRequest;
import com.cbd.cbdcommoninterface.request.DistributeContractRequest;
import com.cbd.cbdcommoninterface.request.PageContractConditionRequest;
import com.cbd.cbdcommoninterface.response.ContractInfoResponse;
import com.cbd.cbdcommoninterface.response.PageContractListResponse;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.response.UnpaidContractInfoResponse;

import java.util.List;


public interface ContractService {

    /**
     * 获取当前公司下的合同类别列表
     * @param companyID
     * @return
     */
    List<String> findContractTypeNameByCompanyID(String companyID);

    /**
     * 根据指定条件获取对应的合同列表(五条件)
     * @param contractConditionRequest
     * @return
     */
    PageResponse findContractListByCondition(PageContractConditionRequest contractConditionRequest);

    /**
     * 根据合同ID获取合同详细信息
     * @param contractID
     * @return
     */
    ContractInfoResponse findContractInfoByContractID(String contractID);

    /**
     * 获取当前公司下可使用的合同列表
     * @return
     */
    List<PageContractListResponse> findUsingContractListByCompanyID(String companyID);

    /**
     * 根据合同ID和公司名进行合同派发
     * @param contractRequest
     * @return
     */
    Boolean distributeContractByContractIDAndCompanyName(DistributeContractRequest contractRequest);

    /**
     * 根据合同ID获得不同时长续费金额(三种)
     * @param contractID
     * @return
     */
    List<Float> getRenewMoneyByContractID(String contractID);

    /**
     *  获取之前未支付的合同信息，甲方访问
     * @param companyID
     * @return
     */
    UnpaidContractInfoResponse getUnPaidContractInfoByCompanyID(String companyID);

    /**
     * 新建合同，车佰度平台管理员访问
     * @param addContractRequest
     * @return
     */
    String addContract(AddContractRequest addContractRequest);

    /**
     * 获取所有合同类型，车佰度平台管理员访问
     * @return
     */
    List<String> getAllContractType();

    /**
     * 添加合同，并完成设备批量调拨信息推送
     * @param contractID
     */
    void addOrder(String contractID);

    /**
     * 续费合同
     * @param contractID
     */
    void renewContract(String contractID);
}