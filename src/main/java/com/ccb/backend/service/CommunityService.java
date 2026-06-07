package com.ccb.backend.service;

import com.ccb.backend.VO.UserContactVO;
import com.ccb.backend.VO.UserProfileVO;

import java.util.List;

public interface CommunityService {

    List<UserContactVO> show();

    List<UserProfileVO> showProfile(Long id);
}
