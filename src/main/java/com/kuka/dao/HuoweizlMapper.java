package com.kuka.dao;

import com.kuka.domain.Huoweizl;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HuoweizlMapper {
    int deleteByPrimaryKey(String hw);

    int insert(Huoweizl record);

    int insertSelective(Huoweizl record);

    Huoweizl selectByPrimaryKey(String hw);

    int updateByPrimaryKeySelective(Huoweizl record);

    int updateByPrimaryKey(Huoweizl record);

    int batchInsert(@Param("list") List<Huoweizl> list);

    Huoweizl queryMaxHuoWeiNo();

}