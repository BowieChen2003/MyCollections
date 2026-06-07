package com.ccb.backend.mapper;

import com.ccb.backend.VO.UserContactVO;
import com.ccb.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {

    List<UserContactVO> showNearbyUsers(User user);
}
