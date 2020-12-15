package com.kuka.dao;

import com.kuka.domain.SupplierStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierStockExtMapper {
   List<SupplierStock> querySupplierStockItem(@Param("pageSize") int pageSize,@Param("page") int page);
   int querySupplierStockItemCount();
   void truncateSupplierStock();
    List<SupplierStock> queryMergeSupplierStockItem(@Param("pageSize") int pageSize,@Param("page") int page);
    int queryMergeSupplierStockItemCount();

    List<SupplierStock> queryBathSupplierStockItemByPage(@Param("pageSize") int pageSize,@Param("page") int page);
    int queryBatchSupplierStockItemCount();
}