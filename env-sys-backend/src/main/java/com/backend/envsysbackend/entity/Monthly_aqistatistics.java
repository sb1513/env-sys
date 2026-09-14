package com.backend.envsysbackend.entity;

public class Monthly_aqistatistics {
    private String year;
    private String month;
    private int aqiCount;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAqiCount() {
        return aqiCount;
    }

    public void setAqiCount(int aqiCount) {
        this.aqiCount = aqiCount;
    }
}
