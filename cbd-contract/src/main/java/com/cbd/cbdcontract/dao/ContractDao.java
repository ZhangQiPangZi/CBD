package com.cbd.cbdcontract.dao;

import com.cbd.cbdcommoninterface.dto.ContractConditionDto;
import com.cbd.cbdcommoninterface.pojo.contract.ContractInfo;
import com.cbd.cbdcommoninterface.pojo.contract.ContractType;
import com.cbd.cbdcommoninterface.pojo.device.DevType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContractDao {
    /**
     *  根据指定条件获取对应的合同ID列表
     * @param contractConditionDto
     * @return
     */
    List<String> findContractListByCondition(ContractConditionDto contractConditionDto);

    /**
     *  根据合同ID获取合同详细信息
     * @param contractID
     * @return
     */
    ContractInfo findContractInfoByContractID(@Param("contractID") String contractID);

    /**
     * 根据合同ID获取合同类型名
     * @param contractTypeID
     * @return
     */
    String getContractTypeName(@Param("contractTypeID") Integer contractTypeID);

    DevType getDevType(@Param("devTypeID") Integer devTypeID);
}
