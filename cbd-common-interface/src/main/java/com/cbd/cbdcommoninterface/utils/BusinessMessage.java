package com.cbd.cbdcommoninterface.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class BusinessMessage implements Serializable {
    private String businessType;
    private Object context;
}
