package com.backend.envsysbackend.entity;

import java.util.List;

public class Other_statistics {
    private int capitalCoverage;
    private int bigCityCoverage;
    private int totalCount;
    private int goodCount;
    private int pollutionCount;
    private List<ProvincePollutionStatistics> provincePollution;

    public int getCapitalCoverage() {
        return capitalCoverage;
    }

    public void setCapitalCoverage(int capitalCoverage) {
        this.capitalCoverage = capitalCoverage;
    }

    public int getBigCityCoverage() {
        return bigCityCoverage;
    }

    public void setBigCityCoverage(int bigCityCoverage) {
        this.bigCityCoverage = bigCityCoverage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getPollutionCount() {
        return pollutionCount;
    }

    public void setPollutionCount(int pollutionCount) {
        this.pollutionCount = pollutionCount;
    }

    public List<ProvincePollutionStatistics> getProvincePollution() {
        return provincePollution;
    }

    public void setProvincePollution(List<ProvincePollutionStatistics> provincePollution) {
        this.provincePollution = provincePollution;
    }
}