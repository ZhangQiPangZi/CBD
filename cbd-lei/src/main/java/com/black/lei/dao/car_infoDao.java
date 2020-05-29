package com.black.lei.dao;


import com.cbd.cbdcommoninterface.pojo.leipojo.car_info;
import com.cbd.cbdcommoninterface.response.leiVo.BaseOrderInfoVo;
import com.cbd.cbdcommoninterface.response.leiVo.CarForTreeVo;
import com.cbd.cbdcommoninterface.response.leiVo.CompanyInfoVo;
import com.cbd.cbdcommoninterface.response.leiVo.LftAndRgtVo;
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

    @Select("select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, nTime,c.carPlateNum " +
            "       from track t,car_info  c " +
            "       where t.devID = #{devID} " +
            "       ORDER BY nTime DESC limit 1 ")
    CarForTreeVo findTrackLastAndCarInfo(@Param("devID") String devID);


    @Select("select c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, MAX(t.nTime) AS nTime ,carPlateNum " +
            "            from car_info c  " +
            "            LEFT JOIN track t on c.devID = t.devID where c.companyID = #{companyID}" +
            "            GROUP BY c.devID,c.owerName,c.phoneNum ,t.dbLon,t.dbLat, nTime ")
    List<CarForTreeVo>  getUserForCarVo(String companyID);

    @Select("select lft,rgt from company_info where companyID=#{companyID}")
    LftAndRgtVo getLftAndRgt(String companyID);

    //id,companyName,companyID,parentID,lft,rgt,level
    @Select("select D.id as id," +
            "D.companyID as companyID," +
            "D.companyName as companyName ," +
            "D.parentID as parentID ," +
            "D.lft as lft ," +
            "D.rgt as rgt ," +
            "(select count(1) from company_info tmp where tmp.lft < D.lft and tmp.rgt > D.rgt) as level " +
            "from company_info D " +
            "where lft>=#{lft} and rgt<=#{rgt} " +
            "ORDER BY lft ")
    //'lft>'1' and rgt<'6' ORDER BY lft'
    List<CompanyInfoVo> getCompanyTreeList(@Param("lft") String lft, @Param("rgt") String rgt);


    // 根据车辆devID/车主姓名/电话/车牌号
    @Select("select c.devID " +
            "from car_info  c ,company_info d " +
            "where d.lft >= #{lft} and d.rgt<= #{rgt}  and c.companyID=d.companyID " +
            "and ( c.devID like CONCAT(CONCAT('%', #{searchKey}), '%') " +
            "or c.owerName like CONCAT(CONCAT('%', #{searchKey}), '%') " +
            "or c.phoneNum like CONCAT(CONCAT('%', #{searchKey}), '%')n" +
            "or c.carPlateNum like CONCAT(CONCAT('%', #{searchKey}), '%') ) ")

    List<String> findLikelyDevID(@Param("lft") String lft,
                                 @Param("rgt") String rgt,
                                 @Param("searchKey") String searchKey);


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

    //根据车主id查找车辆
    @Select("select * from car_info " +
            "where owerID = #{0}")
    car_info findCarbyOwnerID(String owerID);

    @Select("select owerID from car_info " +
            "where devID = #{devID}")
    String findOwerIDByDevID(@Param("devID") String devID);


    //点击设备时获取车辆信息
    //TODO
    @Select("select a.userName,a.phoneNum,b.modelName,b.carPlateNum,b.devID,c.dbLon,c.dbLat,c.Max(nTime)" +
            "from user as a,car_info as b ,track as c" +
            "where c.devID=#{devID} AND c.company = #{companyID}")
    Map<String,Object> getCarAndGPSInfo(String devID, String companyID);

    //根据设备号获取车辆信息
    //TODO
    @Select("select * from car_info where devID=#{0}")
    Map<String,Object> getCarInfoByTEID(String devID);

    //根据车主名字模糊查询车辆以及车主信息
    //TODO
    @Select("select")
    List<Map<String, Object>> findCarInfo(String strCompanyID, String strName);

    //根据手机号查询车辆设备ID
    //TODO  1.在user中找到userID，即ID，2.在car_user中根据userID找出carUUID，3.在car_info中找到devID
    @Select("select a.devID from car_info as a ,car_user as b where ")
    List<Map<String,Object>> findListBystrPhone(String phoneNum);

    //获取公司所有的车辆信息
    @Select("select a.userName,a.phoneNum,b.modelName,b.carPlateNum,b.devID,c.dbLon,c.dbLat,c.Max(nTime)" +
            "from user as a,car_info as b ,track as c" +
            "where b.devID = c.devID AND b.company = #{0}")
    List<Map<String,Object>> getCarAndGPSInfoList(String strCompanyID);

    //根据公司id获取车辆信息-车主，电话，车型，车牌，设备信息
    //select a.*,b.strTEID from Tlb_CarInfo as a,Tlb_CarAndDeviceRefer as b
    //  	where a.strCarUUID = b.strCarUUID AND b.strCompanyID = #{0}
    @Select("select a.userName,a.phoneNum,b.modelName,b.carPlateNum,b.devID from user as a,car_info as b " +
            "where b.companyID = #{0}")
    List<Map<String, Object>> getCarByCompanyID(String strCompanyID);


    //根据公司id获取设备ID列表
    //查找companyID下的车辆的devID
    @Select("select devID from car_device where companyID = #{0}")
    List<Map<String, Object>> getstrTEIDListByCompanyID(String strCompanyID);

    @Select("select carPlateNum from car_info where owerID=#{owerID}")
    String getCarPlateNumByOwerID(String owerID);
}
