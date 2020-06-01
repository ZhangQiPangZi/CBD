package com.cbd.cbdcontract.dao;

import com.cbd.cbdcommoninterface.dto.ContractConditionDto;
import com.cbd.cbdcommoninterface.pojo.contract.ContractInfo;
import com.cbd.cbdcommoninterface.pojo.contract.ContractType;
import com.cbd.cbdcommoninterface.pojo.device.DevType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContractDao {

    /**
     * 获取当前公司下的合同类别列表
     * @param lft
     * @param rgt
     * @return
     */
    List<String> findContractTypeNameByCompanyID(@Param("lft") Integer lft, @Param("rgt") Integer rgt);

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

    void updateContractByDistribute(@Param("contractID") String contractID, @Param("companyID") String companyID, @Param("shopID") String shopID);

    void updateContractStatus(@Param("contractID") String contractID, @Param("contractStatus") Integer contractStatus);

    Integer getContractTypeID(@Param("contractTypeName") String contractTypeName);

    Integer getDevTypeID(@Param("devName") String devName);

    void addContractInfo(ContractInfo contractInfo);

    @Select("select contractTypeName from contractType")
    List<String> getAllContractType();
}
