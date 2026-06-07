package com.ccb.backend.service.impl;

import com.ccb.backend.VO.UserContactVO;
import com.ccb.backend.VO.UserProfileVO;
import com.ccb.backend.context.BaseContext;
import com.ccb.backend.entity.User;
import com.ccb.backend.mapper.CommunityMapper;
import com.ccb.backend.mapper.ModelMapper;
import com.ccb.backend.mapper.UserMapper;
import com.ccb.backend.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private ModelMapper modelMapper;

    // 显示附近30km内的用户
    @Override
    public List<UserContactVO> show() {

        Long id = BaseContext.getCurrentId();
        User user = userMapper.getUserByID(id);
        return communityMapper.showNearbyUsers(user);

    }

    // 展示该用户主页的模型信息
    @Override
    public List<UserProfileVO> showProfile(Long id) {
        return modelMapper.getModelProfileByID(id);
    }
}
