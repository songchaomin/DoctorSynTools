package com.kuka.dao;

import com.kuka.domain.SupplierStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierStockMapper {
    int deleteByPrimaryKey(String storeId);

    int insert(SupplierStock record);

    int insertSelective(SupplierStock record);

    SupplierStock selectByPrimaryKey(String storeId);

    int updateByPrimaryKeySelective(SupplierStock record);

    int updateByPrimaryKey(SupplierStock record);

    int batchInsert(@Param("list") List<SupplierStock> list);
}