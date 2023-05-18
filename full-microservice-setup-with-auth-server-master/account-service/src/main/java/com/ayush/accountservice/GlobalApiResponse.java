package com.ayush.accountservice;

import lombok.Data;

import java.io.Serializable;

@Data
public class GlobalApiResponse implements Serializable {
    private Object data;
    private String message;
}
