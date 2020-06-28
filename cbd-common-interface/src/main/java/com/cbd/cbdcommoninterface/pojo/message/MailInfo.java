package com.cbd.cbdcommoninterface.pojo.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailInfo implements Serializable {
    /**
     * 接收人邮箱
     */
    private String receiverName;
    /**
     * 邮件标题
     */
    private String title;
    /**
     * 邮件内容
     */
    private String content;
}
