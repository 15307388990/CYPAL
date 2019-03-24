package com.cypal.ming.cypal.bean;

import java.util.List;

public class CommissionEntity {

    /**
     * code : 1
     * msg : success
     * data : {"myCommisionBalance":98500,"totalCommision":1200,"todayCommision":0,"todayTeamCommision":0,"list":{"content":[{"incomeUserId":15,"incomeMoney":1200,"commisionEnum":"OTC","amount":100,"payType":"WXPAY","createTime":"2019-03-22 00:08:09"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":1,"totalPages":1,"first":false,"numberOfElements":1,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}}
     * serverTime : 1553395532909
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public  class DataBean {
        /**
         * myCommisionBalance : 98500.0
         * totalCommision : 1200.0
         * todayCommision : 0.0
         * todayTeamCommision : 0.0
         * list : {"content":[{"incomeUserId":15,"incomeMoney":1200,"commisionEnum":"OTC","amount":100,"payType":"WXPAY","createTime":"2019-03-22 00:08:09"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":1,"totalPages":1,"first":false,"numberOfElements":1,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
         */

        public double myCommisionBalance;
        public double totalCommision;
        public double todayCommision;
        public double todayTeamCommision;
        public ListBean list;

        public  class ListBean {
            /**
             * content : [{"incomeUserId":15,"incomeMoney":1200,"commisionEnum":"OTC","amount":100,"payType":"WXPAY","createTime":"2019-03-22 00:08:09"}]
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

            public  class PageableBean {
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

                public  class SortBean {
                    /**
                     * sorted : false
                     * unsorted : true
                     */

                    public boolean sorted;
                    public boolean unsorted;
                }
            }

            public  class SortBeanX {
                /**
                 * sorted : false
                 * unsorted : true
                 */

                public boolean sorted;
                public boolean unsorted;
            }

            public  class ContentBean {
                /**
                 * incomeUserId : 15
                 * incomeMoney : 1200.0
                 * commisionEnum : OTC
                 * amount : 100
                 * payType : WXPAY
                 * createTime : 2019-03-22 00:08:09
                 */

                public int incomeUserId;
                public double incomeMoney;
                public String commisionEnum;
                public int amount;
                public String payType;
                public String createTime;
            }
        }
    }
}
