package com.kuka.services.impl;

import com.kuka.dao.SpkfjcExtMapper;
import com.kuka.dao.SpkfjcMapper;
import com.kuka.domain.Spkfjc;
import com.kuka.services.SpkfjcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SpkfjcServiceImpl implements SpkfjcService {
    private static final int dbHandlerItem = 50;
    @Autowired
    private SpkfjcMapper spkfjcMapper;
    @Autowired
    private SpkfjcExtMapper spkfjcExtMapper;

    @Override
    public List<Spkfjc> querySpkfjc(List<String> spids) {
        return spkfjcExtMapper.querySpkfjc(spids);
    }

    @Override
    public void batchInsertSpkfjc(List<Spkfjc> spkfjcList) {
        if (!CollectionUtils.isEmpty(spkfjcList)) {
            //分批操作
            Integer totalCount = spkfjcList.size();
            //计算需要遍历的次数
            Integer loopCount = totalCount % dbHandlerItem == 0 ? totalCount / dbHandlerItem : (totalCount / dbHandlerItem) + 1;
            for (int count = 1; count <= loopCount; count++) {
                List<Spkfjc> spkfjcs = null;
                if (spkfjcList.size() <= dbHandlerItem) {
                    spkfjcs = new ArrayList<>(spkfjcList.subList(0, spkfjcList.size()));
                    try {
                        spkfjcMapper.batchInsert(spkfjcs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    spkfjcList.subList(0, spkfjcList.size()).clear();
                } else {
                    spkfjcs = new ArrayList<>(spkfjcList.subList(0, dbHandlerItem));
                    try {
                        spkfjcMapper.batchInsert(spkfjcs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    spkfjcList.subList(0, dbHandlerItem).clear();
                }
            }
        }
    }

    @Override
    public void batchUpateSpkfjc(List<Spkfjc> spkfjcList) {
        if (!CollectionUtils.isEmpty(spkfjcList)) {
            //分批操作
            Integer totalCount = spkfjcList.size();
            //计算需要遍历的次数
            Integer loopCount = totalCount % dbHandlerItem == 0 ? totalCount / dbHandlerItem : (totalCount / dbHandlerItem) + 1;
            for (int count = 1; count <= loopCount; count++) {
                List<Spkfjc> spkfjcs = null;
                if (spkfjcList.size() <= dbHandlerItem) {
                    spkfjcs = new ArrayList<>(spkfjcList.subList(0, spkfjcList.size()));
                    try {
                        spkfjcExtMapper.batchUpateSpkfjc(spkfjcs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    spkfjcList.subList(0, spkfjcList.size()).clear();
                } else {
                    spkfjcs = new ArrayList<>(spkfjcList.subList(0, dbHandlerItem));
                    try {
                        spkfjcExtMapper.batchUpateSpkfjc(spkfjcs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    spkfjcList.subList(0, dbHandlerItem).clear();
                }
            }
        }
    }
}
