package com.cbd.cbdcommoninterface.utils;

import com.cbd.cbdcommoninterface.pojo.leipojo.TrackLast;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author shy_black
 * @date 2020/5/29 22:20
 * @Description: 东经107.40度～109.49度---------dbLon
 * 和北纬33.42度～34.45度之间-----dbLat
 */
public class RandomTrackLastUtil {

    private double MinLon = 107.40;
    private double MaxLon = 109.49;
    private double MinLat = 33.42;
    private double MaxLat = 34.45;


//    private Integer direction;
////
////    private Short speed;
////
////    private Integer mileage;//里程

    public TrackLast createTrackLast() {
        TrackLast trackLast = new TrackLast();

        trackLast.setNID(-1);
        trackLast.setNTime(this.curTime());
        trackLast.setMileage(this.getMileage());
        trackLast.setSpeed(this.getSpeed());
        trackLast.setDirection(this.getDirection());
        trackLast.setDbLon(this.randomLon());
        trackLast.setDbLat(this.randomLat());
        
        return trackLast;
    }

    public Integer getMileage() {
        Random random = new Random();
        return random.nextInt(100);
    }

    public Short getSpeed() {
        return 0;
    }

    public Integer getDirection() {
        Random random = new Random();
        return random.nextInt(10);
    }

    //当前时间
    public Integer curTime() {

        String tmp = String.valueOf(System.currentTimeMillis());
        Integer.valueOf(tmp);

        return Integer.valueOf(tmp);
    }

    //随机经度
    public String randomLon() {
        return randomLonLat(this.MinLon,this.MaxLon,this.MinLat,this.MaxLat,"Lon") ;
    }

    //随机纬度
    public String randomLat() {
        return randomLonLat(this.MinLon,this.MaxLon,this.MinLat,this.MaxLat,"Lat") ;
    }


    public String randomLonLat(double MinLon, double MaxLon, double MinLat, double MaxLat, String type) {
        Random random = new Random();
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        if (type.equals("Lon")) {
            return lon;
        } else {
            return lat;
        }


    }

}
