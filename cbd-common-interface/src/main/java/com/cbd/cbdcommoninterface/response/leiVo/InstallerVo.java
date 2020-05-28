package com.cbd.cbdcommoninterface.response.leiVo;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shy_black
 * @date 2020/5/25 19:54
 * @Description:
 */
@Getter
@Setter
@ToString
public class InstallerVo extends UserResponseVo implements Serializable {

    private Integer installer_id;

    private Integer level;

    private String longitude;

    private String latitude;

    public InstallerVo() {

    }

    public InstallerVo(Integer level,String longitude,String latitude) {
        this.level = level;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
