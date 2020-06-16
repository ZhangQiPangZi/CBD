package com.cbd.cbdcommoninterface.cbd_interface.MQ;

import com.cbd.cbdcommoninterface.utils.BusinessMessage;

public interface MQSender {
    void send(BusinessMessage message);
}
