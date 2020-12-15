package com.kuka.dao;

import com.kuka.domain.Spkfjc;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpkfjcMapper {
    int deleteByPrimaryKey(String spid);

    int insert(Spkfjc record);

    int insertSelective(Spkfjc record);

    Spkfjc selectByPrimaryKey(String spid);

    int updateByPrimaryKeySelective(Spkfjc record);

    int updateByPrimaryKey(Spkfjc record);

    int batchInsert(@Param("list") List<Spkfjc> list);
}