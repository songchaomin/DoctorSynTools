package com.kuka.dao;

import com.kuka.domain.SupplierJixing;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplierJixingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SupplierJixing record);

    int insertSelective(SupplierJixing record);

    SupplierJixing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SupplierJixing record);

    int updateByPrimaryKey(SupplierJixing record);

    int batchInsert(@Param("list") List<SupplierJixing> list);

    List<SupplierJixing> queryAll();
}