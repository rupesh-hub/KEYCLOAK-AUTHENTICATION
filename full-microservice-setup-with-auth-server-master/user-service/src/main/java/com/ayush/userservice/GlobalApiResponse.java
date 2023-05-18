package com.ayush.userservice;

import lombok.Data;

import java.io.Serializable;

@Data
public class GlobalApiResponse implements Serializable {
    private Object data;
    private String message;
}
