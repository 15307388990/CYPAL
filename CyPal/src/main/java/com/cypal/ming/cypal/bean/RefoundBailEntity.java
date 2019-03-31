package com.cypal.ming.cypal.bean;

import java.util.List;

public class RefoundBailEntity {

    /**
     * code : 1
     * msg : success
     * data : {"description":"已成功缴纳保证金","status":"SUCCESS","usedPayAccountList":["WXPAY"]}
     * serverTime : 1554023575068
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * description : 已成功缴纳保证金
         * status : SUCCESS
         * usedPayAccountList : ["WXPAY"]
         */

        public String description;
        public String status;
        public String usedPayAccountList;
    }
}
