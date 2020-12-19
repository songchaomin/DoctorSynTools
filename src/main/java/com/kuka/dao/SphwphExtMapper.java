package com.kuka.dao;

import com.kuka.domain.Sphwph;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SphwphExtMapper {
    void truncateSphwph();

    void deleteSphwphByHwi(String hwi);
}