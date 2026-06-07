package com.ccb.backend.service.impl;

import com.ccb.backend.VO.ModelOnSaleVO;
import com.ccb.backend.mapper.ShopMapper;
import com.ccb.backend.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public List<ModelOnSaleVO> list() {
        return shopMapper.list();
    }
}
