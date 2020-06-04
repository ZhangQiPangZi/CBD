package com.black.lei.dao;


import com.alipay.api.domain.Car;
import com.cbd.cbdcommoninterface.pojo.leipojo.car_info;
import com.cbd.cbdcommoninterface.response.leiVo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author shy_black
 * @date 2020/4/21 22:21
 * @Description:
 *     private Integer id;
 *     private String UUID;
 *     private String devID;
 *     private String owerID;
 *     private  String owerName;
 *     private String companyID;//is company
 *     private  String companyName;
 *     private String carPlateNum;
 *     private  String carModelID;//车辆具体品牌型号
 *     private String modelName;
 *     private Integer buyTime;
 *     private Integer serviceStartTime;
 *     private Integer serviceTime;//服务期限
 *     private String carColor;
 *     private  String phoneNum;
 *     private String carPrice;
 *     private Integer status;
 */
@Mapper
public interface car_infoDao {

    //新增车辆信息
    @Insert("insert into car_info (UUID,devID,owerID,owerName,companyID,companyName,carPlateNum,carModelID,modelName,buyTime,serviceStartTime,serviceTime,carColor,phoneNum,carPrice,status " +
            "values (#{UUID},#{devID},#{owerID},#{owerName},#{companyID},#{companyName},#{carPlateNum},#{carModelID},#{modelName},#{buyTime},#{serviceStartTime},#{serviceTime},#{carColor},#{phoneNum},#{carPrice},#{status} ))")
    Integer insert(car_info saveCarInfo);


    @Select("select count(orderID) from car_info where orderID=#{orderID}")
    Integer isOrderIDRepeated(@Param("orderID") String orderID);


    @Insert("insert into car_info (orderID,owerName,companyID,companyName,carPlateNum,phoneNum,status) " +
            "values (#{orderId},#{carOwnerName},#{companyID},#{companyName},#{licenseNumber},#{phoneNumber},#{status})" )
    Integer addCarInfo(BaseOrderInfoVo baseOrderInfoVo);

    //查询当前公司下的车辆的devID

    @Select("select c.devID " +
            "from car_info c  " +
            "LEFT JOIN track t on c.devID = t.devID where c.companyID = #{companyID} " +
            "GROUP BY c.devID ")
    List<String> getDevIDListByCompanyID(@Param("companyID") String companyID);

    @Select("select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, nTime,c.carPlateNum , t.speed " +
            "       from track t,car_info  c " +
            "       where t.devID = #{devID} " +
            "       ORDER BY nTime DESC limit 1 ")
    CarForTreeVo findTrackLastAndCarInfo(@Param("devID") String devID);


    @Select("select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, MAX(t.nTime) AS nTime ,carPlateNum ,t.speed " +
            "            from car_info c  " +
            "            LEFT JOIN track t on c.devID = t.devID where c.companyID = #{companyID}" +
            "            GROUP BY c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, nTime ")
    List<CarForTreeVo>  getUserForCarVo(String companyID);

    @Select("select lft,rgt from companyInfo where companyID=#{companyID}")
    LftAndRgtVo getLftAndRgt(String companyID);

    //id,companyName,companyID,parentID,lft,rgt,level
    @Select("select D.id as id," +
            "D.companyID as companyID," +
            "D.companyName as companyName ," +
            "D.parentID as parentID ," +
            "D.lft as lft ," +
            "D.rgt as rgt ," +
            "(select count(1) from companyInfo tmp where tmp.lft < D.lft and tmp.rgt > D.rgt) as level " +
            "from companyInfo D " +
            "where lft>=#{lft} and rgt<=#{rgt} " +
            "ORDER BY lft ")
    //'lft>'1' and rgt<'6' ORDER BY lft'
    List<CompanyInfoVo> getCompanyTreeList(@Param("lft") String lft, @Param("rgt") String rgt);




//    @Select("select c.")
//    List<String> findLikelyDevID(@Param("lft") String lft,
//                                 @Param("rgt") String rgt,
//                                 @Param("searchKey") String searchKey);


//     根据车辆devID/车主姓名/电话/车牌号
    @Select("select c.devID " +
            "from car_info  c ,companyInfo d " +
            "where d.lft >= #{lft} and d.rgt<= #{rgt}  and c.companyID=d.companyID " +
            "and ( c.devID like CONCAT(CONCAT('%', #{searchKey}), '%') " +
            "or c.owerName like CONCAT(CONCAT('%', #{searchKey}), '%') " +
            "or c.phoneNum like CONCAT(CONCAT('%', #{searchKey}), '%') " +
            "or c.carPlateNum like CONCAT(CONCAT('%', #{searchKey}), '%') ) ")

    List<String> findLikelyDevID(@Param("lft") String lft,
                                 @Param("rgt") String rgt,
                                 @Param("searchKey") String searchKey);

    /**
     * @Update("update car_info " +
     *             "set " +
     *             "UUID = #{UUID}," +
     *             "devID = #{devID}," +
     *             "owerID = #{owerID}," +
     *             "owerName = #{owerName}," +
     *             "companyID = #{companyID}," +
     * @param
     * @return
     */


    @Update("update car_info " +
            "set " +
            "devID = #{devID}," +
            "status = 1  " +
            "where orderID = #{orderID}")
    Integer updateDevIDAndStatusByOrderID(OrderScheduledVo orderScheduledVo);

    //根据UUID查找车辆全部信息
    @Select("select * from car_info " +
            "where UUID = #{0}")
    car_info findById(String UUID);

    //更新车辆信息
    @Update("update car_info " +
            "set " +
            "UUID = #{UUID}," +
            "devID = #{devID}," +
            "owerID = #{owerID}," +
            "owerName = #{owerName}," +
            "companyID = #{companyID}," +
            "companyName = #{companyName}," +
            "carPlateNum = #{carPlateNum}," +
            "carModelID = #{carModelID}," +
            "modelName = #{modelName}," +
            "buyTime = #{buyTime}," +
            "serviceStartTime = #{serviceStartTime}," +
            "serviceTime = #{serviceTime}," +
            "carColor = {carColor}," +
            "phoneNum = {phoneNum}," +
            "carPrice = #{carPrice}," +
            "status = #{status} " +
            "where UUID = #{UUID}")
    void update(car_info saveCarInfo);

    //select b.strOwnerID as strOwnerID,b.strCarUUID as strCarUUID, b.strCarPlateNum as strCarPlateNum ,
    //   b.strName as strName,b.strPhone as strPhone from Tlb_OrderInfo as a,Tlb_CarInfo as b
    //   where (a.strCarUUID = b.strCarUUID ) and b.strCarPlateNum like CONCAT(CONCAT('%', #{0}), '%');
    //
    //    @Select("select b.owerID as owerID,b.carUUID as carUUID,b.carPlateNum as carPlateNum ," +
    //            "b.userName as username,b.phoneNum as phoneNum from ")
    List<car_info> findCarList(String strCarNumber);

    //根据设备号查找车辆
    List<Map<String, String>> findCarListBystrTEID(String strTEID);


    @Select("select count(devID) from car_info where devID = #{devID} ")
    Integer hasDevID(@Param("devID") String devID);


    @Select("select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat,t. nTime,c.carPlateNum , t.speed ,c.companyID,companyInfo.companyName " +
            "                               from track t " +
            "                               LEFT JOIN car_info  c ON c.devID = t.devID  " +
            "                               LEFT JOIN companyInfo ON c.companyID = companyInfo.companyID " +
            "                               where t.devID = #{devID} " +
            "                               ORDER BY nTime DESC limit 1 ")
    CarForTreeVo findCarListByDevID(@Param("devID") String devID);

    @Select("select count(orderID) from car_info where orderID=#{orderID}")
    Integer hasOrderID(@Param("orderID") String orderID);

}
