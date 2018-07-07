package com.jeecg.xzkx.xzkxdata.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.p3.core.utils.common.ApplicationContextUtil;

import com.jeecg.xzkx.xzkxdata.XzkxDataCollectI;
import com.jeecg.xzkx.common.XzkxConstant;
import com.jeecg.xzkx.common.XzkxDataContent;
import com.jeecg.xzkx.dao.XzkxArticleDao;
import com.jeecg.xzkx.dao.XzkxMenuDao;
import com.jeecg.xzkx.entity.XzkxArticle;
import com.jeecg.xzkx.entity.XzkxMenu;
import com.jeecg.xzkx.util.MyBeanUtils;



/**
 * CMS 栏目数据收集器
 * @author jg_huangxg
 *
 */
public class XzkxMenuDataCollect implements XzkxDataCollectI {

	@Override
	public void collect(Map<String, String> params) {
		XzkxMenuDao xzkxMenuDao = (XzkxMenuDao) ApplicationContextUtil.getContext().getBean("xzkxMenuDao");
		XzkxArticleDao xzkxArticleDao = (XzkxArticleDao) ApplicationContextUtil.getContext().getBean("xzkxArticleDao");
		
		String menuid = params.get("id") != null ? params.get("id").toString(): "-";
		XzkxMenu menuEntity = xzkxMenuDao.get(menuid);
		XzkxArticle xzkxArticleEntity = null;
		if (menuEntity != null) {
			//------------------------------------------------------------------
			List<XzkxArticle> list = xzkxArticleDao.getListByMenu(menuid).getResults();
			//判断Menu类型
//			if("link".equals(menuEntity.getType())){
			if("single".equals(menuEntity.getType())){
				//单文章类型，则加载排序第一条文章
				if(list!=null&&list.size()>0){
					 xzkxArticleEntity = list.get(0);
					 ArrayList<XzkxArticle> articles = new ArrayList<XzkxArticle>();
					 articles.add(xzkxArticleEntity);
					 XzkxDataContent.put(XzkxConstant.XZKX_DATA_LIST_ARTICLE, articles);
				}
			} else {
				XzkxDataContent.put(XzkxConstant.XZKX_DATA_LIST_ARTICLE, list);
			}
			Map<String, XzkxArticle> valueMap = new HashMap<String, XzkxArticle>();
			MyBeanUtils.copyBean2Map(valueMap, menuEntity);
			if(xzkxArticleEntity==null)
					xzkxArticleEntity = new XzkxArticle();
			valueMap.put("article", xzkxArticleEntity);
			//------------------------------------------------------------------
			XzkxDataContent.put(XzkxConstant.XZKX_DATA_MAP_MENU, valueMap);
			XzkxDataContent.put(XzkxConstant.XZKX_DATA_STR_TITLE, menuEntity.getName());
		} else {
			XzkxDataContent.put(XzkxConstant.XZKX_DATA_MAP_MENU, new XzkxMenu());
			XzkxDataContent.put(XzkxConstant.XZKX_DATA_STR_TITLE, "信息列表");
		}
		String res = XzkxConstant.XZKX_ROOT_PATH + File.separator + params.get(XzkxConstant.XZKX_STYLE_NAME);
		//资源路径
		XzkxDataContent.put(XzkxConstant.BASE, res);
		XzkxDataContent.put(XzkxConstant.BASEPATH, params.get(XzkxConstant.BASEPATH));
	}

}
