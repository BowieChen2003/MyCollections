package com.ccb.backend.controller;


import com.ccb.backend.DTO.ModelOrderDTO;
import com.ccb.backend.VO.ModelOnSaleVO;
import com.ccb.backend.VO.UserContactVO;
import com.ccb.backend.result.Result;
import com.ccb.backend.service.OrderService;
import com.ccb.backend.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/shop")
@RequestMapping("/user/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    @Operation(summary = "获取在售模型列表", description = "获取在售模型列表接口")
    public Result<List<ModelOnSaleVO>> list(){
        List<ModelOnSaleVO> l = shopService.list();
        return Result.success(l);
    }

    // 提交交易：存入订单信息，等待卖家确认
    @PostMapping("/submit")
    @Operation(summary = "提交交易", description = "存入订单信息")
    public Result submitOrder(@RequestBody ModelOrderDTO modelOrderDTO){
        orderService.submitOrder(modelOrderDTO);
        return Result.success();
    }

    // 联系卖家接口：点击联系卖家，返回卖家名称和闲鱼ID
//    @GetMapping("/contact/{id}")
//    @Operation(summary = "联系卖家", description = "联系卖家接口")
//    public Result<UserContactVO> contact(@PathVariable Long id){
//        return Result.success();
//    }




}
