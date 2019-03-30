package com.cypal.ming.cypal.bean;

import java.io.Serializable;

public class VersionEntity implements Serializable {

    /**
     * code : 1
     * msg : success
     * data : {"id":4,"createTime":"2019-03-25 02:17:15","deleted":false,"versionId":2010,"versionName":"2.0.10","updateType":1,"updateUrl":"","updateContext":"测试版本升级","platformType":"android","lastUpdateTime":"2019-03-25 02:17:22"}
     * serverTime : 1553948790914
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean implements Serializable {
        /**
         * id : 4
         * createTime : 2019-03-25 02:17:15
         * deleted : false
         * versionId : 2010
         * versionName : 2.0.10
         * updateType : 1
         * updateUrl :
         * updateContext : 测试版本升级
         * platformType : android
         * lastUpdateTime : 2019-03-25 02:17:22
         */

        public int id;
        public String createTime;
        public boolean deleted;
        public int versionId;
        public String versionName;
        public int updateType;
        public String updateUrl;
        public String updateContext;
        public String platformType;
        public String lastUpdateTime;
    }
}
