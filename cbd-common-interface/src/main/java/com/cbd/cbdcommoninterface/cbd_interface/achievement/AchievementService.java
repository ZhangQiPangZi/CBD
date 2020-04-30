package com.cbd.cbdcommoninterface.cbd_interface.achievement;

public interface AchievementService {

    /**
     * 传入销售员id和所完成的这单使用的合同ID来增加销售额
     * @param salersID
     * @param contractID
     * @return
     */
    Boolean addAchievement(String salersID, String contractID);
}
