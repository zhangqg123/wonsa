package com.jeecg.xzkx.entity;

public class Depart {
	private String id;
	private String parentDepartId;
	private String departName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentDepartId() {
		return parentDepartId;
	}
	public void setParentDepartId(String parentDepartId) {
		this.parentDepartId = parentDepartId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
}
