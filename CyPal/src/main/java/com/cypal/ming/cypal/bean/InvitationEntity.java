package com.cypal.ming.cypal.bean;

public class InvitationEntity {

    /**
     * code : 1
     * msg : success
     * data : {"inviteCode":"123456","qrCodeUrl":"http://111.230.242.115:8888/h5/register?inviteCode=123456"}
     * serverTime : 1554614682483
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public  class DataBean {
        /**
         * inviteCode : 123456
         * qrCodeUrl : http://111.230.242.115:8888/h5/register?inviteCode=123456
         */

        public String inviteCode;
        public String qrCodeUrl;
    }
}
