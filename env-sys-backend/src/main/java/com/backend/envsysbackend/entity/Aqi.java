package com.backend.envsysbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("aqi")
public class Aqi {
    @TableId(value = "aqi_id",type= IdType.AUTO)
    private int aqiId;
    @TableField("chinese_explain")
    private String chineseExplain;
    @TableField("aqi_explain")
    private String aqiExplain;
    @TableField("color")
    private String color;
    @TableField("health_impact")
    private String healthImpact;
    @TableField("take_steps")
    private String takeSteps;
    @TableField("so2_min")
    private int so2Min;
    @TableField("so2_max")
    private int so2Max;
    @TableField("co_min")
    private int coMin;
    @TableField("co_max")
    private int coMax;
    @TableField("spm_min")
    private int spmMin;
    @TableField("spm_max")
    private int spmMax;
    @TableField("remarks")
    private String remarks;

    public int getAqiId() {
        return aqiId;
    }

    public void setAqiId(int aqiId) {
        this.aqiId = aqiId;
    }

    public String getChineseExplain() {
        return chineseExplain;
    }

    public void setChineseExplain(String chineseExplain) {
        this.chineseExplain = chineseExplain;
    }

    public String getAqiExplain() {
        return aqiExplain;
    }

    public void setAqiExplain(String aqiExplain) {
        this.aqiExplain = aqiExplain;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHealthImpact() {
        return healthImpact;
    }

    public void setHealthImpact(String healthImpact) {
        this.healthImpact = healthImpact;
    }

    public String getTakeSteps() {
        return takeSteps;
    }

    public void setTakeSteps(String takeSteps) {
        this.takeSteps = takeSteps;
    }

    public int getSo2Min() {
        return so2Min;
    }

    public void setSo2Min(int so2Min) {
        this.so2Min = so2Min;
    }

    public int getSo2Max() {
        return so2Max;
    }

    public void setSo2Max(int so2Max) {
        this.so2Max = so2Max;
    }

    public int getCoMin() {
        return coMin;
    }

    public void setCoMin(int coMin) {
        this.coMin = coMin;
    }

    public int getCoMax() {
        return coMax;
    }

    public void setCoMax(int coMax) {
        this.coMax = coMax;
    }

    public int getSpmMin() {
        return spmMin;
    }

    public void setSpmMin(int spmMin) {
        this.spmMin = spmMin;
    }

    public int getSpmMax() {
        return spmMax;
    }

    public void setSpmMax(int spmMax) {
        this.spmMax = spmMax;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
