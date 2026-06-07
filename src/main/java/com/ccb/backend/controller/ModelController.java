package com.ccb.backend.controller;

import com.ccb.backend.DTO.ModelDTO;
import com.ccb.backend.DTO.ModelPageDTO;
import com.ccb.backend.DTO.ModelSetSellDTO;
import com.ccb.backend.VO.ModelVO;
import com.ccb.backend.result.PageResult;
import com.ccb.backend.result.Result;
import com.ccb.backend.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/model")
@Tag(name = "模型管理相关接口", description = "模型管理相关接口")
public class ModelController {

    @Autowired
    private ModelService modelService;


    // 模型列表回显
    @GetMapping("/list")
    @Operation(summary = "模型列表回显", description = "模型列表回显接口")
//    @Cacheable(value = "modelListCache", key = "T(com.ccb.backend.context.BaseContext).getCurrentId()")
    @Cacheable(cacheNames = "modelCache", key = "T(com.ccb.backend.context.BaseContext).getCurrentId() + ':list'")
    public Result<List<ModelVO>> list() {
        List<ModelVO> l = modelService.list();
        return Result.success(l);
    }

    // 添加模型
    @PostMapping("/addModel")
    @Operation(summary = "添加模型", description = "添加模型接口")
//    @CacheEvict(cacheNames = "modelListCache",key = "T(com.ccb.backend.context.BaseContext).getCurrentId()")
    @Caching(evict = {
            @CacheEvict(cacheNames = "modelCache", key = "T(com.ccb.backend.context.BaseContext).getCurrentId() + ':list'"),
            @CacheEvict(cacheNames = "modelCache", allEntries = true) // 分页情况复杂，建议直接清理当前 cacheNames 下的所有项
    })
    public Result addModel(ModelDTO modelDTO){
        modelService.addModel(modelDTO);
        return Result.success();
    }

    // 分页查询
    @GetMapping("/pageQuery")
    @Operation(summary = "模型分页查询", description = "模型分页查询接口")
    @Cacheable(cacheNames = "modelCache", key = "T(com.ccb.backend.context.BaseContext).getCurrentId() + ':page:' + #modelPageDTO.pageNum + ':' + #modelPageDTO.pageSize")
    public Result<PageResult> pageQuery(ModelPageDTO modelPageDTO){
        PageResult pageResult = modelService.pageQuery(modelPageDTO);
        return Result.success(pageResult);
    }

    // 根据id查询模型信息
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询模型信息", description = "根据id查询模型信息接口")
    public Result<ModelVO> getById(@PathVariable Long id){
        ModelVO modelVO = modelService.getModelById(id);
        return Result.success(modelVO);
    }

    // 修改模型信息
    @PutMapping("/update")
    @Operation(summary = "修改模型信息", description = "修改模型信息接口")
    @Caching(evict = {
            @CacheEvict(cacheNames = "modelCache", key = "T(com.ccb.backend.context.BaseContext).getCurrentId() + ':list'"),
            @CacheEvict(cacheNames = "modelCache", allEntries = true)
    })
    public Result update(@RequestBody ModelDTO modelDTO){
        modelService.update(modelDTO);
        return Result.success();
    }

    // 起售模型
    @PutMapping("/setSell")
    @Operation(summary = "起售模型", description = "出售模型接口")
    @Caching(evict = {
            @CacheEvict(cacheNames = "modelCache", key = "T(com.ccb.backend.context.BaseContext).getCurrentId() + ':list'"),
            @CacheEvict(cacheNames = "modelCache", allEntries = true)
    })
    public Result setSell(@RequestBody ModelSetSellDTO modelSetSellDTO){
        modelService.setSell(modelSetSellDTO);
        return Result.success();
    }

}
