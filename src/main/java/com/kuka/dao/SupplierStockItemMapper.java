package com.kuka.dao;

import com.kuka.domain.SupplierStockItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplierStockItemMapper {
    int deleteByPrimaryKey(String spid);

    int insert(SupplierStockItem record);

    int insertSelective(SupplierStockItem record);

    SupplierStockItem selectByPrimaryKey(String spid);

    int updateByPrimaryKeySelective(SupplierStockItem record);

    int updateByPrimaryKey(SupplierStockItem record);

    int batchInsert(@Param("list") List<SupplierStockItem> list);
}