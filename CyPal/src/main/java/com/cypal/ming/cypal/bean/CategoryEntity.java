package com.cypal.ming.cypal.bean;

import java.util.List;

public class CategoryEntity {

    /**
     * code : 1
     * msg : success
     * data : [{"type":"ALIPAY","value":"支付宝"},{"type":"WXPAY","value":"微信"},{"type":"CLOUDPAY","value":"云闪付"}]
     * serverTime : 1553324316110
     */

    public int code;
    public String msg;
    public long serverTime;
    public List<DataBean> data;

    public  class DataBean {
        /**
         * type : ALIPAY
         * value : 支付宝
         */

        public String type;
        public String value;
    }
}
