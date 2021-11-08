package com.ssafy.backend.dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Password {
    private String email;
    private String password;
    private int userId;
    private String originPW;
    private String newPW;
}
