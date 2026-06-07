package com.ccb.backend.controller;

import com.ccb.backend.DTO.UserLoginDTO;
import com.ccb.backend.DTO.UserRegisterDTO;
import com.ccb.backend.VO.UserLoginVO;
import com.ccb.backend.entity.User;
import com.ccb.backend.result.Result;
import com.ccb.backend.service.UserService;
import com.ccb.backend.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Slf4j
@Tag(name = "用户管理相关接口", description = "用户管理相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口，返回JWT令牌")
    public Result<UserLoginVO> Userlogin(@RequestBody UserLoginDTO userLoginDTO) {

        // 1、调用登录逻辑
        log.info("用户登录，用户名：{}", userLoginDTO.getUsername());

        User user = userService.login(userLoginDTO);
        // 2、生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token = JwtUtil.createJWT(   // 直接使用了一个工具类
                "ccbSecretKey123456789012345678901234567890",
                7200000,
                claims);

        // 3、封装返回结果
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .token(token).build();

        return Result.success(userLoginVO);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口，需要提供用户名、密码和闲鱼ID")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {

        log.info("用户注册，用户名：{}", userRegisterDTO.getUsername());

        userService.register(userRegisterDTO);

        return Result.success();
    }
}
