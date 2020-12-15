package com.kuka.services;

import com.kuka.domain.ResultDto;
import com.kuka.domain.SupplierStock;

import java.util.List;

public interface SupplierStockService {
    int batchInsertSupplierStock(List<SupplierStock> supplierStocks);

    int querySupplierStockItemCount();

    List<SupplierStock> querySupplierStockItemByPage(int pageSize, int page);

    void truncateSupplierStock();

    /**
     * 查询合并金额的库存总记录数
     * @return
     */
    int queryMergeSupplierStockItemCount();

    /**
     * 查询合并金额的库存
     * @return
     */
    List<SupplierStock> queryMergeSupplierStockItemByPage(int pageSize, int page);

    /**
     * 查询带批次号的库存信息总记录
     * @return
     */
    int queryBatchSupplierStockItemCount();

    /**
     * 查询带批次号的库存信息
     * @return
     */
    List<SupplierStock> queryBathSupplierStockItemByPage(int pageSize, int page);


}
