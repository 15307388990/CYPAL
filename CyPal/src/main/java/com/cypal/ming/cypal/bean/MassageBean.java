/**
 *
 */
package com.cypal.ming.cypal.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author luoming
 * @Date 2018/11/16 1:52 PM
 */
public class MassageBean implements Serializable {


    /**
     * code : 1
     * msg : success
     * data : {"content":[{"id":49,"createTime":"2019-04-23 22:58:41","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":44,"createTime":"2019-04-23 01:08:33","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":43,"createTime":"2019-04-23 00:27:37","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":42,"createTime":"2019-04-22 23:57:47","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":32,"createTime":"2019-04-22 23:47:39","deleted":false,"userId":18,"title":"充值到账通知","content":"尊敬的会员,您充值的100元现已成功到账,赶紧去接单吧。"},{"id":31,"createTime":"2019-04-22 23:42:20","deleted":false,"userId":18,"title":"充值到账通知","content":"尊敬的会员,您充值的100元现已成功到账,赶紧去接单吧。"},{"id":30,"createTime":"2019-04-22 23:41:28","deleted":false,"userId":18,"title":"充值到账通知","content":"尊敬的会员,您充值的100元现已成功到账,赶紧去接单吧。"},{"id":29,"createTime":"2019-04-22 23:41:06","deleted":false,"userId":18,"title":"充值到账通知","content":"尊敬的会员,您充值的100元现已成功到账,赶紧去接单吧。"},{"id":28,"createTime":"2019-04-22 23:39:49","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":27,"createTime":"2019-04-22 23:37:57","deleted":false,"userId":18,"title":"自动接单","content":"恭喜您自动接单100元,收到款请及时确认，如金额不符请联系客服，未收到款请十分钟后点击申诉。"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":18,"totalPages":2,"first":false,"numberOfElements":10,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
     * serverTime : 1556031526655
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public class DataBean {
        /**
         * content : [{"id":49,"createTime":"2019-04-23 22:58:41","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":44,"createTime":"2019-04-23 01:08:33","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":43,"createTime":"2019-04-23 00:27:37","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":42,"createTime":"2019-04-22 23:57:47","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":32,"createTime":"2019-04-22 23:47:39","deleted":false,"userId":18,"title":"充值到账通知","content":"尊敬的会员,您充值的100元现已成功到账,赶紧去接单吧。"},{"id":31,"createTime":"2019-04-22 23:42:20","deleted":false,"userId":18,"title":"充值到账通知","content":"尊敬的会员,您充值的100元现已成功到账,赶紧去接单吧。"},{"id":30,"createTime":"2019-04-22 23:41:28","deleted":false,"userId":18,"title":"充值到账通知","content":"尊敬的会员,您充值的100元现已成功到账,赶紧去接单吧。"},{"id":29,"createTime":"2019-04-22 23:41:06","deleted":false,"userId":18,"title":"充值到账通知","content":"尊敬的会员,您充值的100元现已成功到账,赶紧去接单吧。"},{"id":28,"createTime":"2019-04-22 23:39:49","deleted":false,"userId":18,"title":"转账到账通知","content":"成功收到一笔转账,金额100元"},{"id":27,"createTime":"2019-04-22 23:37:57","deleted":false,"userId":18,"title":"自动接单","content":"恭喜您自动接单100元,收到款请及时确认，如金额不符请联系客服，未收到款请十分钟后点击申诉。"}]
         * pageable : {"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
         * number : 1
         * totalElements : 18
         * totalPages : 2
         * first : false
         * numberOfElements : 10
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
             * id : 49
             * createTime : 2019-04-23 22:58:41
             * deleted : false
             * userId : 18
             * title : 转账到账通知
             * content : 成功收到一笔转账,金额100元
             */

            public int id;
            public String createTime;
            public boolean deleted;
            public int userId;
            public String title;
            public String content;
        }
    }
}