package com.ccb.backend.service;

import com.ccb.backend.DTO.UserLoginDTO;
import com.ccb.backend.DTO.UserRegisterDTO;
import com.ccb.backend.entity.User;

public interface UserService {
    User login(UserLoginDTO userLoginDTO);

    void register(UserRegisterDTO userRegisterDTO);

}
