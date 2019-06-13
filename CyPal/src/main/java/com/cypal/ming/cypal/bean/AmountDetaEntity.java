package com.cypal.ming.cypal.bean;

import java.util.List;

public class AmountDetaEntity {


    /**
     * code : 1
     * msg : success
     * data : {"content":[{"title":"转账收入","logEnum":"TRANSFER_INCOME","createTime":"2019-06-01 12:26:22","amount":444,"balance":17063,"description":"13777777777给你转账收入"},{"title":"转账收入","logEnum":"TRANSFER_INCOME","createTime":"2019-06-01 12:20:19","amount":300,"balance":16619,"description":"137777777771377777777713777777777给你转账收入"},{"title":"佣金提现","logEnum":"COMMISION_WITHDRAW","createTime":"2019-05-31 15:33:41","amount":120,"balance":16319,"description":"佣金提现转入"},{"title":"转账收入","logEnum":"TRANSFER_INCOME","createTime":"2019-05-31 00:17:41","amount":9999,"balance":16199,"description":"13777777777给你转账收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:46:33","amount":-1000,"balance":6200,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:45:13","amount":-1000,"balance":7200,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:43:44","amount":-1000,"balance":8200,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:39:09","amount":-500,"balance":9200,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:30:03","amount":-300,"balance":9700,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:12:00","amount":-300,"balance":11811,"description":"商户订单成交收入"}],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":27,"totalPages":3,"first":false,"numberOfElements":10,"last":false,"sort":{"sorted":false,"unsorted":true},"size":10}
     * serverTime : 1560444092167
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

    public class DataBean {
        /**
         * content : [{"title":"转账收入","logEnum":"TRANSFER_INCOME","createTime":"2019-06-01 12:26:22","amount":444,"balance":17063,"description":"13777777777给你转账收入"},{"title":"转账收入","logEnum":"TRANSFER_INCOME","createTime":"2019-06-01 12:20:19","amount":300,"balance":16619,"description":"137777777771377777777713777777777给你转账收入"},{"title":"佣金提现","logEnum":"COMMISION_WITHDRAW","createTime":"2019-05-31 15:33:41","amount":120,"balance":16319,"description":"佣金提现转入"},{"title":"转账收入","logEnum":"TRANSFER_INCOME","createTime":"2019-05-31 00:17:41","amount":9999,"balance":16199,"description":"13777777777给你转账收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:46:33","amount":-1000,"balance":6200,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:45:13","amount":-1000,"balance":7200,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:43:44","amount":-1000,"balance":8200,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:39:09","amount":-500,"balance":9200,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:30:03","amount":-300,"balance":9700,"description":"商户订单成交收入"},{"title":"商户订单成交收入","logEnum":"MERCHANT","createTime":"2019-05-27 15:12:00","amount":-300,"balance":11811,"description":"商户订单成交收入"}]
         * pageable : {"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
         * number : 1
         * totalElements : 27
         * totalPages : 3
         * first : false
         * numberOfElements : 10
         * last : false
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

        public class PageableBean {
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

            public class SortBean {
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

        public class SortBeanX {
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

        public class ContentBean {
            /**
             * title : 转账收入
             * logEnum : TRANSFER_INCOME
             * createTime : 2019-06-01 12:26:22
             * amount : 444.0
             * balance : 17063.0
             * description : 13777777777给你转账收入
             */

            private String title;
            private String logEnum;
            private String createTime;
            private double amount;
            private double balance;
            private String description;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLogEnum() {
                return logEnum;
            }

            public void setLogEnum(String logEnum) {
                this.logEnum = logEnum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
