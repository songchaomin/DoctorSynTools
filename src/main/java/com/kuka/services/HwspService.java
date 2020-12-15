package com.kuka.services;

import com.kuka.domain.Hwsp;

import java.util.List;

public interface HwspService {
    List<Hwsp> queryHwsp(List<String> spids);
    void batchInsertHwsp(List<Hwsp> hwspList);
    void batchUpateHwsp(List<Hwsp> hwspList);

}
