package com.cypal.ming.cypal.bean;

import java.io.Serializable;
import java.util.List;

public class AccountListEntity implements Serializable {

    /**
     * code : 1
     * msg : success
     * data : [{"id":8,"createTime":"2019-03-23 20:22:40","deleted":false,"userId":15,"accountName":"15307388990","realName":"李志伟","accountType":"WXPAY","accountData":"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J","used":true}]
     * serverTime : 1553343767573
     */

    public int code;
    public String msg;
    public long serverTime;
    public List<DataBean> data;

    public class DataBean implements Serializable {
        /**
         * id : 8
         * createTime : 2019-03-23 20:22:40
         * deleted : false
         * userId : 15
         * accountName : 15307388990
         * realName : 李志伟
         * accountType : WXPAY
         * accountData : wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J
         * used : true
         */

        public int id;
        public String createTime;
        public boolean deleted;
        public int userId;
        public String accountName;
        public String realName;
        public String accountType;
        public String accountData;
        public boolean used;
    }
}
