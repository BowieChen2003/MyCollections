package com.ccb.backend.service.impl;

import com.ccb.backend.DTO.ModelOrderDTO;
import com.ccb.backend.VO.OrderVO;
import com.ccb.backend.context.BaseContext;
import com.ccb.backend.entity.Model;
import com.ccb.backend.entity.Order;
import com.ccb.backend.entity.Transaction;
import com.ccb.backend.mapper.ModelMapper;
import com.ccb.backend.mapper.OrderMapper;
import com.ccb.backend.mapper.TransactionMapper;
import com.ccb.backend.mapper.UserMapper;
import com.ccb.backend.service.OrderService;
import com.ccb.backend.websocket.WebSocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private ObjectMapper objectMapper; // 注入 Jackson 的 ObjectMapper


    private static final String ORDER_REPEAT_KEY = "order:repeat:";

    // 提交订单
    @Override
    @Transactional
    public void submitOrder(ModelOrderDTO modelOrderDTO) {

        // 防止重复下单
        Long currentId = BaseContext.getCurrentId();
        String key = ORDER_REPEAT_KEY + currentId + ":" + modelOrderDTO.getModelId();
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "1", 900, TimeUnit.SECONDS);

        if (Boolean.FALSE.equals(success)) {
            throw new RuntimeException("请勿重复提交订单！");
        }

        // 锁定订单
        String key1 = "product:reserved:" + modelOrderDTO.getModelId();
        String value = BaseContext.getCurrentId().toString();
        Boolean success1 = redisTemplate.opsForValue()
                .setIfAbsent(key1, value, 900, TimeUnit.SECONDS);

        if(Boolean.FALSE.equals(success1)){
            throw new RuntimeException("商品已被他人下单，请重新确认");
        }

        // 订单信息存入订单数据库
        Order order = new Order();
        BeanUtils.copyProperties(modelOrderDTO,order);
        order.setBuyerId(currentId);
        order.setPrice(modelOrderDTO.getSellPrice());
        orderMapper.insert(order);

        // 向卖家端发送订单通知
        String buyerName = userMapper.getUsername(BaseContext.getCurrentId());
        String modelName = modelMapper.getModelById(modelOrderDTO.getModelId()).getModelName();
        Map<String, Object> map = new HashMap<>();
        map.put("buyerName", buyerName);
        map.put("modelName",modelName);

        try {
            String json = objectMapper.writeValueAsString(map);
            webSocketServer.sendMessage(json, modelMapper.getModelById(modelOrderDTO.getModelId()).getOwnerId());
        } catch (JsonProcessingException e) {
            log.error("JSON 序列化失败，Map内容: {}", map, e); // 打印详细错误
        }

    }

    // 卖家查看订单
    @Override
    public List<OrderVO> getOrdersById() {
        Long ownerId = BaseContext.getCurrentId();
        return orderMapper.getOrdersById(ownerId);
    }

    @Override
    @Transactional
    public void confirm(Order order) {

        // 通过订单id查找模型数据
        Model model = modelMapper.getModelById(order.getModelId());

        // 修改模型状态为已售出
        model.setStatus(1);

        // 更新模型状态
        modelMapper.update(model);

        // 删除订单数据
        orderMapper.delete(order.getId());

        // 保存交易数据
        Transaction transaction = Transaction.builder()
                .modelId(order.getModelId())
                .price(order.getPrice())
                .buyerId(order.getBuyerId())
                .notes(order.getNotes())
                .buyer(userMapper.getUsername(order.getBuyerId()))
                .build();

        transactionMapper.save(transaction);

        // 释放商品锁定
        String key = "product:reserved:" + order.getModelId();
        redisTemplate.delete(key);
    }

    @Override
    public void reject(Long id) {

        Order order = orderMapper.getOrderById(id);

        orderMapper.delete(id);

        String key = "product:reserved:" + order.getModelId();
        redisTemplate.delete(key);
    }
}
