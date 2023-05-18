package com.ayush.shared.pojo;

import com.ayush.shared.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalApiResponse {
    private ResponseStatus responseStatus;
    private String message;
    private Object data;
}
