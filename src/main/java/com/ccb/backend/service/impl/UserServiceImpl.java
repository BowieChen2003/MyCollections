package com.ccb.backend.service.impl;

import com.ccb.backend.DTO.UserLoginDTO;
import com.ccb.backend.DTO.UserRegisterDTO;
import com.ccb.backend.entity.User;
import com.ccb.backend.mapper.UserMapper;
import com.ccb.backend.service.UserService;
import com.ccb.backend.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getUserByUsername(username);
        if(user == null){
            throw new RuntimeException("用户用户不存在");
        }
        if(!user.getPassword().equals(password)){
            throw new RuntimeException("密码错误");
        }
        return user;
    }

    // 用户注册接口
    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();

        User existUser = userMapper.getUserByUsername(username);
        if(existUser != null){
            throw new RuntimeException("用户名已存在");
        }

        // 调用百度地图api获取用户地址所在的经纬度
        float[] latLng = new MapUtil().getLatLng(userRegisterDTO.getAddress());


        User user = User.builder()
                .username(userRegisterDTO.getUsername())
                .password(userRegisterDTO.getPassword())
                .xianyuId(userRegisterDTO.getXianyuId())
                .address(userRegisterDTO.getAddress())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .longitude(latLng[1])
                .latitude(latLng[0])
                .wkt(String.format("POINT(%f %f)", latLng[1], latLng[0]))
                .build();

        userMapper.insert(user);
    }
}
