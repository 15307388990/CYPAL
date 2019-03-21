package com.cypal.ming.cypal.bean;

import java.util.List;

public class TopUpEntity {

    /**
     * code : 1
     * msg : success
     * data : {"content":[{"rwpId":1,"amount":98800,"minLimit":100,"payAccountJson":"[{\"accountType\":\"WXPAY\"}]","avatar":null,"nickName":"超级管理员","successCount":0}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":1,"totalPages":1,"first":false,"numberOfElements":1,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
     * serverTime : 1553094023400
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * content : [{"rwpId":1,"amount":98800,"minLimit":100,"payAccountJson":"[{\"accountType\":\"WXPAY\"}]","avatar":null,"nickName":"超级管理员","successCount":0}]
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
             * rwpId : 1
             * amount : 98800
             * minLimit : 100
             * payAccountJson : [{"accountType":"WXPAY"}]
             * avatar : null
             * nickName : 超级管理员
             * successCount : 0
             */

            public int rwpId;
            public int amount;
            public int minLimit;
            public String payAccountJson;
            public String avatar;
            public String nickName;
            public int successCount;
        }
    }
}
