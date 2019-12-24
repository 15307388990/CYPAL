package com.cypal.ming.cypal.bean;

public class DescmisionEntity {


    /**
     * code : 1
     * msg : success
     * data : {"WXPAY":"0.900%","ALIPAY":"0.900%","CLOUDPAY":"0.900%","SECONDS":"0.04000%","FIRST":"0.06000%"}
     * serverTime : 1554308647207
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * WXPAY : 0.900%
         * ALIPAY : 0.900%
         * CLOUDPAY : 0.900%
         * SECONDS : 0.04000%
         * FIRST : 0.06000%
         */

        public String WXPAY;
        public String ALIPAY;
        public String CLOUDPAY;
        public String SECONDS;
        public String FIRST;
        public String PDD;
        public String CNP;
        public String WBHB;
        public String ZZ;

    }
}
