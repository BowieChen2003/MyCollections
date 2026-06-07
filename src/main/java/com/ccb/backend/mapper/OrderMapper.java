package com.ccb.backend.mapper;

import com.ccb.backend.VO.OrderVO;
import com.ccb.backend.annotation.AutoFill;
import com.ccb.backend.entity.Order;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    @AutoFill(value = "INSERT")
    @Insert("insert into Orders (id, model_id, price, notes, buyer_id, created_at) values (#{id}, #{modelId}, #{price},  #{notes}, #{buyerId}, #{createTime})")
    void insert(Order order);

    @Select("select o.*, m.model_name, u.username from Orders o join models m on o.model_id = m.id join User u on o.buyer_id = u.id " +
            "where m.owner_id = #{ownerId}")
    List<OrderVO> getOrdersById(@Param("ownerId") Long ownerId);

    @Delete("delete from Orders where id = #{id}")
    void delete(@Param("id") Long id);

    @Select("select * from Orders where id = #{id}")
    Order getOrderById(Long id);

    @Select("select * from Orders where  created_at < #{orderTime}")
    List<Order> getByOrdertime(@Param("orderTime") LocalDateTime orderTime);

}
