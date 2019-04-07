package com.cypal.ming.cypal.bean;

public class TransferEntity {


    /**
     * code : 1
     * msg : success
     * data : {"balance":3259.89,"friend":{"nickName":"153****8991","avatar":"http://111.230.242.115:8888/static/avatar/20190406/IMG1554554715430.png","account":"15307388991"}}
     * serverTime : 1554622666205
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * balance : 3259.89
         * friend : {"nickName":"153****8991","avatar":"http://111.230.242.115:8888/static/avatar/20190406/IMG1554554715430.png","account":"15307388991"}
         */

        public double balance;
        public FriendBean friend;

        public class FriendBean {
            /**
             * nickName : 153****8991
             * avatar : http://111.230.242.115:8888/static/avatar/20190406/IMG1554554715430.png
             * account : 15307388991
             */

            public String nickName;
            public String avatar;
            public String account;
        }
    }
}
