package com.cypal.ming.cypal.bean;

import java.util.List;

public class TransferRecordEntity {

    /**
     * code : 1
     * msg : success
     * data : {"content":[{"id":7,"createTime":"2019-04-07 16:59:25","deleted":false,"userId":4,"money":100,"incomeUserId":18,"incomeAccount":"15307388991","incomeNickName":"153****8991","avatar":"http://111.230.242.115:8888//avatar/20190406/IMG1554554715430.png"},{"id":6,"createTime":"2019-04-03 17:03:45","deleted":false,"userId":4,"money":100,"incomeUserId":27,"incomeAccount":"18566622070","incomeNickName":"185****2070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg"},{"id":5,"createTime":"2019-04-03 17:02:50","deleted":false,"userId":4,"money":100,"incomeUserId":27,"incomeAccount":"18566622070","incomeNickName":"185****2070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg"},{"id":4,"createTime":"2019-04-01 10:07:31","deleted":false,"userId":4,"money":120,"incomeUserId":27,"incomeAccount":"18566622070","incomeNickName":"185****2070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg"},{"id":3,"createTime":"2019-04-01 09:59:39","deleted":false,"userId":4,"money":100.01,"incomeUserId":27,"incomeAccount":"18566622070","incomeNickName":"185****2070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg"},{"id":2,"createTime":"2019-04-01 02:21:49","deleted":false,"userId":4,"money":100.1,"incomeUserId":8,"incomeAccount":"18566655270","incomeNickName":"185****5270","avatar":null},{"id":1,"createTime":"2019-03-28 17:24:57","deleted":false,"userId":4,"money":1000,"incomeUserId":8,"incomeAccount":"18566655270","incomeNickName":"185****5270","avatar":null}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":7,"totalPages":1,"first":false,"numberOfElements":7,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
     * serverTime : 1554629060021
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public  class DataBean {
        /**
         * content : [{"id":7,"createTime":"2019-04-07 16:59:25","deleted":false,"userId":4,"money":100,"incomeUserId":18,"incomeAccount":"15307388991","incomeNickName":"153****8991","avatar":"http://111.230.242.115:8888//avatar/20190406/IMG1554554715430.png"},{"id":6,"createTime":"2019-04-03 17:03:45","deleted":false,"userId":4,"money":100,"incomeUserId":27,"incomeAccount":"18566622070","incomeNickName":"185****2070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg"},{"id":5,"createTime":"2019-04-03 17:02:50","deleted":false,"userId":4,"money":100,"incomeUserId":27,"incomeAccount":"18566622070","incomeNickName":"185****2070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg"},{"id":4,"createTime":"2019-04-01 10:07:31","deleted":false,"userId":4,"money":120,"incomeUserId":27,"incomeAccount":"18566622070","incomeNickName":"185****2070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg"},{"id":3,"createTime":"2019-04-01 09:59:39","deleted":false,"userId":4,"money":100.01,"incomeUserId":27,"incomeAccount":"18566622070","incomeNickName":"185****2070","avatar":"http://111.230.242.115:8888//avatar/20190403/IMG1554294077805.jpg"},{"id":2,"createTime":"2019-04-01 02:21:49","deleted":false,"userId":4,"money":100.1,"incomeUserId":8,"incomeAccount":"18566655270","incomeNickName":"185****5270","avatar":null},{"id":1,"createTime":"2019-03-28 17:24:57","deleted":false,"userId":4,"money":1000,"incomeUserId":8,"incomeAccount":"18566655270","incomeNickName":"185****5270","avatar":null}]
         * pageable : {"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
         * number : 1
         * totalElements : 7
         * totalPages : 1
         * first : false
         * numberOfElements : 7
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
             * id : 7
             * createTime : 2019-04-07 16:59:25
             * deleted : false
             * userId : 4
             * money : 100.0
             * incomeUserId : 18
             * incomeAccount : 15307388991
             * incomeNickName : 153****8991
             * avatar : http://111.230.242.115:8888//avatar/20190406/IMG1554554715430.png
             */

            public int id;
            public String createTime;
            public boolean deleted;
            public int userId;
            public double money;
            public int incomeUserId;
            public String incomeAccount;
            public String incomeNickName;
            public String avatar;
        }
    }
}
