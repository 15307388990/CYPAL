/**
 *
 */
package com.fmt.ming.paotui.bean;

import java.io.Serializable;

/**
 * @Author luoming
 * @Date 2018/11/16 1:52 PM
 */
public class UserBean implements Serializable {

    /**
     * id : 1
     * mobile : 13500000001
     * name : 王欣欣
     * sex : 1
     * department : 派送部
     * avatar : http://fmt.pengkeda.com//upload/20180801/1533117878853507.png
     * salary : 100.00
     * lon : 104.046133
     * lat : 30.626631
     * joined_at : 2018-07-04
     * is_work : 0
     * status : 0
     * logined_ip : 171.220.36.73
     * logined_at : 2018-09-12 15:36:18
     * created_at : 2018-07-04 07:10:18
     * updated_at : 2018-09-12 09:01:25
     * order_nums : 0
     * income : 0.0
     */

    private int id;
    private String mobile;
    private String name;
    private int sex;
    private String department;
    private String avatar;
    private String salary;
    private String lon;
    private String lat;
    private String joined_at;
    private int is_work;
    private int status;
    private String logined_ip;
    private String logined_at;
    private String created_at;
    private String updated_at;
    private int order_nums;
    private String income;
    private String mood;
    private int msg_noread_nums;

    public int getMsg_noread_nums() {
        return msg_noread_nums;
    }

    public void setMsg_noread_nums(int msg_noread_nums) {
        this.msg_noread_nums = msg_noread_nums;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getJoined_at() {
        return joined_at;
    }

    public void setJoined_at(String joined_at) {
        this.joined_at = joined_at;
    }

    public int getIs_work() {
        return is_work;
    }

    public void setIs_work(int is_work) {
        this.is_work = is_work;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogined_ip() {
        return logined_ip;
    }

    public void setLogined_ip(String logined_ip) {
        this.logined_ip = logined_ip;
    }

    public String getLogined_at() {
        return logined_at;
    }

    public void setLogined_at(String logined_at) {
        this.logined_at = logined_at;
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

    public int getOrder_nums() {
        return order_nums;
    }

    public void setOrder_nums(int order_nums) {
        this.order_nums = order_nums;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
