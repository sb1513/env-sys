package com.backend.envsysbackend.entity;

public class ProvincePollutionStatistics {
    private int provinceId;
    private String provinceName;
    private int pollutionCount;
    private int totalCount;
    private double pollutionRate;

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

    public int getPollutionCount() {
        return pollutionCount;
    }

    public void setPollutionCount(int pollutionCount) {
        this.pollutionCount = pollutionCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getPollutionRate() {
        return pollutionRate;
    }

    public void setPollutionRate(double pollutionRate) {
        this.pollutionRate = pollutionRate;
    }
}