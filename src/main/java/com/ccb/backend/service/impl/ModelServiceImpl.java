package com.ccb.backend.service.impl;

import com.ccb.backend.DTO.ModelDTO;
import com.ccb.backend.DTO.ModelPageDTO;
import com.ccb.backend.DTO.ModelSetSellDTO;
import com.ccb.backend.VO.ModelVO;
import com.ccb.backend.context.BaseContext;
import com.ccb.backend.entity.Model;
import com.ccb.backend.mapper.ModelMapper;
import com.ccb.backend.result.PageResult;
import com.ccb.backend.service.ModelService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addModel(ModelDTO modelDTO) {

        Model model = new Model();
        BeanUtils.copyProperties(modelDTO, model);

        modelMapper.insert(model);

    }

    @Override
    public PageResult pageQuery(ModelPageDTO modelPageDTO) {

        modelPageDTO.setOwnerId(BaseContext.getCurrentId());
        log.info("当前用户id为：{}", modelPageDTO.getOwnerId());

        PageHelper.startPage(modelPageDTO.getPageNum(), modelPageDTO.getPageSize());
        Page<ModelVO> page = modelMapper.pageQuery(modelPageDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<ModelVO> list() {
        Long id = BaseContext.getCurrentId();
        return modelMapper.list(id);
    }

    @Override
    public void update(ModelDTO modelDTO) {
        Model model = new Model();
        BeanUtils.copyProperties(modelDTO, model);

        modelMapper.update(model);

    }

    @Override
    public ModelVO getModelById(Long id) {
        Model model = modelMapper.getModelById(id);
        ModelVO modelVO = new ModelVO();
        BeanUtils.copyProperties(model, modelVO);
        return modelVO;
    }

    // 起售模型，将
    @Override
    public void setSell(ModelSetSellDTO modelSetSellDTO) {
        Model model = new Model();
        BeanUtils.copyProperties(modelSetSellDTO, model);
        modelMapper.update(model);
    }
}
