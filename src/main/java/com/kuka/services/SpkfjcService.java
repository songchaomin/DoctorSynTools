package com.kuka.services;

import com.kuka.domain.Spkfjc;

import java.util.List;

public interface SpkfjcService {
    List<Spkfjc> querySpkfjc(List<String> spids);
    void batchInsertSpkfjc(List<Spkfjc> spkfjcList);
    void batchUpateSpkfjc(List<Spkfjc> spkfjcList);
}
