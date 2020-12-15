package com.kuka.services.impl;

import com.kuka.dao.SupplierStockItemExtMapper;
import com.kuka.dao.SupplierStockItemMapper;
import com.kuka.domain.ResultDto;
import com.kuka.domain.SupplierStockItem;
import com.kuka.services.SupplierStockItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SupplierStockItemServiceImpl implements SupplierStockItemService {
    @Autowired
    private SupplierStockItemMapper supplierStockItemMapper;
    @Autowired
    private SupplierStockItemExtMapper supplierStockItemExtMapper;

    @Override
    public void batchInsertSupplierStockItem(List<SupplierStockItem> supplierStockItems) {
        ResultDto<Integer> resultDto=new ResultDto<>();
        if (!CollectionUtils.isEmpty(supplierStockItems)){
            supplierStockItemMapper.batchInsert(supplierStockItems);
        }
    }


    @Override
    public List<SupplierStockItem> queryItemByDrugNum(List<String> drugNums) {
        return supplierStockItemExtMapper.queryItemByDrugNum(drugNums);
    }
}
