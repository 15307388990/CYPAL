package com.fmt.ming.paotui.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wood121 on 2017/12/11.
 */

public class SellListBean implements Serializable {

    private ArrayList<SellBean> list;
    private int totalCount;

    public ArrayList<SellBean> getList() {
        return list;
    }

    public void setList(ArrayList<SellBean> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public class SellBean implements Serializable {
        private String device_name;
        private int pay_price;
        private int pay_type;//1微信支付、2支付宝支付
        private String dateTime;//时间

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public int getPay_price() {
            return pay_price;
        }

        public void setPay_price(int pay_price) {
            this.pay_price = pay_price;
        }

        public int getPay_type() {
            return pay_type;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

    }
}
