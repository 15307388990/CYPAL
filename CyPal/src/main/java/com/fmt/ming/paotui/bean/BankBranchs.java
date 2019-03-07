/**
 *
 */
package com.fmt.ming.paotui.bean;

/**
 * 银行支行实体类
 */
public class BankBranchs {
    private int id;//
    private String bank_base_no;//
    private String parent_bank_no;
    private String parent_bank_name;
    private String branch_bank_no;
    private String branch_bank_name;
    private String city_code;
    private String branch_bank_address;

    public String getBranch_bank_address() {
        return branch_bank_address;
    }

    public void setBranch_bank_address(String branch_bank_address) {
        this.branch_bank_address = branch_bank_address;
    }

    private String postalcode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_base_no() {
        return bank_base_no;
    }

    public void setBank_base_no(String bank_base_no) {
        this.bank_base_no = bank_base_no;
    }

    public String getParent_bank_no() {
        return parent_bank_no;
    }

    public void setParent_bank_no(String parent_bank_no) {
        this.parent_bank_no = parent_bank_no;
    }

    public String getParent_bank_name() {
        return parent_bank_name;
    }

    public void setParent_bank_name(String parent_bank_name) {
        this.parent_bank_name = parent_bank_name;
    }

    public String getBranch_bank_no() {
        return branch_bank_no;
    }

    public void setBranch_bank_no(String branch_bank_no) {
        this.branch_bank_no = branch_bank_no;
    }

    public String getBranch_bank_name() {
        return branch_bank_name;
    }

    public void setBranch_bank_name(String branch_bank_name) {
        this.branch_bank_name = branch_bank_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }


    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
}
