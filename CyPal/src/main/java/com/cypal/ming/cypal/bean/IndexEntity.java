package com.cypal.ming.cypal.bean;

import java.util.List;

public class IndexEntity {


    /**
     * code : 1
     * msg : success
     * data : {"noticeList":[{"id":2,"createTime":"2019-03-10 15:07:07","deleted":false,"title":"第二天公告","summary":"aaa","content":null,"publish_userId":1,"publish_user":null},{"id":1,"createTime":"2019-03-10 03:50:47","deleted":false,"title":"公告功能测试","summary":"测试公告","content":null,"publish_userId":1,"publish_user":null}],"usedPayAccount":[],"balance":1200,"undoOrder":{"content":[],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":0,"totalPages":0,"first":false,"numberOfElements":0,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10},"successRateText":"","indexTodayOrderAnalysisResp":{"todaySuccess":0,"todaySuccessMoney":0,"todayCommision":0},"otc":{"id":4,"createTime":"2019-03-18 20:15:23","deleted":false,"userId":3,"otcType":null,"commision":0,"startTime":null,"start":false}}
     * serverTime : 1553001212040
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
         * noticeList : [{"id":2,"createTime":"2019-03-10 15:07:07","deleted":false,"title":"第二天公告","summary":"aaa","content":null,"publish_userId":1,"publish_user":null},{"id":1,"createTime":"2019-03-10 03:50:47","deleted":false,"title":"公告功能测试","summary":"测试公告","content":null,"publish_userId":1,"publish_user":null}]
         * usedPayAccount : []
         * balance : 1200.0
         * undoOrder : {"content":[],"pageable":{"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"number":1,"totalElements":0,"totalPages":0,"first":false,"numberOfElements":0,"last":true,"sort":{"sorted":false,"unsorted":true},"size":10}
         * successRateText :
         * indexTodayOrderAnalysisResp : {"todaySuccess":0,"todaySuccessMoney":0,"todayCommision":0}
         * otc : {"id":4,"createTime":"2019-03-18 20:15:23","deleted":false,"userId":3,"otcType":null,"commision":0,"startTime":null,"start":false}
         */

        private double balance;
        private UndoOrderBean undoOrder;
        private String successRateText;
        private IndexTodayOrderAnalysisRespBean indexTodayOrderAnalysisResp;
        private OtcBean otc;
        private List<NoticeListBean> noticeList;
        private String usedPayAccount;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public UndoOrderBean getUndoOrder() {
            return undoOrder;
        }

        public void setUndoOrder(UndoOrderBean undoOrder) {
            this.undoOrder = undoOrder;
        }

        public String getSuccessRateText() {
            return successRateText;
        }

        public void setSuccessRateText(String successRateText) {
            this.successRateText = successRateText;
        }

        public IndexTodayOrderAnalysisRespBean getIndexTodayOrderAnalysisResp() {
            return indexTodayOrderAnalysisResp;
        }

        public void setIndexTodayOrderAnalysisResp(IndexTodayOrderAnalysisRespBean indexTodayOrderAnalysisResp) {
            this.indexTodayOrderAnalysisResp = indexTodayOrderAnalysisResp;
        }

        public OtcBean getOtc() {
            return otc;
        }

        public void setOtc(OtcBean otc) {
            this.otc = otc;
        }

        public List<NoticeListBean> getNoticeList() {
            return noticeList;
        }

        public void setNoticeList(List<NoticeListBean> noticeList) {
            this.noticeList = noticeList;
        }

        public String getUsedPayAccount() {
            return usedPayAccount;
        }

        public void setUsedPayAccount(String usedPayAccount) {
            this.usedPayAccount = usedPayAccount;
        }

        public class UndoOrderBean {
            /**
             * content : []
             * pageable : {"sort":{"sorted":false,"unsorted":true},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
             * number : 1
             * totalElements : 0
             * totalPages : 0
             * first : false
             * numberOfElements : 0
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
            private List<?> content;

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

            public List<?> getContent() {
                return content;
            }

            public void setContent(List<?> content) {
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
        }

        public class IndexTodayOrderAnalysisRespBean {
            /**
             * todaySuccess : 0
             * todaySuccessMoney : 0
             * todayCommision : 0
             */

            private int todaySuccess;
            private int todaySuccessMoney;
            private int todayCommision;

            public int getTodaySuccess() {
                return todaySuccess;
            }

            public void setTodaySuccess(int todaySuccess) {
                this.todaySuccess = todaySuccess;
            }

            public int getTodaySuccessMoney() {
                return todaySuccessMoney;
            }

            public void setTodaySuccessMoney(int todaySuccessMoney) {
                this.todaySuccessMoney = todaySuccessMoney;
            }

            public int getTodayCommision() {
                return todayCommision;
            }

            public void setTodayCommision(int todayCommision) {
                this.todayCommision = todayCommision;
            }
        }

        public class OtcBean {
            /**
             * id : 4
             * createTime : 2019-03-18 20:15:23
             * deleted : false
             * userId : 3
             * otcType : null
             * commision : 0.0
             * startTime : null
             * start : false
             */

            private int id;
            private String createTime;
            private boolean deleted;
            private int userId;
            private Object otcType;
            private double commision;
            private String startTime;
            private boolean start;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public Object getOtcType() {
                return otcType;
            }

            public void setOtcType(Object otcType) {
                this.otcType = otcType;
            }

            public double getCommision() {
                return commision;
            }

            public void setCommision(double commision) {
                this.commision = commision;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public boolean isStart() {
                return start;
            }

            public void setStart(boolean start) {
                this.start = start;
            }
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

            private int id;
            private String createTime;
            private boolean deleted;
            private String title;
            private String summary;
            private Object content;
            private int publish_userId;
            private Object publish_user;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public int getPublish_userId() {
                return publish_userId;
            }

            public void setPublish_userId(int publish_userId) {
                this.publish_userId = publish_userId;
            }

            public Object getPublish_user() {
                return publish_user;
            }

            public void setPublish_user(Object publish_user) {
                this.publish_user = publish_user;
            }
        }
    }
}
