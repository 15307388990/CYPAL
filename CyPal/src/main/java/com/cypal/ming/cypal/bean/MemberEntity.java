package com.cypal.ming.cypal.bean;

import java.io.Serializable;

public class MemberEntity implements Serializable {


    /**
     * code : 1
     * msg : success
     * data : {"bailMoneyRecord_status":"SUCCESS","bailMoneyRecord_description":"已成功缴纳保证金","host":"http://111.230.242.115:8888/static/","certification_status":"SUCCESS","certification_description":"已完成身份认证","certification":{"id":3,"createTime":"2019-03-30 00:30:32","deleted":false,"userId":18,"status":"SUCCESS","identitycard_front":"certification/20190330/IMG1553876999925.png","identitycard_number":"158695869589658586","identitycard_reverse":"certification/20190330/IMG1553877004884.png","identitycard_hand":"certification/20190330/IMG1553877009404.png","real_name":"萝莉控","remark":null}}
     * serverTime : 1554299280122
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean implements Serializable {
        /**
         * bailMoneyRecord_status : SUCCESS
         * bailMoneyRecord_description : 已成功缴纳保证金
         * host : http://111.230.242.115:8888/static/
         * certification_status : SUCCESS
         * certification_description : 已完成身份认证
         * certification : {"id":3,"createTime":"2019-03-30 00:30:32","deleted":false,"userId":18,"status":"SUCCESS","identitycard_front":"certification/20190330/IMG1553876999925.png","identitycard_number":"158695869589658586","identitycard_reverse":"certification/20190330/IMG1553877004884.png","identitycard_hand":"certification/20190330/IMG1553877009404.png","real_name":"萝莉控","remark":null}
         */

        public String bailMoneyRecord_status;
        public String bailMoneyRecord_description;
        public String host;
        public String certification_status;
        public String certification_description;
        public CertificationBean certification;

        public class CertificationBean implements Serializable {
            /**
             * id : 3
             * createTime : 2019-03-30 00:30:32
             * deleted : false
             * userId : 18
             * status : SUCCESS
             * identitycard_front : certification/20190330/IMG1553876999925.png
             * identitycard_number : 158695869589658586
             * identitycard_reverse : certification/20190330/IMG1553877004884.png
             * identitycard_hand : certification/20190330/IMG1553877009404.png
             * real_name : 萝莉控
             * remark : null
             */

            public int id;
            public String createTime;
            public boolean deleted;
            public int userId;
            public String status;
            public String identitycard_front;
            public String identitycard_number;
            public String identitycard_reverse;
            public String identitycard_hand;
            public String real_name;
            public Object remark;
        }
    }
}
