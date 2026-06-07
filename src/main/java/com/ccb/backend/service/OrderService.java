package com.ccb.backend.service;

import com.ccb.backend.DTO.ModelOrderDTO;
import com.ccb.backend.VO.OrderVO;
import com.ccb.backend.entity.Order;

import java.util.List;

public interface OrderService {
    void submitOrder(ModelOrderDTO modelOrderDTO);

    List<OrderVO> getOrdersById();

    void confirm(Order order);

    void reject(Long id);
}
