package com.kuka.services;

import com.kuka.domain.InventoryConfig;

import java.util.List;

public interface InventoryConfigService {
    List<InventoryConfig>  queryInventoryConfig();
    void updateInventoryConfigByType(String content,String type);
}
