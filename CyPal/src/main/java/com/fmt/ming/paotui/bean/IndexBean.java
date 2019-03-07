/**
 *
 */
package com.fmt.ming.paotui.bean;

import java.util.List;

/**
 * 银行支行实体类
 */
public class IndexBean {


    private List<OrderStatisticsBean> orderStatistics;
    private int accumulate_goods_count; //   累计销售量
    private int accumulate_total_price; //累计销售总金额
    private int accumulate_order_count;//    累计订单数

    public int getAccumulate_goods_count() {
        return accumulate_goods_count;
    }

    public void setAccumulate_goods_count(int accumulate_goods_count) {
        this.accumulate_goods_count = accumulate_goods_count;
    }

    public int getAccumulate_total_price() {
        return accumulate_total_price;
    }

    public void setAccumulate_total_price(int accumulate_total_price) {
        this.accumulate_total_price = accumulate_total_price;
    }

    public int getAccumulate_order_count() {
        return accumulate_order_count;
    }

    public void setAccumulate_order_count(int accumulate_order_count) {
        this.accumulate_order_count = accumulate_order_count;
    }

    public List<OrderStatisticsBean> getOrderStatistics() {
        return orderStatistics;
    }

    public void setOrderStatistics(List<OrderStatisticsBean> orderStatistics) {
        this.orderStatistics = orderStatistics;
    }


    public static class OrderStatisticsBean {
        /**
         * total_price : 0
         * order_count : 0
         * day_time : 05-04
         */

        private int total_price;//销售额
        private int order_count;//订单数
        private String day_time;//日期

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public int getOrder_count() {
            return order_count;
        }

        public void setOrder_count(int order_count) {
            this.order_count = order_count;
        }

        public String getDay_time() {
            return day_time;
        }

        public void setDay_time(String day_time) {
            this.day_time = day_time;
        }
    }

}
