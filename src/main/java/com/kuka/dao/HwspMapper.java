package com.kuka.dao;

import com.kuka.domain.Hwsp;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HwspMapper {
    int deleteByPrimaryKey(@Param("hw") String hw, @Param("spid") String spid);

    int insert(Hwsp record);

    int insertSelective(Hwsp record);

    Hwsp selectByPrimaryKey(@Param("hw") String hw, @Param("spid") String spid);

    int updateByPrimaryKeySelective(Hwsp record);

    int updateByPrimaryKey(Hwsp record);

    int batchInsert(@Param("list") List<Hwsp> list);
}