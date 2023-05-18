package com.ayush.shared.pojo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String studentName;
    private List<FeePOJO> paidFee;
}
