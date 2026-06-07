package com.ccb.backend.controller;

import com.ccb.backend.DTO.ModelOrderDTO;
import com.ccb.backend.VO.OrderVO;
import com.ccb.backend.entity.Order;
import com.ccb.backend.result.Result;
import com.ccb.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/order")
@Tag(name = "订单管理相关接口", description = "订单管理相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 卖家查看订单
    @GetMapping("/list")
    @Operation(summary = "卖家查看订单", description = "卖家查看订单接口")
//    @Cacheable(cacheNames = "orderCache", key = "T(com.ccb.backend.context.BaseContext).getCurrentId()")
    public Result<List<OrderVO>> list(){
        List<OrderVO> orders = orderService.getOrdersById();
        return Result.success(orders);
    }

    // 卖家确认order
    // 传入model_id
    @PutMapping("/confirm")
    @Operation(summary = "确认order", description = "确认order接口")
//    @CacheEvict(cacheNames = "orderCache")
    public Result confirm(@RequestBody Order order){
        orderService.confirm(order);
        return Result.success();
    }

    // 卖家拒单
    @DeleteMapping("/reject/{id}")
    @Operation(summary = "拒单", description = "拒单接口")
    public Result reject(@PathVariable Long id){
        orderService.reject(id);
        return Result.success();
    }

}
