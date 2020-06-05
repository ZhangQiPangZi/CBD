package com.cbd.cbdcommoninterface.utils;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BusinessMessage {
    private String businessType;
    private Object context;
}
