package com.cypal.ming.cypal.bean;

import java.util.List;

public class TopUpListEntity {


    /**
     * code : 1
     * msg : success
     * data : {"content":[{"id":4,"avatar":null,"nickName":"185****2068","createTime":"2019-03-24 23:00:30","amount":200,"statusEnum":"SUCCESS"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":1,"totalPages":1,"first":false,"numberOfElements":1,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
     * serverTime : 1553603054758
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * content : [{"id":4,"avatar":null,"nickName":"185****2068","createTime":"2019-03-24 23:00:30","amount":200,"statusEnum":"SUCCESS"}]
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
             * id : 4
             * avatar : null
             * nickName : 185****2068
             * createTime : 2019-03-24 23:00:30
             * amount : 200
             * statusEnum : SUCCESS
             */

            public int id;
            public String avatar;
            public String nickName;
            public String createTime;
            public int amount;
            public String statusEnum;
        }
    }
}
