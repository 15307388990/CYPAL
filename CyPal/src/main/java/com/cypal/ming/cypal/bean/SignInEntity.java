package com.cypal.ming.cypal.bean;

public class SignInEntity {


    /**
     * code : 1
     * msg : success
     * data : {"signReward":""}
     * serverTime : 1554131339884
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public  class DataBean {
        /**
         * signReward :
         */

        public String signReward;
    }
}
