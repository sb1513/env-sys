package com.backend.envsysbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("statistics")
public class Statistics {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField("province_id")
    private int provinceId;
    @TableField("city_id")
    private int cityId;
    @TableField("address")
    private String address;
    @TableField("so2_value")
    private int so2Value;
    @TableField("so2_level")
    private int so2Level;
    @TableField("co_value")
    private int coValue;
    @TableField("co_level")
    private int coLevel;
    @TableField("spm_value")
    private int spmValue;
    @TableField("spm_level")
    private int spmLevel;
    @TableField("aqi_id")
    private int aqiId;
    @TableField("confirm_date")
    private String confirmDate;
    @TableField("confirm_time")
    private String confirmTime;
    @TableField("gm_id")
    private int gmId;
    @TableField("fd_id")
    private String fdId;
    @TableField("information")
    private String information;
    @TableField("remarks")
    private String remarks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSo2Value() {
        return so2Value;
    }

    public void setSo2Value(int so2Value) {
        this.so2Value = so2Value;
    }

    public int getSo2Level() {
        return so2Level;
    }

    public void setSo2Level(int so2Level) {
        this.so2Level = so2Level;
    }

    public int getCoValue() {
        return coValue;
    }

    public void setCoValue(int coValue) {
        this.coValue = coValue;
    }

    public int getCoLevel() {
        return coLevel;
    }

    public void setCoLevel(int coLevel) {
        this.coLevel = coLevel;
    }

    public int getSpmValue() {
        return spmValue;
    }

    public void setSpmValue(int spmValue) {
        this.spmValue = spmValue;
    }

    public int getSpmLevel() {
        return spmLevel;
    }

    public void setSpmLevel(int spmLevel) {
        this.spmLevel = spmLevel;
    }

    public int getAqiId() {
        return aqiId;
    }

    public void setAqiId(int aqiId) {
        this.aqiId = aqiId;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public int getGmId() {
        return gmId;
    }

    public void setGmId(int gmId) {
        this.gmId = gmId;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
