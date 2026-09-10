package com.ccb.backend.task;

import com.ccb.backend.entity.Order;
import com.ccb.backend.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;
    @Value("${ccb.redis.enabled:true}")
    private boolean redisEnabled;

    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteExpiredOrders(){
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        List<Order> orderList = orderMapper.getByOrdertime(now);

        if(orderList != null && orderList.size() > 0){
            orderList.forEach(order -> {
                if (redisEnabled && redisTemplate != null) {
                    String key = "product:reserved:" + order.getModelId();
                    redisTemplate.delete(key);
                }
                orderMapper.delete(order.getId());
            });
        }
    }
}
