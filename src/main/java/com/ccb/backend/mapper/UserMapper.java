package com.ccb.backend.mapper;

import com.ccb.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);

    @Insert("insert into user (username, password, xianyu_id, create_time, update_time, address, latitude, longitude, location) " +
            "values (#{username}, #{password}, #{xianyuId}, #{createTime}, #{updateTime}, #{address}, #{latitude}, #{longitude}, "
            + "ST_GeomFromText(CONCAT('POINT(', #{latitude}, ' ', #{longitude},')'), 4326))")
    void insert(User user);

    @Select("select username from User where id = #{buyerId}")
    String getUsername(@Param("buyerId") Long buyerId);

    @Select("select * from user where id = #{id}")
    User getUserByID(Long id);
}
