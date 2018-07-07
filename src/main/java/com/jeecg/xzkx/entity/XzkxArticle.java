package com.jeecg.xzkx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：</b>XzkxArticle:<br>
 * 实体定义规则
 * 字段不允许存在基本类型，必须都是包装类型(因为基本类型有默认值)
 * 基本数据类型  包装类 byte Byte boolean Boolean short Short char Character int Integer long Long float Float double  Double 
 * @author p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
public class XzkxArticle implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	/**
	private String agentId;
	private String qianshou;
	private String extUrl;
	
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getLabel() {
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getQianshou() {
		return qianshou;
	}
	public void setQianshou(String qianshou) {
		this.qianshou = qianshou;
	}
	public String getExtUrl() {
		return extUrl;
	}
	public void setExtUrl(String extUrl) {
		this.extUrl = extUrl;
	}
}
