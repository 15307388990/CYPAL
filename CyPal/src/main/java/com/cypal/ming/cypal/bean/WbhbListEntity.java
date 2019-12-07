package com.cypal.ming.cypal.bean;

import java.io.Serializable;
import java.util.List;

public class WbhbListEntity implements Serializable {

    /**
     * code : 1
     * msg : success
     * data : {"content":[{"orderNo":"SK201912062253540367967","amount":1,"pass":"241833","takeUrl":"http://47.244.184.128:8888/take/MjAxOTEyMDYyMjU0MDE4MjQ5MTIy"},{"orderNo":"SK201912062248047960690","amount":1,"pass":"942672","takeUrl":"http://47.244.184.128:8888/take/MjAxOTEyMDYyMjQ4MTYxMjQ1NDY2"},{"orderNo":"SK201912062244190754885","amount":1,"pass":"448942","takeUrl":"http://47.244.184.128:8888/take/MjAxOTEyMDYyMjQ0MzkwOTY4MDAw"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":3,"totalPages":1,"first":false,"numberOfElements":3,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
     * serverTime : 1575720686253
     */

    private int code;
    private String msg;
    private DataBean data;
    private long serverTime;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public static class DataBean {
        /**
         * content : [{"orderNo":"SK201912062253540367967","amount":1,"pass":"241833","takeUrl":"http://47.244.184.128:8888/take/MjAxOTEyMDYyMjU0MDE4MjQ5MTIy"},{"orderNo":"SK201912062248047960690","amount":1,"pass":"942672","takeUrl":"http://47.244.184.128:8888/take/MjAxOTEyMDYyMjQ4MTYxMjQ1NDY2"},{"orderNo":"SK201912062244190754885","amount":1,"pass":"448942","takeUrl":"http://47.244.184.128:8888/take/MjAxOTEyMDYyMjQ0MzkwOTY4MDAw"}]
         * pageable : {"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
         * number : 1
         * totalElements : 3
         * totalPages : 1
         * first : false
         * numberOfElements : 3
         * last : true
         * sort : {"sorted":false,"unsorted":true}
         * size : 10
         */

        private PageableBean pageable;
        private int number;
        private int totalElements;
        private int totalPages;
        private boolean first;
        private int numberOfElements;
        private boolean last;
        private SortBeanX sort;
        private int size;
        private List<ContentBean> content;

        public PageableBean getPageable() {
            return pageable;
        }

        public void setPageable(PageableBean pageable) {
            this.pageable = pageable;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public SortBeanX getSort() {
            return sort;
        }

        public void setSort(SortBeanX sort) {
            this.sort = sort;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class PageableBean {
            /**
             * sort : {"sorted":false,"unsorted":true}
             * pageSize : 10
             * pageNumber : 0
             * offset : 0
             * paged : true
             * unpaged : false
             */

            private SortBean sort;
            private int pageSize;
            private int pageNumber;
            private int offset;
            private boolean paged;
            private boolean unpaged;

            public SortBean getSort() {
                return sort;
            }

            public void setSort(SortBean sort) {
                this.sort = sort;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public boolean isPaged() {
                return paged;
            }

            public void setPaged(boolean paged) {
                this.paged = paged;
            }

            public boolean isUnpaged() {
                return unpaged;
            }

            public void setUnpaged(boolean unpaged) {
                this.unpaged = unpaged;
            }

            public static class SortBean {
                /**
                 * sorted : false
                 * unsorted : true
                 */

                private boolean sorted;
                private boolean unsorted;

                public boolean isSorted() {
                    return sorted;
                }

                public void setSorted(boolean sorted) {
                    this.sorted = sorted;
                }

                public boolean isUnsorted() {
                    return unsorted;
                }

                public void setUnsorted(boolean unsorted) {
                    this.unsorted = unsorted;
                }
            }
        }

        public static class SortBeanX {
            /**
             * sorted : false
             * unsorted : true
             */

            private boolean sorted;
            private boolean unsorted;

            public boolean isSorted() {
                return sorted;
            }

            public void setSorted(boolean sorted) {
                this.sorted = sorted;
            }

            public boolean isUnsorted() {
                return unsorted;
            }

            public void setUnsorted(boolean unsorted) {
                this.unsorted = unsorted;
            }
        }

        public static class ContentBean {
            /**
             * orderNo : SK201912062253540367967
             * amount : 1
             * pass : 241833
             * takeUrl : http://47.244.184.128:8888/take/MjAxOTEyMDYyMjU0MDE4MjQ5MTIy
             */

            private String orderNo;
            private int amount;
            private String pass;
            private String takeUrl;

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getPass() {
                return pass;
            }

            public void setPass(String pass) {
                this.pass = pass;
            }

            public String getTakeUrl() {
                return takeUrl;
            }

            public void setTakeUrl(String takeUrl) {
                this.takeUrl = takeUrl;
            }
        }
    }
}
