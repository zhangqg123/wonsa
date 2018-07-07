package com.jeecg.xzkx.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 */
public class XzkxResourceUtil {

	private static final String domain;
	private static final String agentId;
	private static final String roleid;

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("hcnyconfig");
	static {
		domain = getConfigByName("domain");
		agentId = getConfigByName("agentId");
		roleid = getConfigByName("roleid");
	}
	public static final String getConfigByName(String name) {
		return bundle.getString(name);
	}

	public static final String getDomain() {
		return domain;
	}
	
	public static final String getAgentId() {
		return agentId;
	}

	public static final String getRoleid() {
		return roleid;
	}

	public static void main(String[] args) {
		System.out.println(getDomain());
	}
}
