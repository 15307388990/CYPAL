package com.cypal.ming.cypal.bean;

import java.util.List;

public class IndexEntity {


    /**
     * code : 1
     * msg : success
     * data : {"noticeList":[{"id":2,"createTime":"2019-03-29 23:33:43","deleted":false,"title":"1","summary":"2","content":null,"publish_userId":1,"publish_user":null}],"usedPayAccount":["CLOUDPAY","WXPAY","ALIPAY"],"balance":4214,"undoOrder":{"content":[{"id":239,"createTime":"2019-04-02 21:15:56","deleted":false,"orderNo":"OTC201904022115563253874","outOrderNo":null,"status":"A_PROCESS","statusDesc":"新订单生成,等待支付","amount":195,"userId":null,"merchantUserId":null,"takeTime":"2019-04-02 21:15:57","closeTime":null,"completeTime":null,"successTime":null,"payType":"WXPAY","payAccountJson":null,"userUnqueNo":null,"orderDesc":null,"remark":null,"attach":null,"notifyCount":0,"returnUrl":null,"notifyUrl":null}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":1,"totalPages":1,"first":false,"numberOfElements":1,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10},"successRateText":"当前平台成功率0.00%,分佣比例0.9%","indexTodayOrderAnalysisResp":{"todaySuccess":0,"todaySuccessMoney":0,"todayCommision":0},"version":{"id":3,"createTime":"2019-03-24 22:17:26","deleted":false,"versionId":2020,"versionName":"2.0.10","updateType":0,"updateUrl":"https://img.cypal.cn/Fidak_v1.8.17_20190228_release.apk","updateContext":"测试版本升级","platformType":"android","lastUpdateTime":"2019-04-06 10:53:22"},"otc":{"id":9,"createTime":"2019-03-29 21:44:16","deleted":false,"userId":18,"otcType":"HAND","commision":4608.964,"startTime":"2019-04-02 20:34:36","start":true}}
     * serverTime : 1554634080526
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * noticeList : [{"id":2,"createTime":"2019-03-29 23:33:43","deleted":false,"title":"1","summary":"2","content":null,"publish_userId":1,"publish_user":null}]
         * usedPayAccount : ["CLOUDPAY","WXPAY","ALIPAY"]
         * balance : 4214.0
         * undoOrder : {"content":[{"id":239,"createTime":"2019-04-02 21:15:56","deleted":false,"orderNo":"OTC201904022115563253874","outOrderNo":null,"status":"A_PROCESS","statusDesc":"新订单生成,等待支付","amount":195,"userId":null,"merchantUserId":null,"takeTime":"2019-04-02 21:15:57","closeTime":null,"completeTime":null,"successTime":null,"payType":"WXPAY","payAccountJson":null,"userUnqueNo":null,"orderDesc":null,"remark":null,"attach":null,"notifyCount":0,"returnUrl":null,"notifyUrl":null}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":1,"totalPages":1,"first":false,"numberOfElements":1,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
         * successRateText : 当前平台成功率0.00%,分佣比例0.9%
         * indexTodayOrderAnalysisResp : {"todaySuccess":0,"todaySuccessMoney":0,"todayCommision":0}
         * version : {"id":3,"createTime":"2019-03-24 22:17:26","deleted":false,"versionId":2020,"versionName":"2.0.10","updateType":0,"updateUrl":"https://img.cypal.cn/Fidak_v1.8.17_20190228_release.apk","updateContext":"测试版本升级","platformType":"android","lastUpdateTime":"2019-04-06 10:53:22"}
         * otc : {"id":9,"createTime":"2019-03-29 21:44:16","deleted":false,"userId":18,"otcType":"HAND","commision":4608.964,"startTime":"2019-04-02 20:34:36","start":true}
         */

        public double balance;
        public UndoOrderBean undoOrder;
        public String successRateText;
        public IndexTodayOrderAnalysisRespBean indexTodayOrderAnalysisResp;
        public VersionBean version;
        public boolean hasPayPassword;
        public OtcBean otc;
        public List<NoticeListBean> noticeList;
        public String usedPayAccount;

        public class UndoOrderBean {
            /**
             * content : [{"id":239,"createTime":"2019-04-02 21:15:56","deleted":false,"orderNo":"OTC201904022115563253874","outOrderNo":null,"status":"A_PROCESS","statusDesc":"新订单生成,等待支付","amount":195,"userId":null,"merchantUserId":null,"takeTime":"2019-04-02 21:15:57","closeTime":null,"completeTime":null,"successTime":null,"payType":"WXPAY","payAccountJson":null,"userUnqueNo":null,"orderDesc":null,"remark":null,"attach":null,"notifyCount":0,"returnUrl":null,"notifyUrl":null}]
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
                 * id : 239
                 * createTime : 2019-04-02 21:15:56
                 * deleted : false
                 * orderNo : OTC201904022115563253874
                 * outOrderNo : null
                 * status : A_PROCESS
                 * statusDesc : 新订单生成,等待支付
                 * amount : 195
                 * userId : null
                 * merchantUserId : null
                 * takeTime : 2019-04-02 21:15:57
                 * closeTime : null
                 * completeTime : null
                 * successTime : null
                 * payType : WXPAY
                 * payAccountJson : null
                 * userUnqueNo : null
                 * orderDesc : null
                 * remark : null
                 * attach : null
                 * notifyCount : 0
                 * returnUrl : null
                 * notifyUrl : null
                 */

                public int id;
                public String createTime;
                public boolean deleted;
                public String orderNo;
                public Object outOrderNo;
                public String status;
                public String statusDesc;
                public int amount;
                public Object userId;
                public Object merchantUserId;
                public String takeTime;
                public Object closeTime;
                public Object completeTime;
                public Object successTime;
                public String payType;
                public Object payAccountJson;
                public Object userUnqueNo;
                public Object orderDesc;
                public Object remark;
                public Object attach;
                public int notifyCount;
                public Object returnUrl;
                public Object notifyUrl;
            }
        }

        public class IndexTodayOrderAnalysisRespBean {
            /**
             * todaySuccess : 0
             * todaySuccessMoney : 0
             * todayCommision : 0
             */

            public int todaySuccess;
            public double todaySuccessMoney;
            public double todayCommision;
        }

        public class VersionBean {
            /**
             * id : 3
             * createTime : 2019-03-24 22:17:26
             * deleted : false
             * versionId : 2020
             * versionName : 2.0.10
             * updateType : 0
             * updateUrl : https://img.cypal.cn/Fidak_v1.8.17_20190228_release.apk
             * updateContext : 测试版本升级
             * platformType : android
             * lastUpdateTime : 2019-04-06 10:53:22
             */

            public int id;
            public String createTime;
            public boolean deleted;
            public int versionId;
            public String versionName;
            public int updateType;
            public String updateUrl;
            public String updateContext;
            public String platformType;
            public String lastUpdateTime;
        }

        public class OtcBean {
            /**
             * id : 9
             * createTime : 2019-03-29 21:44:16
             * deleted : false
             * userId : 18
             * otcType : HAND
             * commision : 4608.964
             * startTime : 2019-04-02 20:34:36
             * start : true
             */

            public int id;
            public String createTime;
            public boolean deleted;
            public int userId;
            public String otcType;
            public double commision;
            public String startTime;
            public boolean start;
        }

        public class NoticeListBean {
            /**
             * id : 2
             * createTime : 2019-03-29 23:33:43
             * deleted : false
             * title : 1
             * summary : 2
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
