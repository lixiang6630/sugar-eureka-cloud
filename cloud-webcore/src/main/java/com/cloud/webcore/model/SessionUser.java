package com.cloud.webcore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionUser {

    private Long userId;

    private String phone;

    public SessionUser() {

    }
}
