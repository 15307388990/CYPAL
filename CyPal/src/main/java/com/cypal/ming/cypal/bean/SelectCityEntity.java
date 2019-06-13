package com.cypal.ming.cypal.bean;

import java.util.List;

public class SelectCityEntity {

    /**
     * code : 1
     * msg : success
     * data : [{"id":1,"createTime":"2019-06-13 04:26:44","deleted":false,"cityName":"全部","citySort":"#"},{"id":2,"createTime":"2019-06-13 04:26:59","deleted":false,"cityName":"北京","citySort":"B"},{"id":3,"createTime":"2019-06-13 04:27:09","deleted":false,"cityName":"深圳","citySort":"C"},{"id":4,"createTime":"2019-06-13 04:27:18","deleted":false,"cityName":"天津","citySort":"T"}]
     * serverTime : 1560405701369
     */

    private int code;
    private String msg;
    private long serverTime;
    private List<CityEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public List<CityEntity> getData() {
        return data;
    }

    public void setData(List<CityEntity> data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
