package com.jeecg.xzkx.dao;

import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;

import com.jeecg.xzkx.entity.XzkxAd;

/**
 * 描述：</b>XzkxAdDao<br>
 * @author：p3.jeecg
 * @since：2016年06月13日 15时00分28秒 星期一 
 * @version:1.0
 */
@Repository
public interface XzkxAdDao{

    /**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("SELECT * FROM xzkx_ad WHERE ID = :id")
	XzkxAd get(@Param("id") String id);
	
	/**
	 * 修改数据
	 * @param xzkxAd
	 * @return
	 */
	int update(@Param("xzkxAd") XzkxAd xzkxAd);
	
	/**
	 * 插入数据
	 * @param act
	 */
	void insert(@Param("xzkxAd") XzkxAd xzkxAd);
	
	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param xzkxAd
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(XzkxAd.class)
	public MiniDaoPage<XzkxAd> getAll(@Param("xzkxAd") XzkxAd xzkxAd,@Param("page")  int page,@Param("rows") int rows);
	
	@ResultType(XzkxAd.class)
	public MiniDaoPage<XzkxAd> getAll();
	
	@Sql("DELETE from xzkx_ad WHERE ID = :xzkxAd.id")
	public void delete(@Param("xzkxAd") XzkxAd xzkxAd);
	
}

