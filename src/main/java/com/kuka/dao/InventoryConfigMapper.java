package com.kuka.dao;

import com.kuka.domain.InventoryConfig;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InventoryConfig record);

    int insertSelective(InventoryConfig record);

    InventoryConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InventoryConfig record);

    int updateByPrimaryKey(InventoryConfig record);

    int batchInsert(@Param("list") List<InventoryConfig> list);
}