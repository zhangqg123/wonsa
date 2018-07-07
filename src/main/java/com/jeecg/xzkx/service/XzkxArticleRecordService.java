package com.jeecg.xzkx.service;

import java.util.List;

import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.pojo.MiniDaoPage;

import com.jeecg.xzkx.entity.XzkxArticleRecordEntity;

/**
 * 描述：公告管理
 * @author: www.jeecg.org
 * @since：2017年01月27日 11时22分16秒 星期五 
 * @version:1.0
 */
public interface XzkxArticleRecordService {
	public XzkxArticleRecordEntity get(String id);

	public List<XzkxArticleRecordEntity> getArticleRecord(String articleid,String userId);

	public int update(XzkxArticleRecordEntity xzkxArticleRecord);

	public void insert(XzkxArticleRecordEntity xzkxArticleRecord);

	public MiniDaoPage<XzkxArticleRecordEntity> getAll(XzkxArticleRecordEntity xzkxArticleRecord,int page,int rows);

	public void delete(XzkxArticleRecordEntity xzkxArticleRecord);
	
}
