package com.kuka.dao;

import com.kuka.domain.Sphwph;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SphwphMapper {
    int deleteByPrimaryKey(@Param("spid") String spid, @Param("hw") String hw, @Param("pihao") String pihao);

    int insert(Sphwph record);

    int insertSelective(Sphwph record);

    Sphwph selectByPrimaryKey(@Param("spid") String spid, @Param("hw") String hw, @Param("pihao") String pihao);

    int updateByPrimaryKeySelective(Sphwph record);

    int updateByPrimaryKey(Sphwph record);

    int batchInsert(@Param("list") List<Sphwph> list);
}