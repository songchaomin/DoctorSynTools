package com.kuka.domain;

import java.math.BigDecimal;

public class SupplierStock {
    public static final String COL_STORE_ID = "store_id";
    public static final String COL_IS_RETAIL = "is_retail";
    public static final String COL_DRUG_NUM = "drug_num";
    public static final String COL_CLERK_NUM = "clerk_num";
    public static final String COL_DRUG_NAME = "drug_name";
    public static final String COL_PRODUCT_FACTORY = "product_factory";
    public static final String COL_DRUG_OWNER = "drug_owner";
    public static final String COL_SPEC = "spec";
    public static final String COL_PACK = "pack";
    public static final String COL_MIDDLE_PACKAGE = "middle_package";
    public static final String COL_BATCH_NUM = "batch_num";
    public static final String COL_BATCH_GROUP = "batch_group";
    public static final String COL_DUE_DATE = "due_date";
    public static final String COL_DUE_DATE2 = "due_date2";
    public static final String COL_STORE_NUM = "store_num";
    public static final String COL_HIGHTEST_PRICE = "hightest_price";
    public static final String COL_PRICE = "price";
    public static final String COL_UNIT = "unit";
    public static final String COL_DRUG_TYPE = "drug_type";
    public static final String COL_TAX_RATE = "tax_rate";
    public static final String COL_APPROVE_NO = "approve_no";
    public static final String COL_STORE_NO = "store_no";
    public static final String COL_SPECIAL_DRUG = "special_drug";
    public static final String COL_SUPPLIER_CATEGORY = "supplier_category";
    public static final String COL_MAIN_URL = "main_url";
    public static final String COL_UPDATE_DATE = "update_date";
    public static final String COL_DRUG_BAR_CODE = "drug_bar_code";
    public static final String COL_DRUG_CODE = "drug_code";
    public static final String COL_FACTORY_CODE = "factory_code";
    public static final String COL_DRUG_OWNER_CODE = "drug_owner_code";
    public static final String COL_PROD_DATE = "prod_date";
    public static final String COL_PROD_DATE2 = "prod_date2";
    private String storeId;

    private Integer isRetail;

    private String drugNum;

    private String clerkNum;

    private String drugName;

    private String productFactory;

    private String drugOwner;

    private String spec;

    private String pack;

    private String middlePackage;

    private String batchNum;

    private String batchGroup;

    private String dueDate;

    private String dueDate2;

    private Long storeNum;

    private BigDecimal hightestPrice;

    private BigDecimal price;

    private String unit;

    private String drugType;

    private Integer taxRate;

    private String approveNo;

    private String storeNo;

    private Integer specialDrug;

    private String supplierCategory;

    private String mainUrl;

    private String updateDate;

    private String drugBarCode;

    private String drugCode;

    private String factoryCode;

    private String drugOwnerCode;

    private String prodDate;

    private String prodDate2;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getIsRetail() {
        return isRetail;
    }

    public void setIsRetail(Integer isRetail) {
        this.isRetail = isRetail;
    }

    public String getDrugNum() {
        return drugNum;
    }

    public void setDrugNum(String drugNum) {
        this.drugNum = drugNum;
    }

    public String getClerkNum() {
        return clerkNum;
    }

    public void setClerkNum(String clerkNum) {
        this.clerkNum = clerkNum;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getProductFactory() {
        return productFactory;
    }

    public void setProductFactory(String productFactory) {
        this.productFactory = productFactory;
    }

    public String getDrugOwner() {
        return drugOwner;
    }

    public void setDrugOwner(String drugOwner) {
        this.drugOwner = drugOwner;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getMiddlePackage() {
        return middlePackage;
    }

    public void setMiddlePackage(String middlePackage) {
        this.middlePackage = middlePackage;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getBatchGroup() {
        return batchGroup;
    }

    public void setBatchGroup(String batchGroup) {
        this.batchGroup = batchGroup;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate2() {
        return dueDate2;
    }

    public void setDueDate2(String dueDate2) {
        this.dueDate2 = dueDate2;
    }

    public Long getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(Long storeNum) {
        this.storeNum = storeNum;
    }

    public BigDecimal getHightestPrice() {
        return hightestPrice;
    }

    public void setHightestPrice(BigDecimal hightestPrice) {
        this.hightestPrice = hightestPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public Integer getSpecialDrug() {
        return specialDrug;
    }

    public void setSpecialDrug(Integer specialDrug) {
        this.specialDrug = specialDrug;
    }

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDrugBarCode() {
        return drugBarCode;
    }

    public void setDrugBarCode(String drugBarCode) {
        this.drugBarCode = drugBarCode;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getDrugOwnerCode() {
        return drugOwnerCode;
    }

    public void setDrugOwnerCode(String drugOwnerCode) {
        this.drugOwnerCode = drugOwnerCode;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public String getProdDate2() {
        return prodDate2;
    }

    public void setProdDate2(String prodDate2) {
        this.prodDate2 = prodDate2;
    }
}