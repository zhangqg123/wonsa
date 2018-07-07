package com.jeecg.xzkx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Service;

import com.jeecg.xzkx.dao.XzkxArticleRecordDao;
import com.jeecg.xzkx.entity.XzkxArticleRecordEntity;
import com.jeecg.xzkx.service.XzkxArticleRecordService;


/**
 * 描述：公告管理
 * @author: www.jeecg.org
 * @since：2017年01月27日 11时22分16秒 星期五 
 * @version:1.0
 */

@Service("xzkxArticleRecordService")
public class XzkxArticleRecordServiceImpl implements XzkxArticleRecordService {
	@Resource
	private XzkxArticleRecordDao xzkxArticleRecordDao;

	@Override
	public XzkxArticleRecordEntity get(String id) {
		return xzkxArticleRecordDao.get(id);
	}

	@Override
	public List<XzkxArticleRecordEntity> getArticleRecord(String articleid,String userId) {
		return xzkxArticleRecordDao.getArticleRecord(articleid,userId);
	}

	@Override
	public int update(XzkxArticleRecordEntity xzkxArticleRecord) {
		return xzkxArticleRecordDao.update(xzkxArticleRecord);
	}

	@Override
	public void insert(XzkxArticleRecordEntity xzkxArticleRecord) {
		xzkxArticleRecordDao.insert(xzkxArticleRecord);
		
	}

	@Override
	public MiniDaoPage<XzkxArticleRecordEntity> getAll(XzkxArticleRecordEntity xzkxArticleRecord, int page, int rows) {
		return xzkxArticleRecordDao.getAll(xzkxArticleRecord, page, rows);
	}

	@Override
	public void delete(XzkxArticleRecordEntity xzkxArticleRecord) {
		xzkxArticleRecordDao.delete(xzkxArticleRecord);
		
	}


}
