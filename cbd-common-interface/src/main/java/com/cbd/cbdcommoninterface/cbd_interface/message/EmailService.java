package com.cbd.cbdcommoninterface.cbd_interface.message;

import java.io.File;

public interface EmailService {
    /**
     * 发送文本/简单的邮件
     * @param receiverName 接收人
     * @param title 标题
     * @param content 内容
     */
    void sendStringEmail(String receiverName, String title, String content);

    /**
     * 发送大文件/附件的邮件
     * @param receiverName
     * @param title
     * @param content
     * @param file 文件
     */
    void sendBigEmail(String receiverName, String title, String content, File file);


}
