package com.cypal.ming.cypal.bean;

public class ManagerEntity {

    /**
     * content : {"amount":200,"payType":"ALIPAY","orderId":200}
     * messageEnum : OTC
     */

    public ContentBean content;
    public String messageEnum;

    public  class ContentBean {
        /**
         * amount : 200
         * payType : ALIPAY
         * orderId : 200
         */

        public int amount;
        public String payType;
        public int orderId;
        public boolean isQian=false;
    }
}
