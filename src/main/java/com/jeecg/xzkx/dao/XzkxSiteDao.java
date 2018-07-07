package com.jeecg.xzkx.dao;

import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;

import com.jeecg.xzkx.entity.XzkxSite;

/**
 * 描述：</b>XzkxSiteDao<br>
 * @author：p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Repository
public interface XzkxSiteDao extends XzkxDao{

    /**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("SELECT * FROM xzkx_site WHERE ID = :id")
	XzkxSite get(@Param("id") String id);
	
	/**
	 * 修改数据
	 * @param xzkxSite
	 * @return
	 */
	int update(@Param("xzkxSite") XzkxSite xzkxSite);
	
	/**
	 * 插入数据
	 * @param act
	 */
	void insert(@Param("xzkxSite") XzkxSite xzkxSite);
	
	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param xzkxSite
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(XzkxSite.class)
	public MiniDaoPage<XzkxSite> getAll(@Param("xzkxSite") XzkxSite xzkxSite,@Param("page")  int page,@Param("rows") int rows);
	
	@ResultType(XzkxSite.class)
	public MiniDaoPage<XzkxSite> getAll();
	
	@Sql("DELETE from xzkx_site WHERE ID = :xzkxSite.id")
	public void delete(@Param("xzkxSite") XzkxSite xzkxSite);
	
}

