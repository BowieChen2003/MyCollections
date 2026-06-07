package com.ccb.backend.service;

import com.ccb.backend.DTO.ModelDTO;
import com.ccb.backend.DTO.ModelPageDTO;
import com.ccb.backend.DTO.ModelSetSellDTO;
import com.ccb.backend.VO.ModelVO;
import com.ccb.backend.result.PageResult;

import java.util.List;

public interface ModelService {

    void addModel(ModelDTO modelDTO);

    PageResult pageQuery(ModelPageDTO modelDTO);

    List<ModelVO> list();

    void update(ModelDTO modelDTO);

    ModelVO getModelById(Long id);

    void setSell(ModelSetSellDTO modelSetSellDTO);
}
