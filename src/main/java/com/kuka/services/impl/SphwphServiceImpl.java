package com.kuka.services.impl;

import com.kuka.dao.SphwphExtMapper;
import com.kuka.dao.SphwphMapper;
import com.kuka.domain.Sphwph;
import com.kuka.services.SphwphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SphwphServiceImpl implements SphwphService {
    private static final int dbHandlerItem = 50;
    @Autowired
    private SphwphExtMapper sphwphExtMapper;

    @Autowired
    private SphwphMapper sphwphMapper;

    @Override
    public void batchInsertSphwph(List<Sphwph> sphwphList) {
        if (!CollectionUtils.isEmpty(sphwphList)) {
            //分批操作
            Integer totalCount = sphwphList.size();
            //计算需要遍历的次数
            Integer loopCount = totalCount % dbHandlerItem == 0 ? totalCount / dbHandlerItem : (totalCount / dbHandlerItem) + 1;
            for (int count = 1; count <= loopCount; count++) {
                List<Sphwph> sphwphs = null;
                if (sphwphList.size() <= dbHandlerItem) {
                    sphwphs = new ArrayList<>(sphwphList.subList(0, sphwphList.size()));
                    try {
                        sphwphMapper.batchInsert(sphwphs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sphwphList.subList(0, sphwphList.size()).clear();
                } else {
                    sphwphs = new ArrayList<>(sphwphList.subList(0, dbHandlerItem));
                    try {
                        sphwphMapper.batchInsert(sphwphs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sphwphList.subList(0, dbHandlerItem).clear();
                }
            }
        }
    }

    @Override
    public void truncateSphwph() {
        sphwphExtMapper.truncateSphwph();
    }

    @Override
    public void deleteSphwphByHwi(String hwi) {
        sphwphExtMapper.deleteSphwphByHwi(hwi);
    }
}
