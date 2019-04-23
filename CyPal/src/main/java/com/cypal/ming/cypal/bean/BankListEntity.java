package com.cypal.ming.cypal.bean;

import java.util.List;

public class BankListEntity {


    /**
     * code : 1
     * msg : success
     * data : {"bankCards":[{"id":13,"createTime":"2019-03-31 18:41:21","deleted":false,"userId":4,"accountName":"中国工商银行","realName":"cxk","accountType":"BANKCARD","accountData":"11230558673115","payAccountCodeList":[],"used":true}],"bankLists":["中国农业银行","重庆银行","广发银行","湖北银行","网商银行","长沙银行","交通银行","杭州银行","浙商银行","北京银行","中国银行","宁夏银行","招商银行","中信银行","平安银行","华融湘江银行","兴业银行","华夏银行","成都银行","中国邮政储蓄银行","中国光大银行","中国建设银行","上海银行","广州银行","宁波银行","北京农商行","江苏银行","中国民生银行","湖南省农村信用社","厦门银行","西安银行","中国工商银行","哈尔滨银行","大连银行","浦发银行","河北银行","南京银行"]}
     * serverTime : 1555910450361
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        public List<BankCardsBean> bankCards;
        public List<String> bankLists;

        public class BankCardsBean {
            /**
             * id : 13
             * createTime : 2019-03-31 18:41:21
             * deleted : false
             * userId : 4
             * accountName : 中国工商银行
             * realName : cxk
             * accountType : BANKCARD
             * accountData : 11230558673115
             * payAccountCodeList : []
             * used : true
             */

            public int id;
            public String createTime;
            public boolean deleted;
            public int userId;
            public String accountName;
            public String realName;
            public String accountType;
            public String accountData;
            public boolean used;
            public List<?> payAccountCodeList;
        }
    }
}
