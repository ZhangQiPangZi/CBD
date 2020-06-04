package com.cbd.cbdcommoninterface.response.leiVo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shy_black
 * @date 2020/6/3 16:05
 * @Description:
 */
@Data
public class StartToEndTrackVo {

    private String key ;
    private long startTime ;
    private long endTime ;

}
