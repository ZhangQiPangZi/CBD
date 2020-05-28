package com.cbd.cbdcommoninterface.utils;

import com.cbd.cbdcommoninterface.response.leiVo.InstallerVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author shy_black
 * @date 2020/5/27 18:44
 * @Description:        随机返回一个InstallerVo
 */
@Getter
@Setter
@ToString
public class RandomInstallerUtil {


    private InstallerVo installerVo;


    public InstallerVo RandomInstallerInfo() {

        Random random = new Random();

        int tmp = random.nextInt(4);

        List<InstallerVo> installerVoList = new ArrayList<>();

        installerVoList.add(new InstallerVo(4, "108.93", "34.23"));
        installerVoList.add(new InstallerVo(4, "108.93", "34.29"));
        installerVoList.add(new InstallerVo(3, "108.95", "34.27"));
        installerVoList.add(new InstallerVo(2, "108.93", "34.27"));
        installerVoList.add(new InstallerVo(1, "108.93", "34.27"));


        return installerVoList.get(tmp);

    }


}
