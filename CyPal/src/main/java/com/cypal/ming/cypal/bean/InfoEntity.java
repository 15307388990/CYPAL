package com.cypal.ming.cypal.bean;

public class InfoEntity {

    /**
     * code : 1
     * msg : success
     * data : {"myInformationBean":{"avatar":null,"nickName":"153****8991","creditScore":100,"signStatus":"NOTSIGNIN","account":null,"showInviteFriends":true,"certification":false}}
     * serverTime : 1553173582480
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * myInformationBean : {"avatar":null,"nickName":"153****8991","creditScore":100,"signStatus":"NOTSIGNIN","account":null,"showInviteFriends":true,"certification":false}
         */

        public MyInformationBeanBean myInformationBean;

        public class MyInformationBeanBean {
            /**
             * avatar : null
             * nickName : 153****8991
             * creditScore : 100
             * signStatus : NOTSIGNIN
             * account : null
             * showInviteFriends : true
             * certification : false
             */

            public String avatar;
            public String nickName;
            public int creditScore;
            public String signStatus;
            public String account;
            public boolean showInviteFriends;
            public boolean certification;
            public boolean isShowTeamRecharge;
        }
    }
}
