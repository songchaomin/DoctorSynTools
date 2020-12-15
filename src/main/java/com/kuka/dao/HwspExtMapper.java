package com.kuka.dao;

import com.kuka.domain.Hwsp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HwspExtMapper {
    List<Hwsp> queryHwsp(@Param("list")List<String> spids);
    void batchUpateHwsp(@Param("list")List<Hwsp> hwspList);
}