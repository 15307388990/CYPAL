package com.fmt.ming.paotui.bean;

public class CityModel {
	private int id;
	private long create_date;
	private boolean deleted;
	private String province;
	private int provice_code;
	private String city_name;
	private String city_code;
	private String sort;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCreate_date() {
		return create_date;
	}

	public void setCreate_date(long create_date) {
		this.create_date = create_date;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getProvice_code() {
		return provice_code;
	}

	public void setProvice_code(int provice_code) {
		this.provice_code = provice_code;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
