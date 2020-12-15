package com.kuka.services.impl;

import com.kuka.dao.InventoryConfigExtMapper;
import com.kuka.dao.InventoryConfigMapper;
import com.kuka.domain.InventoryConfig;
import com.kuka.services.InventoryConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryConfigServiceImpl implements InventoryConfigService {
    @Autowired
    private InventoryConfigMapper inventoryConfigMapper;
    @Autowired
    private InventoryConfigExtMapper inventoryConfigExtMapper;
    @Override
    public List<InventoryConfig> queryInventoryConfig() {
        return inventoryConfigExtMapper.queryInventoryConfig();
    }

    @Override
    public void updateInventoryConfigByType(String content, String type) {
        inventoryConfigExtMapper.updateInventoryConfigByType(content,type);
    }
}
