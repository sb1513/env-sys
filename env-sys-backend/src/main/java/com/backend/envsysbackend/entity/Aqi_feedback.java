package com.backend.envsysbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("aqi_feedback")
public class Aqi_feedback {
    @TableId(value = "af_id", type = IdType.AUTO)
    private int afId;
    @TableField("tel_id")
    private String telId;
    @TableField("province_id")
    private int provinceId;
    @TableField("city_id")
    private int cityId;
    @TableField("address")
    private String address;
    @TableField("information")
    private String information;
    @TableField("estimated_grade")
    private int estimatedGrade;
    @TableField("af_date")
    private String afDate;
    @TableField("af_time")
    private String afTime;
    @TableField("gm_id")
    private int gmId;
    @TableField("assign_date")
    private String assignDate;
    @TableField("assign_time")
    private String assignTime;
    @TableField("state")
    private int state;
    @TableField("remarks")
    private String remarks;

    public int getAfId() {
        return afId;
    }

    public void setAfId(int afId) {
        this.afId = afId;
    }

    public String getTelId() {
        return telId;
    }

    public void setTelId(String telId) {
        this.telId = telId;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getEstimatedGrade() {
        return estimatedGrade;
    }

    public void setEstimatedGrade(int estimatedGrade) {
        this.estimatedGrade = estimatedGrade;
    }

    public String getAfDate() {
        return afDate;
    }

    public void setAfDate(String afDate) {
        this.afDate = afDate;
    }

    public String getAfTime() {
        return afTime;
    }

    public void setAfTime(String afTime) {
        this.afTime = afTime;
    }

    public int getGmId() {
        return gmId;
    }

    public void setGmId(int gmId) {
        this.gmId = gmId;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
