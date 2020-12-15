package com.kuka.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.kuka.dao.SpkfkExtMapper;
import com.kuka.dao.SpkfkMapper;
import com.kuka.domain.Spkfk;
import com.kuka.services.SpkfkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class SpkfkServiceImpl implements SpkfkService {
    private static final int dbHandlerItem = 50;
    @Autowired
    private SpkfkExtMapper spkfkExtMapper;
    @Autowired
    private SpkfkMapper spkfkMapper;
    @Override
    public void batchInsertSpkfk(List<Spkfk> spkfkList) {
        if (!CollectionUtils.isEmpty(spkfkList)) {
            //分批操作
            Integer totalCount = spkfkList.size();
            //计算需要遍历的次数
            Integer loopCount = totalCount % dbHandlerItem == 0 ? totalCount / dbHandlerItem : (totalCount / dbHandlerItem) + 1;
            for (int count = 1; count <= loopCount; count++) {
                List<Spkfk> spkfks = null;
                if (spkfkList.size() <= dbHandlerItem) {
                    spkfks = new ArrayList<>(spkfkList.subList(0, spkfkList.size()));
                    try {
                        spkfkExtMapper.batchInsert(spkfks);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    spkfkList.subList(0, spkfkList.size()).clear();
                } else {
                    spkfks = new ArrayList<>(spkfkList.subList(0, dbHandlerItem));
                    try {
                        spkfkExtMapper.batchInsert(spkfks);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    spkfkList.subList(0, dbHandlerItem).clear();
                }
            }
        }

    }
}
