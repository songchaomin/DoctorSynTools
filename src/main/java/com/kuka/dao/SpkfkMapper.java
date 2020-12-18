package com.kuka.dao;

import com.kuka.domain.Spkfk;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpkfkMapper {
    int deleteByPrimaryKey(String spid);

    int insert(Spkfk record);

    int insertSelective(Spkfk record);

    Spkfk selectByPrimaryKey(String spid);

    int updateByPrimaryKeySelective(Spkfk record);

    int updateByPrimaryKey(Spkfk record);

    int batchInsert(@Param("list") List<Spkfk> list);
}