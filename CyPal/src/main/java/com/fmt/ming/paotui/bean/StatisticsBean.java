package com.fmt.ming.paotui.bean;

import java.io.Serializable;

/**
 * Created by wood121 on 2017/12/12.
 */

public class StatisticsBean implements Serializable {
    private int goodsCount; //今日销售量
    private int orderCount; //今日订单数
    private int priceSum;   //今日销售额

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(int priceSum) {
        this.priceSum = priceSum;
    }
}
