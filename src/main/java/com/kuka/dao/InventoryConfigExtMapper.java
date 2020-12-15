package com.kuka.dao;

import com.kuka.domain.InventoryConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InventoryConfigExtMapper {
    /**
     * 查询所有的库存配置
     * @return
     */
    List<InventoryConfig> queryInventoryConfig();

    void updateInventoryConfigByType(@Param("cotent") String content,@Param("type") String type);
}