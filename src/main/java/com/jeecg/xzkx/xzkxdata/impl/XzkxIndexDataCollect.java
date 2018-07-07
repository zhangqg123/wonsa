package com.jeecg.xzkx.xzkxdata.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jeecgframework.p3.core.utils.common.ApplicationContextUtil;

import com.jeecg.xzkx.xzkxdata.XzkxDataCollectI;
import com.jeecg.xzkx.common.XzkxConstant;
import com.jeecg.xzkx.common.XzkxDataContent;
import com.jeecg.xzkx.dao.XzkxAdDao;
import com.jeecg.xzkx.dao.XzkxMenuDao;
import com.jeecg.xzkx.entity.XzkxAd;
import com.jeecg.xzkx.entity.XzkxMenu;



/**
 * CMS首页数据收集器
 * @author jg_huangxg
 *
 */
public class XzkxIndexDataCollect implements XzkxDataCollectI {

	@Override
	public void collect(Map<String, String> params) {
		//注意其他方法调用只能写在里面
		XzkxAdDao xzkxAdDao = (XzkxAdDao) ApplicationContextUtil.getContext().getBean("xzkxAdDao");
		XzkxMenuDao xzkxMenuDao = (XzkxMenuDao) ApplicationContextUtil.getContext().getBean("xzkxMenuDao");
		String menuid = params.get("id") != null ? params.get("id").toString(): "-";
		List<XzkxAd> adList = xzkxAdDao.getAll().getResults();
		XzkxDataContent.put(XzkxConstant.XZKX_DATA_LIST_AD, adList);
		List<XzkxMenu> menuList;
		if(menuid.equals("-")){
			menuList = xzkxMenuDao.getSubAll();
		}
		else{		
			menuList = xzkxMenuDao.getSubAll(menuid);
		}
//		List<XzkxMenu> menuList = xzkxMenuDao.getAll().getResults();
		
		XzkxDataContent.put(XzkxConstant.XZKX_DATA_LIST_MENU, menuList);
		String res = XzkxConstant.XZKX_ROOT_PATH + File.separator + params.get(XzkxConstant.XZKX_STYLE_NAME);
		//资源路径
		XzkxDataContent.put(XzkxConstant.BASE, res);
		XzkxDataContent.put(XzkxConstant.BASEPATH, params.get(XzkxConstant.BASEPATH));
	}
}