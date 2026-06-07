package com.ccb.backend.mapper;

import com.ccb.backend.DTO.ModelPageDTO;
import com.ccb.backend.VO.ModelVO;
import com.ccb.backend.VO.UserProfileVO;
import com.ccb.backend.annotation.AutoFill;
import com.ccb.backend.entity.Model;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModelMapper {

    @AutoFill(value = "INSERT")
    void insert(Model model);

    Page<ModelVO> pageQuery(ModelPageDTO modelPageDTO);


    List<ModelVO> list(Long id);

    void update(Model model);

    @Select("select * from models where id = #{id}")
    Model getModelById(Long id);

    @Select("select id, model_name, brand, manufacturer, image, scale, grade, description from models where owner_id = #{id}")
    List<UserProfileVO> getModelProfileByID(@Param("id") Long id);
}
