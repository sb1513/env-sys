package com.backend.envsysbackend.entity;

public class Aqi_statistics {
    private int aqiId;
    private String aqiExplain;
    private int aqiCount;

    public int getAqiId() {
        return aqiId;
    }

    public void setAqiId(int aqiId) {
        this.aqiId = aqiId;
    }

    public String getAqiExplain() {
        return aqiExplain;
    }

    public void setAqiExplain(String aqiExplain) {
        this.aqiExplain = aqiExplain;
    }

    public int getAqiCount() {
        return aqiCount;
    }

    public void setAqiCount(int aqiCount) {
        this.aqiCount = aqiCount;
    }
}
