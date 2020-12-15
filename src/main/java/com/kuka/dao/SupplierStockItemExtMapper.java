package com.kuka.dao;

import com.kuka.domain.SupplierStockItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierStockItemExtMapper {
    List<SupplierStockItem> queryItemByDrugNum(@Param("list") List<String> drugNums);
}