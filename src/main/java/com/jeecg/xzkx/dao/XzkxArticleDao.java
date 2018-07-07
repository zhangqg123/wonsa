package com.jeecg.xzkx.dao;

import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;

import com.jeecg.xzkx.entity.XzkxArticle;
import com.jeecg.qywx.base.entity.QywxGzuserinfo;

/**
 * 描述：</b>XzkxArticleDao<br>
 * @author：p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Repository
public interface XzkxArticleDao extends XzkxDao{

    /**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("SELECT * FROM xzkx_article WHERE ID = :id")
	XzkxArticle get(@Param("id") String id);
	
	/**
	 * 修改数据
	 * @param xzkxArticle
	 * @return
	 */
	int update(@Param("xzkxArticle") XzkxArticle xzkxArticle);
	
	/**
	 * 插入数据
	 * @param act
	 */
	void insert(@Param("xzkxArticle") XzkxArticle xzkxArticle);
	
	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param xzkxArticle
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(XzkxArticle.class)
	public MiniDaoPage<XzkxArticle> getAll(@Param("xzkxArticle") XzkxArticle xzkxArticle,@Param("page")  int page,@Param("rows") int rows);
	
	@ResultType(XzkxArticle.class)
	public MiniDaoPage<XzkxArticle> getMyXzkxArticle(@Param("xzkxArticle") XzkxArticle query,@Param("page")  int page,@Param("rows") int rows);

	@ResultType(XzkxArticle.class)
	public MiniDaoPage<XzkxArticle> getAll();
	
	@ResultType(XzkxArticle.class)
	public MiniDaoPage<XzkxArticle> getListByMenu(@Param("coulmnId") String coulmnId );
	
	@ResultType(String.class)
	public String getCount(@Param("coulmnId") String coulmnId );
	
	@Sql("DELETE from xzkx_article WHERE ID = :xzkxArticle.id")
	public void delete(@Param("xzkxArticle") XzkxArticle xzkxArticle);
	
}

