package com.ccb.backend.mapper;

import com.ccb.backend.VO.ModelOnSaleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopMapper {

    @Select("select m.*, u.xianyuld, u.address from models m join user u on m.owner_id = u.id where m.status=0")
    List<ModelOnSaleVO> list();
}
