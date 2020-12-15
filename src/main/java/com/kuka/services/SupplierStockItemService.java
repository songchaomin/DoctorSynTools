package com.kuka.services;

import com.kuka.domain.ResultDto;
import com.kuka.domain.SupplierStockItem;

import java.util.List;

public interface SupplierStockItemService {
    void batchInsertSupplierStockItem(List<SupplierStockItem> supplierStockItems);
    List<SupplierStockItem> queryItemByDrugNum(List<String> drugNums);
}
