package com.backend.envsysbackend.entity;

public class Province_statistics {
    private int provinceId;
    private String provinceName;
    private String provinceAbbr;
    private int so2Count;
    private int coCount;
    private int spmCount;
    private int aqiCount;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceAbbr() {
        return provinceAbbr;
    }

    public void setProvinceAbbr(String provinceAddr) {
        this.provinceAbbr = provinceAddr;
    }

    public int getSo2Count() {
        return so2Count;
    }

    public void setSo2Count(int so2Count) {
        this.so2Count = so2Count;
    }

    public int getCoCount() {
        return coCount;
    }

    public void setCoCount(int coCount) {
        this.coCount = coCount;
    }

    public int getAqiCount() {
        return aqiCount;
    }

    public void setAqiCount(int aqiCount) {
        this.aqiCount = aqiCount;
    }

    public int getSpmCount() {
        return spmCount;
    }

    public void setSpmCount(int spmCount) {
        this.spmCount = spmCount;
    }
}