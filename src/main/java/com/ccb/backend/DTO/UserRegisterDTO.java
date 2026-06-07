package com.ccb.backend.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {
    private String username;

    private String password;

    private String xianyuId;

    // 地址可选
    private String address;
}
