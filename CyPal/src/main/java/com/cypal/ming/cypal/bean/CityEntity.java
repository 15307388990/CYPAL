package com.cypal.ming.cypal.bean;

import java.io.Serializable;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by luoming on 2018/9/30.
 */
public class CityEntity implements IndexableEntity, Serializable {
    private String citySort;
    private String cityName;

    private String sort;
    private String adcode;
    private String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCityCode() {
        return citySort;
    }

    public void setCityCode(String cityCode) {
        this.citySort = cityCode;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String getFieldIndexBy() {
        return cityName;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.cityName = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        // 需要用到拼音时(比如:搜索), 可增添pinyin字段 this.pinyin  = pinyin
        // 见 CityEntity
        this.pinyin = pinyin;
    }
}
