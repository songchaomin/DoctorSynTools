package com.kuka.services;

import com.kuka.domain.Sphwph;

import java.util.List;

public interface SphwphService {
    void batchInsertSphwph(List<Sphwph> sphwphList);
    void truncateSphwph();
}
