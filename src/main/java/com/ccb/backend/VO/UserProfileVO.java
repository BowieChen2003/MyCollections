package com.ccb.backend.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 用户主页目前展示该用户的模型
public class UserProfileVO implements Serializable {

    private Long id;
    private String modelName;
    private String brand;
    private String manufacturer;
    private String image;

    private String scale;
    private String grade;
    private String description;

}
