package com.kuka.services.impl;

import com.kuka.dao.HwspExtMapper;
import com.kuka.dao.HwspMapper;
import com.kuka.domain.Hwsp;
import com.kuka.services.HwspService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HwspServiceImpl implements HwspService {
    private static final int dbHandlerItem = 50;
    @Autowired
    private HwspMapper hwspMapper;
    @Autowired
    private HwspExtMapper hwspExtMapper;

    @Override
    public List<Hwsp> queryHwsp(List<String> spids) {
        return hwspExtMapper.queryHwsp(spids);
    }

    @Override
    public void batchInsertHwsp(List<Hwsp> hwspList) {
        if (!CollectionUtils.isEmpty(hwspList)) {
            //分批操作
            Integer totalCount = hwspList.size();
            //计算需要遍历的次数
            Integer loopCount = totalCount % dbHandlerItem == 0 ? totalCount / dbHandlerItem : (totalCount / dbHandlerItem) + 1;
            for (int count = 1; count <= loopCount; count++) {
                List<Hwsp> hwsps = null;
                if (hwspList.size() <= dbHandlerItem) {
                    hwsps = new ArrayList<>(hwspList.subList(0, hwspList.size()));
                    try {
                        hwspMapper.batchInsert(hwsps);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hwspList.subList(0, hwspList.size()).clear();
                } else {
                    hwsps = new ArrayList<>(hwspList.subList(0, dbHandlerItem));
                    try {
                        hwspMapper.batchInsert(hwsps);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hwspList.subList(0, dbHandlerItem).clear();
                }
            }
        }
    }

    @Override
    public void batchUpateHwsp(List<Hwsp> hwspList) {
        if (!CollectionUtils.isEmpty(hwspList)) {
            //分批操作
            Integer totalCount = hwspList.size();
            //计算需要遍历的次数
            Integer loopCount = totalCount % dbHandlerItem == 0 ? totalCount / dbHandlerItem : (totalCount / dbHandlerItem) + 1;
            for (int count = 1; count <= loopCount; count++) {
                List<Hwsp> hwsps = null;
                if (hwspList.size() <= dbHandlerItem) {
                    hwsps = new ArrayList<>(hwspList.subList(0, hwspList.size()));
                    try {
                        hwspExtMapper.batchUpateHwsp(hwsps);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hwspList.subList(0, hwspList.size()).clear();
                } else {
                    hwsps = new ArrayList<>(hwspList.subList(0, dbHandlerItem));
                    try {
                        hwspExtMapper.batchUpateHwsp(hwsps);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hwspList.subList(0, dbHandlerItem).clear();
                }
            }
        }
    }
}
