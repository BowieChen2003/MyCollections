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
public class UserContactVO implements Serializable {
    private Long id;
    private String username;
    private String xianyuId;
    private String address;
}
