package com.black.lei.dao;

import com.cbd.cbdcommoninterface.pojo.leipojo.company_info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/21 11:27
 * @Description:
 *   private Integer id;
 *     private String companyID;
 *     private String parentID;
 *     private Integer lft;
 *     private Integer rgt;
 *     private String companyName;
 *     private String companyPhone;
 *     private String companyAddress;
 *     private Integer companyType;
 *     private String companyManagerID;
 */
@Mapper
public interface company_infoDao {

    //获取公司信息
    @Select("select * from company_info " +
            "where companyID = #{0}")
    Map<String, String> getCompanyInfo(String companyID);

    //根据公司ID查询公司信息
    @Select("select * from company_info " +
            "where companyID = #{0}")
    company_info findById(String companyID);


    //查询当前公司上级公司的层级和公司名称
    //SELECT * FROM T_DIR WHERE N_LEFT<5 AND N_RIGHT>10
    @Select("select D.companyName" +
            "(select count(1) from company_info tmp where tmp.lft < D.lft and tmp.rgt > D.rgt) as level" +
            " from company_info D where lft<#{lft} and rgt<#{rgt} " +
            "order by lft")
    List<String> getParentCompanyByCompanyID(String lft,String rgt);


}
