package com.kuka.dao;

import com.kuka.domain.Spkfk;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpkfkExtMapper {
    int batchInsert(@Param("list") List<Spkfk> list);
}