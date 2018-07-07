package com.jeecg.xzkx.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;

import com.jeecg.xzkx.entity.XzkxArticleRecordEntity;


/**
 * 描述：公告管理
 * @author：www.jeecg.org
 * @since：2017年01月27日 11时22分16秒 星期五 
 * @version:1.0
 */
@Repository
public interface XzkxArticleRecordDao{

    /**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("SELECT * FROM xzkx_article_record WHERE ID = :id")
	XzkxArticleRecordEntity get(@Param("id") String id);
	
	@Sql("SELECT * FROM xzkx_article_record WHERE articleid = :articleid and userid=:userid")
	List<XzkxArticleRecordEntity> getArticleRecord(@Param("articleid") String articleid,@Param("userid") String userId);
	/**
	 * 修改数据
	 * @param xzkxArticleRecord
	 * @return
	 */
	int update(@Param("xzkxArticleRecord") XzkxArticleRecordEntity xzkxArticleRecord);
	
	/**
	 * 插入数据
	 * @param act
	 */
	void insert(@Param("xzkxArticleRecord") XzkxArticleRecordEntity xzkxArticleRecord);
	
	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param xzkxArticleRecord
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(XzkxArticleRecordEntity.class)
	public MiniDaoPage<XzkxArticleRecordEntity> getAll(@Param("xzkxArticleRecord") XzkxArticleRecordEntity xzkxArticleRecord,@Param("page")  int page,@Param("rows") int rows);
	
	@Sql("DELETE from xzkx_article_record WHERE ID = :xzkxArticleRecord.id")
	public void delete(@Param("xzkxArticleRecord") XzkxArticleRecordEntity xzkxArticleRecord);

	
}

