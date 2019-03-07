package com.fmt.ming.paotui.bean;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {


    /**
     * uuid : 20180703145046CP7sNN08440800
     * user_id : 1
     * rider_id : 1
     * seller_id : 1
     * otype : 0  otype 订单类型(0:外卖,1:配送,2:跑腿)
     * tprice : 100.00
     * freight : 5.00
     * bagging : 2.00
     * seller_addr : 广东省深圳市福田区京基冰河时代广场
     * seller_lon : 114.027290
     * seller_lat : 22.528650
     * user_addr : 广东省深圳市福田区下沙小学
     * user_lon : 114.023709
     * user_lat : 22.525718
     * user_mobile : 13500000001
     * user_name : 张三
     * distance : 659.39
     * remark : 少冰，少糖
     * dtime : 00:30:00
     * canceled_id : 0
     * canceled_remark :
     * status : 2
     * created_at : 2018-07-03 07:45:54
     * updated_at : 2018-07-10 15:21:29
     * deleted_at : null
     * goods : [{"uuid":"20180703145046CP7sNN08440801","order_uuid":"20180703145046CP7sNN08440800","good_id":"1","nums":"1","price":"30.00","good":{"id":"1","seller_id":"1","gcate_id":"1","name":"拿铁咖啡","price":"30.00","cover":"http://47.92.134.67:78//upload/20180708/1531045041ZfLOY7.jpg","content":"<p>正宗拿铁<\/p>","created_at":"2018-07-03 07:57:41","updated_at":"2018-07-08 10:17:25","deleted_at":null}}]
     */

    private String uuid;
    private String user_id;
    private String rider_id;
    private String seller_id;
    private String otype;
    private String tprice;
    private String freight;
    private String bagging;
    private String seller_addr;
    private String seller_lon;
    private String seller_lat;
    private String user_addr;
    private String user_lon;
    private String user_lat;
    private String user_mobile;
    private String user_name;
    private String distance;
    private String remark;
    private String dtime;
    private String canceled_id;
    private String canceled_remark;
    private String status;
    private String created_at;
    private String updated_at;
    private Object deleted_at;
    private String seller_tel;//商家电话

    public String getSeller_tel() {
        return seller_tel;
    }

    public void setSeller_tel(String seller_tel) {
        this.seller_tel = seller_tel;
    }


    private List<GoodsBean> goods;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRider_id() {
        return rider_id;
    }

    public void setRider_id(String rider_id) {
        this.rider_id = rider_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getBagging() {
        return bagging;
    }

    public void setBagging(String bagging) {
        this.bagging = bagging;
    }

    public String getSeller_addr() {
        return seller_addr;
    }

    public void setSeller_addr(String seller_addr) {
        this.seller_addr = seller_addr;
    }

    public String getSeller_lon() {
        return seller_lon;
    }

    public void setSeller_lon(String seller_lon) {
        this.seller_lon = seller_lon;
    }

    public String getSeller_lat() {
        return seller_lat;
    }

    public void setSeller_lat(String seller_lat) {
        this.seller_lat = seller_lat;
    }

    public String getUser_addr() {
        return user_addr;
    }

    public void setUser_addr(String user_addr) {
        this.user_addr = user_addr;
    }

    public String getUser_lon() {
        return user_lon;
    }

    public void setUser_lon(String user_lon) {
        this.user_lon = user_lon;
    }

    public String getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(String user_lat) {
        this.user_lat = user_lat;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }

    public String getCanceled_id() {
        return canceled_id;
    }

    public void setCanceled_id(String canceled_id) {
        this.canceled_id = canceled_id;
    }

    public String getCanceled_remark() {
        return canceled_remark;
    }

    public void setCanceled_remark(String canceled_remark) {
        this.canceled_remark = canceled_remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Object getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Object deleted_at) {
        this.deleted_at = deleted_at;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * uuid : 20180703145046CP7sNN08440801
         * order_uuid : 20180703145046CP7sNN08440800
         * good_id : 1
         * nums : 1
         * price : 30.00
         * good : {"id":"1","seller_id":"1","gcate_id":"1","name":"拿铁咖啡","price":"30.00","cover":"http://47.92.134.67:78//upload/20180708/1531045041ZfLOY7.jpg","content":"<p>正宗拿铁<\/p>","created_at":"2018-07-03 07:57:41","updated_at":"2018-07-08 10:17:25","deleted_at":null}
         */

        private String uuid;
        private String order_uuid;
        private String good_id;
        private String nums;
        private String price;
        private GoodBean good;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getOrder_uuid() {
            return order_uuid;
        }

        public void setOrder_uuid(String order_uuid) {
            this.order_uuid = order_uuid;
        }

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public GoodBean getGood() {
            return good;
        }

        public void setGood(GoodBean good) {
            this.good = good;
        }

        public static class GoodBean {
            /**
             * id : 1
             * seller_id : 1
             * gcate_id : 1
             * name : 拿铁咖啡
             * price : 30.00
             * cover : http://47.92.134.67:78//upload/20180708/1531045041ZfLOY7.jpg
             * content : <p>正宗拿铁</p>
             * created_at : 2018-07-03 07:57:41
             * updated_at : 2018-07-08 10:17:25
             * deleted_at : null
             */

            private String id;
            private String seller_id;
            private String gcate_id;
            private String name;
            private String price;
            private String cover;
            private String content;
            private String created_at;
            private String updated_at;
            private Object deleted_at;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }

            public String getGcate_id() {
                return gcate_id;
            }

            public void setGcate_id(String gcate_id) {
                this.gcate_id = gcate_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public Object getDeleted_at() {
                return deleted_at;
            }

            public void setDeleted_at(Object deleted_at) {
                this.deleted_at = deleted_at;
            }
        }
    }
}
