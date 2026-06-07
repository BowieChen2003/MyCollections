package com.ccb.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String xianyuId;

    private String address;

    private float longitude;

    private float latitude;

    // 临时字段，用于传给 MyBatis
    private String wkt;   // 如 "POINT(114.3055 30.5928)"

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
