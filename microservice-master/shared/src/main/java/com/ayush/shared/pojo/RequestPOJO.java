package com.ayush.shared.pojo;

import com.ayush.shared.model.Fee;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPOJO {
    private int studentId;
    private String studentName;
    private String faculty;
    private String address;
    private Fee fee;
}
