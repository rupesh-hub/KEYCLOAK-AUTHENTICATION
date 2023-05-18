package com.ayush.userservice.pojos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
    private int httpCode;
    private String message;
}
