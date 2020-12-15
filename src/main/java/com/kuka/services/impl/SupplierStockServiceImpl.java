package com.kuka.services.impl;

import com.kuka.dao.SupplierStockExtMapper;
import com.kuka.dao.SupplierStockMapper;
import com.kuka.domain.ResultDto;
import com.kuka.domain.SupplierStock;
import com.kuka.enums.ResultEnum;
import com.kuka.services.SupplierStockService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierStockServiceImpl implements SupplierStockService {
    /**
     * 数据库每次处理数据的条数（sqlserver 有入参2100个的限制）
     */
    private static final int dbHandlerItem = 50;
    @Autowired
    private SupplierStockMapper supplierStockMapper;
    @Autowired
    private SupplierStockExtMapper supplierStockExtMapper;

    @Override
    public int batchInsertSupplierStock(List<SupplierStock> supplierStocks) {
        if (!CollectionUtils.isEmpty(supplierStocks)) {
            //分批操作
            Integer totalCount = supplierStocks.size();
            //计算需要遍历的次数
            Integer loopCount = totalCount % dbHandlerItem == 0 ? totalCount / dbHandlerItem : (totalCount / dbHandlerItem) + 1;
            for (int count = 1; count <= loopCount; count++) {
                List<SupplierStock> handlerSupplierStock = null;
                if (supplierStocks.size() <= dbHandlerItem) {
                    handlerSupplierStock = new ArrayList<>(supplierStocks.subList(0, supplierStocks.size()));
                    try {
                        supplierStockMapper.batchInsert(handlerSupplierStock);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    supplierStocks.subList(0, supplierStocks.size()).clear();
                } else {
                    handlerSupplierStock = new ArrayList<>(supplierStocks.subList(0, dbHandlerItem));
                    try {
                        supplierStockMapper.batchInsert(handlerSupplierStock);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    supplierStocks.subList(0, dbHandlerItem).clear();
                }
            }
        }
        return supplierStocks.size();
    }

    @Override
    public int querySupplierStockItemCount() {
        return supplierStockExtMapper.querySupplierStockItemCount();
    }

    @Override
    public List<SupplierStock> querySupplierStockItemByPage(int pageSize, int page) {
        return supplierStockExtMapper.querySupplierStockItem(pageSize,page);
    }

    @Override
    public void truncateSupplierStock() {
        supplierStockExtMapper.truncateSupplierStock();
    }


    @Override
    public int queryMergeSupplierStockItemCount() {
        return supplierStockExtMapper.queryMergeSupplierStockItemCount();
    }

    @Override
    public List<SupplierStock> queryMergeSupplierStockItemByPage(int pageSize, int page) {
        return supplierStockExtMapper.queryMergeSupplierStockItem(pageSize,page);
    }

    @Override
    public int queryBatchSupplierStockItemCount() {
        return supplierStockExtMapper.queryBatchSupplierStockItemCount();
    }

    @Override
    public List<SupplierStock> queryBathSupplierStockItemByPage(int pageSize, int page) {
        return supplierStockExtMapper.queryBathSupplierStockItemByPage(pageSize,page);
    }
}
