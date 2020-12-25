package com.kuka.services;

import com.kuka.domain.Huoweizl;

public interface HuoWeiService {
    Huoweizl  queryMaxHuoWeiNo();
    void insertAHHHuoWei(Huoweizl huoweizl);
}
