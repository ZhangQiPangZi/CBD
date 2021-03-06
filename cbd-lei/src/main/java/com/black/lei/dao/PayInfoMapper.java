package com.black.lei.dao;


import com.cbd.cbdcommoninterface.pojo.leipojo.PayInfo;
import org.springframework.stereotype.Component;

@Component
public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

	PayInfo selectByOrderNo(Long orderNo);
}