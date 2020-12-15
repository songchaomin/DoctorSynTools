package com.kuka.services.impl;

import com.kuka.dao.SupplierJixingMapper;
import com.kuka.domain.SupplierJixing;
import com.kuka.services.SupplierJxingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierJxingServiceImpl implements SupplierJxingService {
    @Autowired
    private SupplierJixingMapper supplierJixingMapper;
    @Override
    public List<SupplierJixing> queryAll() {
        return supplierJixingMapper.queryAll();
    }
}
