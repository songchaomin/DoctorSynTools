package com.kuka.dao;

import com.kuka.domain.Spkfjc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpkfjcExtMapper {
    List<Spkfjc> querySpkfjc(@Param("list") List<String> spids);
    void batchUpateSpkfjc(@Param("list") List<Spkfjc> spkfjcList);
}