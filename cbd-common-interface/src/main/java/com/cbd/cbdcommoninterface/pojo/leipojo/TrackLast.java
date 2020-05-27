package com.cbd.cbdcommoninterface.pojo.leipojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/4/23 18:48
 * @Description:
 * CREATE TABLE `Track_868120137463664` (
 *   `nID` int(10) NOT NULL AUTO_INCREMENT,
 *   `strTEID` varchar(23) NOT NULL,
 *   `nTime` int(10) NOT NULL DEFAULT '0',
 *   `dbLon` decimal(10,7) NOT NULL DEFAULT '0.0000000',
 *   `dbLat` decimal(10,7) NOT NULL DEFAULT '0.0000000',
 *   `nDirection` smallint(5) NOT NULL DEFAULT '0',
 *   `nSpeed` tinyint(3) unsigned NOT NULL DEFAULT '0',
 *   `nGSMSignal` tinyint(3) unsigned NOT NULL DEFAULT '0',
 *   `nGPSSignal` tinyint(3) unsigned NOT NULL DEFAULT '0',
 *   `nFuel` int(10) NOT NULL DEFAULT '0',
 *   `nMileage` int(10) NOT NULL DEFAULT '0',
 *   `nTemp` smallint(5) NOT NULL,
 *   `nCarState` int(10) NOT NULL DEFAULT '0',
 *   `nTEState` int(10) NOT NULL DEFAULT '0',
 *   `nAlarmState` int(10) NOT NULL DEFAULT '0',
 *   `strOther` varchar(1000) DEFAULT NULL,
 *   PRIMARY KEY (`nID`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=10513 DEFAULT CHARSET=latin1;
 */
@Getter
@Setter
@ToString
public class TrackLast implements Serializable {
    private Integer nID;

    private String devID;

    private Integer nTime;

    private String dbLon;

    private String dbLat;

    private Integer direction;

    private Short speed;

    private Integer mileage;//里程

}
