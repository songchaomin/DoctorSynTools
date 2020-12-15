package com.kuka.domain;

import java.util.List;

public class DubheErpSupplierStockQueryResponse {
    private String flag;
    private String code;
    private String message;
    private List<SupplierStock> supplierStockList;
    private long total;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SupplierStock> getSupplierStockList() {
        return supplierStockList;
    }

    public void setSupplierStockList(List<SupplierStock> supplierStockList) {
        this.supplierStockList = supplierStockList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
