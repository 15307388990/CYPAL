/**
 *
 */
package com.fmt.ming.paotui.bean;

import java.io.Serializable;

/**
 * @version
 *
 */
@SuppressWarnings("serial")
public class StoreBean implements Serializable {


    /**
     * id : 1
     * mobile : 13500000001
     * name : test1
     * department : 生鲜部
     * avatar :
     * salary : 0.00
     * logined_ip : 61.129.8.250
     * logined_at : 2018-07-03 21:36:41
     * created_at : 0000-00-00 00:00:00
     * updated_at : 2018-07-03 21:36:41
     */

    private String id;
    private String mobile;
    private String name;
    private String department;
    private String avatar;
    private String salary;
    private String logined_ip;
    private String logined_at;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
