package com.cypal.ming.cypal.bean;

public class MySettingEntity {

    /**
     * code : 1
     * msg : success
     * data : {"certificationInfo":{"identitycardFront":"http://111.230.242.115:8888//certification/20190321/IMG1553183224189.jpg","realName":"珑铸","identitycardNumber":"432524198907100496"},"bailMoneyRecord_status":"FAIL","bailMoneyRecord_description":"没有缴纳保证金","certification_status":"PROCESS","certification_description":"待审核身份认证"}
     * serverTime : 1553268826747
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public  class DataBean {
        /**
         * certificationInfo : {"identitycardFront":"http://111.230.242.115:8888//certification/20190321/IMG1553183224189.jpg","realName":"珑铸","identitycardNumber":"432524198907100496"}
         * bailMoneyRecord_status : FAIL
         * bailMoneyRecord_description : 没有缴纳保证金
         * certification_status : PROCESS
         * certification_description : 待审核身份认证
         */

        public CertificationInfoBean certificationInfo;
        public String bailMoneyRecord_status;
        public String bailMoneyRecord_description;
        public String certification_status;
        public String certification_description;

        public  class CertificationInfoBean {
            /**
             * identitycardFront : http://111.230.242.115:8888//certification/20190321/IMG1553183224189.jpg
             * realName : 珑铸
             * identitycardNumber : 432524198907100496
             */

            public String identitycardFront;
            public String realName;
            public String identitycardNumber;
        }
    }
}
