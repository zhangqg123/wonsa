package com.jeecg.xzkx.xzkxdata.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jeecgframework.p3.core.utils.common.ApplicationContextUtil;

import com.jeecg.xzkx.xzkxdata.XzkxDataCollectI;
import com.jeecg.xzkx.service.XzkxArticleRecordService;
import com.jeecg.xzkx.common.XzkxConstant;
import com.jeecg.xzkx.common.XzkxDataContent;
import com.jeecg.xzkx.dao.XzkxArticleDao;
import com.jeecg.xzkx.entity.XzkxArticle;
import com.jeecg.xzkx.entity.XzkxArticleRecordEntity;


/**
 * CMS 文章数据收集器
 * @author jg_huangxg
 *
 */
public class XzkxArticleDataCollect implements XzkxDataCollectI {

	@Override
	public void collect(Map<String, String> params) {
		XzkxArticleDao xzkxArticleDao = (XzkxArticleDao) ApplicationContextUtil.getContext().getBean("xzkxArticleDao");
		XzkxArticleRecordService xzkxArticleRecordService = (XzkxArticleRecordService) ApplicationContextUtil.getContext().getBean("xzkxArticleRecordService");
		
		String articleid = params.get("articleid") != null ? params.get("articleid").toString() : "-";
		String userid = params.get("qyUserId");
		String name = params.get("name");
		String department = params.get("department");

		XzkxArticle xzkxArticleEntity = xzkxArticleDao.get(articleid);
		if (xzkxArticleEntity != null) {
			XzkxDataContent.put(XzkxConstant.XZKX_DATA_MAP_ARTICLE, xzkxArticleEntity);
			XzkxDataContent.put(XzkxConstant.XZKX_DATA_STR_TITLE, xzkxArticleEntity.getTitle());
		}else{
			XzkxDataContent.put(XzkxConstant.XZKX_DATA_MAP_ARTICLE, new XzkxArticle());
			XzkxDataContent.put(XzkxConstant.XZKX_DATA_STR_TITLE, "文章明细");
		}
		String res = XzkxConstant.XZKX_ROOT_PATH + File.separator + params.get(XzkxConstant.XZKX_STYLE_NAME);
		//资源路径
		XzkxDataContent.put(XzkxConstant.BASE, res);
		XzkxDataContent.put(XzkxConstant.BASEPATH, params.get(XzkxConstant.BASEPATH));
		XzkxArticleRecordEntity nar=new XzkxArticleRecordEntity();
		if(userid!=null){
			List<XzkxArticleRecordEntity> xzkxArticleRecordList = xzkxArticleRecordService.getArticleRecord(articleid,userid);
			if(xzkxArticleRecordList.size()>0){
				if(XzkxDataContent.get(XzkxConstant.SIGN)!=null){
					XzkxDataContent.remove(XzkxConstant.SIGN);
				}
				XzkxDataContent.put(XzkxConstant.SIGN, "已签收");
			}else{
				XzkxDataContent.put(XzkxConstant.SIGN, "文章签收");
			}
			nar.setUserid(userid);
			nar.setName(name);
			nar.setDepartment(department);
		}else{
			if(XzkxDataContent.get(XzkxConstant.SIGN)!=null){
				XzkxDataContent.remove(XzkxConstant.SIGN);
			}
			XzkxDataContent.put(XzkxConstant.SIGN, "没用户");

			nar.setUserid("");
			nar.setName("");
			nar.setDepartment("");
		}

		XzkxDataContent.put(XzkxConstant.XZKX_DATA_MAP_ARTICLE_RECORD , nar);
	}

}
