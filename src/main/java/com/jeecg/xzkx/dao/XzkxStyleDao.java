package com.jeecg.xzkx.dao;

import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;

import com.jeecg.xzkx.entity.XzkxStyle;

/**
 * 描述：</b>XzkxStyleDao<br>
 * @author：p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Repository
public interface XzkxStyleDao extends XzkxDao{

    /**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("SELECT * FROM xzkx_style WHERE ID = :id")
	XzkxStyle get(@Param("id") String id);
	
	/**
	 * 修改数据
	 * @param xzkxStyle
	 * @return
	 */
	int update(@Param("xzkxStyle") XzkxStyle xzkxStyle);
	
	/**
	 * 插入数据
	 * @param act
	 */
	void insert(@Param("xzkxStyle") XzkxStyle xzkxStyle);
	
	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param xzkxStyle
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(XzkxStyle.class)
	public MiniDaoPage<XzkxStyle> getAll(@Param("xzkxStyle") XzkxStyle xzkxStyle,@Param("page")  int page,@Param("rows") int rows);
	
	@Sql("DELETE from xzkx_style WHERE ID = :xzkxStyle.id")
	public void delete(@Param("xzkxStyle") XzkxStyle xzkxStyle);
	
}

