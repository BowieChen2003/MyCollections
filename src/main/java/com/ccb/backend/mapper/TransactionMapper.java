package com.ccb.backend.mapper;

import com.ccb.backend.annotation.AutoFill;
import com.ccb.backend.entity.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {

    @AutoFill(value = "INSERT")
    @Insert("insert into Transactions(model_id, price, buyer, buyer_id, notes, created_at)" +
            "values (#{modelId}, #{price}, #{buyer}, #{buyerId}, #{notes}, #{createdTime})")
    void save(Transaction transaction);
}
