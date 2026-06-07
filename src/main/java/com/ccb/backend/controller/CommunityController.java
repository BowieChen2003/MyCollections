package com.ccb.backend.controller;

import com.ccb.backend.VO.UserContactVO;
import com.ccb.backend.VO.UserProfileVO;
import com.ccb.backend.result.Result;
import com.ccb.backend.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/community")
@Tag(name = "附近的人模块", description = "社区相关接口")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    // 展示附近的人列表
    @GetMapping
    @Operation(summary = "展示附近的人列表", description = "展示附近的人列表接口")
    public Result<List<UserContactVO>> show(){
        return Result.success(communityService.show());
    }

    // 点击进入该用户主页
    @GetMapping("/profile/{id}")
    @Operation(summary = "点击进入该用户主页", description = "点击进入该用户主页接口")
    public Result<List<UserProfileVO>> showProfile(@PathVariable Long id){
        return Result.success(communityService.showProfile(id));
    }

}
