package com.cypal.ming.cypal.bean;

import java.util.List;

public class IndexEntity {


    /**
     * code : 1
     * msg : success
     * data : {"noticeList":[{"id":2,"createTime":"2019-03-10 15:07:07","deleted":false,"title":"第二天公告","summary":"aaa","content":null,"publish_userId":1,"publish_user":null},{"id":1,"createTime":"2019-03-10 03:50:47","deleted":false,"title":"公告功能测试","summary":"测试公告","content":null,"publish_userId":1,"publish_user":null}],"usedPayAccount":["WXPAY","CLOUDPAY"],"balance":998500,"undoOrder":{"content":[{"id":212,"createTime":"2019-03-25 20:01:36","deleted":false,"orderNo":"OTC201903252001360304629","outOrderNo":"12345678910","status":"A_PROCESS","statusDesc":"新订单生成,等待支付","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 20:03:04","closeTime":null,"completeTime":null,"successTime":null,"payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"9naMFH","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":0,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":1,"totalPages":1,"first":false,"numberOfElements":1,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10},"successRateText":"","indexTodayOrderAnalysisResp":{"todaySuccess":0,"todaySuccessMoney":0,"todayCommision":0},"otc":{"id":9,"createTime":"2019-03-21 10:32:49","deleted":false,"userId":15,"otcType":"HAND","commision":98500,"startTime":"2019-03-24 21:04:24","start":true}}
     * serverTime : 1553522256470
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * noticeList : [{"id":2,"createTime":"2019-03-10 15:07:07","deleted":false,"title":"第二天公告","summary":"aaa","content":null,"publish_userId":1,"publish_user":null},{"id":1,"createTime":"2019-03-10 03:50:47","deleted":false,"title":"公告功能测试","summary":"测试公告","content":null,"publish_userId":1,"publish_user":null}]
         * usedPayAccount : ["WXPAY","CLOUDPAY"]
         * balance : 998500
         * undoOrder : {"content":[{"id":212,"createTime":"2019-03-25 20:01:36","deleted":false,"orderNo":"OTC201903252001360304629","outOrderNo":"12345678910","status":"A_PROCESS","statusDesc":"新订单生成,等待支付","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 20:03:04","closeTime":null,"completeTime":null,"successTime":null,"payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"9naMFH","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":0,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":1,"totalPages":1,"first":false,"numberOfElements":1,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
         * successRateText :
         * indexTodayOrderAnalysisResp : {"todaySuccess":0,"todaySuccessMoney":0,"todayCommision":0}
         * otc : {"id":9,"createTime":"2019-03-21 10:32:49","deleted":false,"userId":15,"otcType":"HAND","commision":98500,"startTime":"2019-03-24 21:04:24","start":true}
         */

        public float balance;
        public UndoOrderBean undoOrder;
        public String successRateText;
        public IndexTodayOrderAnalysisRespBean indexTodayOrderAnalysisResp;
        public OtcBean otc;
        public List<NoticeListBean> noticeList;
        public String usedPayAccount;

        public class UndoOrderBean {
            /**
             * content : [{"id":212,"createTime":"2019-03-25 20:01:36","deleted":false,"orderNo":"OTC201903252001360304629","outOrderNo":"12345678910","status":"A_PROCESS","statusDesc":"新订单生成,等待支付","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 20:03:04","closeTime":null,"completeTime":null,"successTime":null,"payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"9naMFH","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":0,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"}]
             * pageable : {"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
             * number : 1
             * totalElements : 1
             * totalPages : 1
             * first : false
             * numberOfElements : 1
             * last : true
             * sort : {"sorted":false,"unsorted":true}
             * size : 10
             */

            public PageableBean pageable;
            public int number;
            public int totalElements;
            public int totalPages;
            public boolean first;
            public int numberOfElements;
            public boolean last;
            public SortBeanX sort;
            public int size;
            public List<ContentBean> content;

            public class PageableBean {
                /**
                 * sort : {"sorted":false,"unsorted":true}
                 * pageSize : 10
                 * pageNumber : 0
                 * offset : 0
                 * paged : true
                 * unpaged : false
                 */

                public SortBean sort;
                public int pageSize;
                public int pageNumber;
                public int offset;
                public boolean paged;
                public boolean unpaged;

                public class SortBean {
                    /**
                     * sorted : false
                     * unsorted : true
                     */

                    public boolean sorted;
                    public boolean unsorted;
                }
            }

            public class SortBeanX {
                /**
                 * sorted : false
                 * unsorted : true
                 */

                public boolean sorted;
                public boolean unsorted;
            }

            public class ContentBean {
                /**
                 * id : 212
                 * createTime : 2019-03-25 20:01:36
                 * deleted : false
                 * orderNo : OTC201903252001360304629
                 * outOrderNo : 12345678910
                 * status : A_PROCESS
                 * statusDesc : 新订单生成,等待支付
                 * amount : 100
                 * userId : 15
                 * merchantUserId : 2
                 * takeTime : 2019-03-25 20:03:04
                 * closeTime : null
                 * completeTime : null
                 * successTime : null
                 * payType : WXPAY
                 * payAccountJson : {"accountData":"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J","accountName":"1334","accountType":"WXPAY","createTime":1553349811000,"deleted":false,"id":12,"realName":"孔明灯","used":true,"userId":15}
                 * userUnqueNo : 9naMFH
                 * orderDesc : 演示支付
                 * remark : null
                 * attach :
                 * notifyCount : 0
                 * returnUrl : http://111.230.242.115:8888/
                 * notifyUrl : http://111.230.242.115:8888/notify
                 */

                public int id;
                public String createTime;
                public boolean deleted;
                public String orderNo;
                public String outOrderNo;
                public String status;
                public String statusDesc;
                public int amount;
                public int userId;
                public int merchantUserId;
                public String takeTime;
                public Object closeTime;
                public Object completeTime;
                public Object successTime;
                public String payType;
                public String payAccountJson;
                public String userUnqueNo;
                public String orderDesc;
                public Object remark;
                public String attach;
                public int notifyCount;
                public String returnUrl;
                public String notifyUrl;
            }
        }

        public class IndexTodayOrderAnalysisRespBean {
            /**
             * todaySuccess : 0
             * todaySuccessMoney : 0
             * todayCommision : 0
             */

            public int todaySuccess;
            public float todaySuccessMoney;
            public float todayCommision;
        }

        public class OtcBean {
            /**
             * id : 9
             * createTime : 2019-03-21 10:32:49
             * deleted : false
             * userId : 15
             * otcType : HAND
             * commision : 98500
             * startTime : 2019-03-24 21:04:24
             * start : true
             */

            public int id;
            public String createTime;
            public boolean deleted;
            public int userId;
            public String otcType;
            public int commision;
            public String startTime;
            public boolean start;
        }

        public class NoticeListBean {
            /**
             * id : 2
             * createTime : 2019-03-10 15:07:07
             * deleted : false
             * title : 第二天公告
             * summary : aaa
             * content : null
             * publish_userId : 1
             * publish_user : null
             */

            public int id;
            public String createTime;
            public boolean deleted;
            public String title;
            public String summary;
            public Object content;
            public int publish_userId;
            public Object publish_user;
        }
    }
}
