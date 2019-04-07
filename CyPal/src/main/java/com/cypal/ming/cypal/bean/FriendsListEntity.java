package com.cypal.ming.cypal.bean;

import java.util.List;

public class FriendsListEntity {

    /**
     * code : 1
     * msg : success
     * data : {"Bcount":0,"team":[{"id":32,"account":"15307388999","avatar":null,"invite_user_id":4,"createTime":"2019-04-06 01:43:11"},{"id":31,"account":"15307388995","avatar":null,"invite_user_id":4,"createTime":"2019-04-05 23:32:44"},{"id":30,"account":"15307388994","avatar":null,"invite_user_id":4,"createTime":"2019-04-05 23:28:39"},{"id":29,"account":"15307388993","avatar":null,"invite_user_id":4,"createTime":"2019-04-05 23:25:11"},{"id":27,"account":"18566622070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg","invite_user_id":4,"createTime":"2019-04-01 03:35:28"},{"id":18,"account":"15307388991","avatar":"http://111.230.242.115:8888//avatar/20190406/IMG1554554715430.png","invite_user_id":4,"createTime":"2019-03-29 21:43:13"},{"id":8,"account":"18566655270","avatar":null,"invite_user_id":4,"createTime":"2019-03-28 17:19:37"}],"Acount":7}
     * serverTime : 1554619704546
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * Bcount : 0
         * team : [{"id":32,"account":"15307388999","avatar":null,"invite_user_id":4,"createTime":"2019-04-06 01:43:11"},{"id":31,"account":"15307388995","avatar":null,"invite_user_id":4,"createTime":"2019-04-05 23:32:44"},{"id":30,"account":"15307388994","avatar":null,"invite_user_id":4,"createTime":"2019-04-05 23:28:39"},{"id":29,"account":"15307388993","avatar":null,"invite_user_id":4,"createTime":"2019-04-05 23:25:11"},{"id":27,"account":"18566622070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg","invite_user_id":4,"createTime":"2019-04-01 03:35:28"},{"id":18,"account":"15307388991","avatar":"http://111.230.242.115:8888//avatar/20190406/IMG1554554715430.png","invite_user_id":4,"createTime":"2019-03-29 21:43:13"},{"id":8,"account":"18566655270","avatar":null,"invite_user_id":4,"createTime":"2019-03-28 17:19:37"}]
         * Acount : 7
         */

        public int Bcount;
        public int Acount;
        public List<TeamBean> team;

        public class TeamBean {
            /**
             * id : 32
             * account : 15307388999
             * avatar : null
             * invite_user_id : 4
             * createTime : 2019-04-06 01:43:11
             */

            public int id;
            public String account;
            public String avatar;
            public int invite_user_id;
            public String createTime;
        }
    }
}
