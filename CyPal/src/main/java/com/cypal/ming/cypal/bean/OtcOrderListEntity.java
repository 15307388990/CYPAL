package com.cypal.ming.cypal.bean;

import java.util.List;

public class OtcOrderListEntity {

    /**
     * code : 1
     * msg : success
     * data : {"content":[{"id":268,"createTime":"2019-03-25 23:46:00","deleted":false,"orderNo":"OTC201903252346003471206","outOrderNo":"12345678910","status":"D_SUCCESS","statusDesc":"水军确认收款,等待回调","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 23:46:03","closeTime":null,"completeTime":null,"successTime":"2019-03-25 23:53:24","payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"yDdjzx","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":10,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"},{"id":266,"createTime":"2019-03-25 23:42:07","deleted":false,"orderNo":"OTC201903252342075233292","outOrderNo":"12345678910","status":"D_SUCCESS","statusDesc":"水军确认收款,等待回调","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 23:42:10","closeTime":null,"completeTime":null,"successTime":"2019-03-25 23:45:13","payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"ppgvEe","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":10,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"},{"id":264,"createTime":"2019-03-25 23:26:54","deleted":false,"orderNo":"OTC201903252326542930608","outOrderNo":"12345678910","status":"D_SUCCESS","statusDesc":"水军确认收款,等待回调","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 23:26:56","closeTime":null,"completeTime":null,"successTime":"2019-03-25 23:36:56","payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"R1pW8Z","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":10,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"},{"id":212,"createTime":"2019-03-25 20:01:36","deleted":false,"orderNo":"OTC201903252001360304629","outOrderNo":"12345678910","status":"D_SUCCESS","statusDesc":"水军确认收款,等待回调","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 20:03:04","closeTime":null,"completeTime":null,"successTime":"2019-03-25 23:20:21","payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"9naMFH","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":10,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":4,"totalPages":1,"first":false,"numberOfElements":4,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
     * serverTime : 1553659315387
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * content : [{"id":268,"createTime":"2019-03-25 23:46:00","deleted":false,"orderNo":"OTC201903252346003471206","outOrderNo":"12345678910","status":"D_SUCCESS","statusDesc":"水军确认收款,等待回调","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 23:46:03","closeTime":null,"completeTime":null,"successTime":"2019-03-25 23:53:24","payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"yDdjzx","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":10,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"},{"id":266,"createTime":"2019-03-25 23:42:07","deleted":false,"orderNo":"OTC201903252342075233292","outOrderNo":"12345678910","status":"D_SUCCESS","statusDesc":"水军确认收款,等待回调","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 23:42:10","closeTime":null,"completeTime":null,"successTime":"2019-03-25 23:45:13","payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"ppgvEe","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":10,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"},{"id":264,"createTime":"2019-03-25 23:26:54","deleted":false,"orderNo":"OTC201903252326542930608","outOrderNo":"12345678910","status":"D_SUCCESS","statusDesc":"水军确认收款,等待回调","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 23:26:56","closeTime":null,"completeTime":null,"successTime":"2019-03-25 23:36:56","payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"R1pW8Z","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":10,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"},{"id":212,"createTime":"2019-03-25 20:01:36","deleted":false,"orderNo":"OTC201903252001360304629","outOrderNo":"12345678910","status":"D_SUCCESS","statusDesc":"水军确认收款,等待回调","amount":100,"userId":15,"merchantUserId":2,"takeTime":"2019-03-25 20:03:04","closeTime":null,"completeTime":null,"successTime":"2019-03-25 23:20:21","payType":"WXPAY","payAccountJson":"{\"accountData\":\"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J\",\"accountName\":\"1334\",\"accountType\":\"WXPAY\",\"createTime\":1553349811000,\"deleted\":false,\"id\":12,\"realName\":\"孔明灯\",\"used\":true,\"userId\":15}","userUnqueNo":"9naMFH","orderDesc":"演示支付","remark":null,"attach":"","notifyCount":10,"returnUrl":"http://111.230.242.115:8888/","notifyUrl":"http://111.230.242.115:8888/notify"}]
         * pageable : {"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
         * number : 1
         * totalElements : 4
         * totalPages : 1
         * first : false
         * numberOfElements : 4
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
             * id : 268
             * createTime : 2019-03-25 23:46:00
             * deleted : false
             * orderNo : OTC201903252346003471206
             * outOrderNo : 12345678910
             * status : D_SUCCESS
             * statusDesc : 水军确认收款,等待回调
             * amount : 100
             * userId : 15
             * merchantUserId : 2
             * takeTime : 2019-03-25 23:46:03
             * closeTime : null
             * completeTime : null
             * successTime : 2019-03-25 23:53:24
             * payType : WXPAY
             * payAccountJson : {"accountData":"wxp://f2f0iILS_3Cn5d_AfCCdl5VtsbqIZ-w5ph6J","accountName":"1334","accountType":"WXPAY","createTime":1553349811000,"deleted":false,"id":12,"realName":"孔明灯","used":true,"userId":15}
             * userUnqueNo : yDdjzx
             * orderDesc : 演示支付
             * remark : null
             * attach :
             * notifyCount : 10
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
            public String successTime;
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
}
