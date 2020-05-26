package com.black.lei.Utils.utilsForPlatForm;//package BackManage.common.utils.utilsForPlatForm;
//
//import com.mchange.v2.c3p0.ComboPooledDataSource;
//import org.springframework.core.io.support.PropertiesLoaderUtils;
//
//import java.beans.PropertyVetoException;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
///**
// * sqlServer连接池
// * @author Administrator
// *
// */
//public class SqlServerConnection {
//
//	private final static ComboPooledDataSource dsInterface = new ComboPooledDataSource();
//
//    static {
//        Properties prop = new Properties();
//        try {
//			prop = PropertiesLoaderUtils.loadAllProperties("sqlServier.properties");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//        String driverClass = prop.getProperty("driverClass");
//        String jdbcUrl = prop.getProperty("jdbcUrl");
//        String user = prop.getProperty("user");
//        String password = prop.getProperty("password");
//        String initialPoolSize = prop.getProperty("initialPoolSize");
//        String minPoolSize = prop.getProperty("minPoolSize");
//        String maxPoolSize = prop.getProperty("maxPoolSize");
//        String checkoutTimeout = prop.getProperty("checkoutTimeout");
//        String acquireRetryAttempts = prop.getProperty("acquireRetryAttempts");
//        String preferredTestQuery = prop.getProperty("preferredTestQuery");
//        String idleConnectionTestPeriod = prop.getProperty("idleConnectionTestPeriod");
//        String unreturnedConnectionTimeout = prop.getProperty("unreturnedConnectionTimeout");
//
//        try {
//			dsInterface.setDriverClass(driverClass);
//		} catch (PropertyVetoException e) {
//			e.printStackTrace();
//		}
//        dsInterface.setJdbcUrl(jdbcUrl);
//        dsInterface.setUser(user);
//        dsInterface.setPassword(password);
//        dsInterface.setInitialPoolSize(Integer.parseInt(initialPoolSize));
//        dsInterface.setMinPoolSize(Integer.parseInt(minPoolSize));
//        dsInterface.setMaxPoolSize(Integer.parseInt(maxPoolSize));
//        dsInterface.setCheckoutTimeout(Integer.parseInt(checkoutTimeout));
//        dsInterface.setAcquireRetryAttempts(Integer.parseInt(acquireRetryAttempts));
//        dsInterface.setPreferredTestQuery(preferredTestQuery);
//        dsInterface.setIdleConnectionTestPeriod(Integer.parseInt(idleConnectionTestPeriod));
//        dsInterface.setTestConnectionOnCheckout(true);
//        dsInterface.setUnreturnedConnectionTimeout(Integer.parseInt(unreturnedConnectionTimeout));
//    }
//
//    private static void releaseConnection(Connection conn) {
//        if(conn!=null) {
//            try {
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            conn = null;
//        }
//    }
//
//    private static void releasePreparedStatement(PreparedStatement pstmt) {
//        if(pstmt!=null){
//            try {
//                pstmt.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            pstmt = null;
//        }
//    }
//
//    private static void releaseResultSet(ResultSet rs) {
//        if(rs!=null) {
//            try {
//                rs.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            rs = null;
//        }
//    }
//
//	public static Map<String, Object> executeSql(String sql) {
//		Map<String,Object> map = new HashMap<>();
//        try {
//        	Connection conn = dsInterface.getConnection();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                map.put("strTEID", rs.getString("strTEID"));
//                map.put("nTime", rs.getInt("nTime"));
//                map.put("nLineLastTime", rs.getInt("nLineLastTime"));
//                map.put("dbLon", rs.getBigDecimal("dbLon"));
//                map.put("dbLat", rs.getBigDecimal("dbLat"));
//                map.put("nDirection",rs.getShort("nDirection"));
//                map.put("nSpeed", rs.getShort("nSpeed"));
//                map.put("nGSMSignal", rs.getByte("nGSMSignal"));
//                map.put("nGPSSignal", rs.getByte("nGPSSignal"));
//                map.put("nFuel", rs.getInt("nFuel"));
//                map.put("nMileage",rs.getInt("nMileage"));
//                map.put("nTemp", rs.getShort("nTemp"));
//                map.put("nCarState", rs.getInt("nCarState"));
//                map.put("nTEState", rs.getInt("nTEState"));
//                map.put("nAlarmState",rs.getInt("nAlarmState"));
//                map.put("strOther", rs.getString("strOther"));
//                map.put("nStopTime", 0);
//                CommonTrackLast.updateTEState(map);
//            } else {
//            	map = null;
//            }
//
//            releaseResultSet(rs);
//            releasePreparedStatement(pstmt);
//            releaseConnection(conn);
//
//           } catch (SQLException  e) {
//	            e.printStackTrace();
//	            return null;
//           }
//        return map;
//	}
//
//	public static int executeCnSql(String sql) {
//		int count = 0;
//        try {
//        	Connection conn = dsInterface.getConnection();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                count = rs.getInt("cn");
//            }
//            releaseResultSet(rs);
//            releasePreparedStatement(pstmt);
//            releaseConnection(conn);
//           } catch (SQLException  e) {
//	            e.printStackTrace();
//	            return 0;
//           }
//        return count;
//	}
//
//	public static List<Map<String, Object>> executeSqlFindList(String sql) {
//		List<Map<String,Object>> mapList = new ArrayList<>();
//        try {
//        	Connection conn = dsInterface.getConnection();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//            	Map<String,Object> map = new HashMap<>();
//                map.put("strTEID", rs.getString("strTEID"));
//                map.put("nTime", rs.getInt("nTime"));
//                map.put("dbLon", rs.getBigDecimal("dbLon"));
//                map.put("dbLat", rs.getBigDecimal("dbLat"));
//                map.put("nDirection",rs.getShort("nDirection"));
//                map.put("nSpeed", rs.getShort("nSpeed"));
//                map.put("nGSMSignal", rs.getByte("nGSMSignal"));
//                map.put("nGPSSignal", rs.getByte("nGPSSignal"));
//                map.put("nFuel", rs.getInt("nFuel"));
//                map.put("nMileage",rs.getInt("nMileage"));
//                map.put("nTemp", rs.getShort("nTemp"));
//                map.put("nCarState", rs.getInt("nCarState"));
//                map.put("nTEState", rs.getInt("nTEState"));
//                map.put("nAlarmState",rs.getInt("nAlarmState"));
//                map.put("strOther", rs.getString("strOther"));
//                mapList.add(map);
//            }
//            releaseResultSet(rs);
//            releasePreparedStatement(pstmt);
//            releaseConnection(conn);
//
//           } catch (SQLException  e) {
//	            e.printStackTrace();
//	            return new ArrayList<>();
//           }
//        return mapList;
//	}
//
//	public static Map<String, Object> getDeviceInfo(String sql) {
//		Map<String,Object> map = new HashMap<>();
//        try {
//        	Connection conn = dsInterface.getConnection();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                map.put("nInstallTime", rs.getDate("nInstallTime").getTime());
//                map.put("strInstallLoc", rs.getString("strInstallLoc"));
//                map.put("nServiceStartTime", rs.getDate("nServiceStartTime").getTime());
//                map.put("nServiceEndTime", rs.getDate("nServiceEndTime").getTime());
//                map.put("strCarrVIN", rs.getString("strCarrVIN"));
//                map.put("strName",rs.getString("strName"));
//                map.put("strPhone", rs.getString("strPhone"));
//                map.put("strModelName", rs.getString("strModelName"));
//                map.put("nTEType", rs.getInt("nTEType"));
//                map.put("nOfflineTime", rs.getInt("nOfflineTime"));
//            } else {
//            	map = null;
//            }
//            releaseResultSet(rs);
//            releasePreparedStatement(pstmt);
//            releaseConnection(conn);
//           } catch (SQLException  e) {
//	            e.printStackTrace();
//	            return null;
//           }
//        return map;
//	}
//}
