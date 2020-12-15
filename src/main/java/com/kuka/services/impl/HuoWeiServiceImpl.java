package com.kuka.services.impl;

import com.kuka.dao.HuoweizlMapper;
import com.kuka.domain.Huoweizl;
import com.kuka.services.HuoWeiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HuoWeiServiceImpl implements HuoWeiService {
    @Autowired
    private HuoweizlMapper huoweizlMapper;
    @Override
    public String queryMaxHuoWeiNo() {
        return huoweizlMapper.queryMaxHuoWeiNo();
    }

    @Override
    public void insertAHHHuoWei(Huoweizl huoweizl) {
        huoweizlMapper.insert(huoweizl);
    }
}
