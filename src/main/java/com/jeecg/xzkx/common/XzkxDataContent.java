package com.jeecg.xzkx.common;

import java.util.HashMap;
import java.util.Map;

/**
 * CMS数据容器
 * @author jg_huangxg
 *
 */
public class XzkxDataContent {
	public static final String XZKX_CONTENT_KEY = "xzkxData";
	//在xzkx数据中，加上一层封装
	private static final Map<String,Object> xzkxContent = new HashMap<String,Object>();
	private static final Map<String,Object> xzkxData = new HashMap<String,Object>();
	
	/**
	 * 保存数据到CMS容器
	 */
	public static void put(String key,Object object){
		xzkxData.put(key, object);
	}
	
	/**
	 * 从CMS容器，取值
	 * @param key
	 */
	public static Object get(String key){
		return xzkxData.get(key);
	}
	public static Object remove(String key){
		return xzkxData.remove(key);
	}
	
	/**
	 * 获取CMS容器
	 * @return
	 */
	public static Map<String,Object> loadContent(){
		xzkxContent.put(XZKX_CONTENT_KEY, xzkxData);
		return xzkxContent;
	}
}
