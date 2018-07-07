package com.jeecg.xzkx.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;

import com.jeecg.xzkx.entity.Depart;
import com.jeecg.xzkx.entity.User;
import com.jeecg.xzkx.entity.XzkxMenu;

/**
 * 描述：</b>XzkxMenuDao<br>
 * @author：p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Repository
public interface XzkxMenuDao{

    /**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("SELECT * FROM xzkx_menu WHERE ID = :id")
	XzkxMenu get(@Param("id") String id);
	
	@Sql("SELECT * FROM xzkx_menu WHERE PARENT_CODE = '' or PARENT_CODE is null")
	List<XzkxMenu> getSubAll();

	@Sql("SELECT * FROM xzkx_menu WHERE PARENT_CODE = :id")
	List<XzkxMenu> getSubAll(@Param("id") String menuid);
	
	/**
	 * 修改数据
	 * @param xzkxMenu
	 * @return
	 */
	int update(@Param("xzkxMenu") XzkxMenu xzkxMenu);
	
	/**
	 * 插入数据
	 * @param act
	 */
	void insert(@Param("xzkxMenu") XzkxMenu xzkxMenu);
	
	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param xzkxMenu
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(XzkxMenu.class)
	public MiniDaoPage<XzkxMenu> getAll(@Param("xzkxMenu") XzkxMenu xzkxMenu,@Param("page")  int page,@Param("rows") int rows);
	
	@ResultType(XzkxMenu.class)
	public MiniDaoPage<XzkxMenu> getAll();
	
	@Sql("DELETE from xzkx_menu WHERE ID = :xzkxMenu.id")
	public void delete(@Param("xzkxMenu") XzkxMenu xzkxMenu);
	
	@Sql("SELECT * FROM xzkx_menu WHERE ALIAS = :alias")
	XzkxMenu getMenuAlias(@Param("alias") String alias);
	
	@Sql("SELECT * FROM t_s_depart WHERE id = :departId")
	public Depart getDepart(@Param("departId") String departId);
	
	@Sql("SELECT tsbu.username,tsbu.realname FROM t_s_base_user tsbu left join t_s_role_user tsru on tsbu.id=tsru.userid where tsru.roleid= :roleid")
	public List<User> getUsers(@Param("roleid") String roleid);
	
	@ResultType(XzkxMenu.class)
	public MiniDaoPage<XzkxMenu> getTree();

	@ResultType(String.class)
	public String getMaxLocalCode(@Param("localCodeLength") String localCodeLength,@Param("parentCode") String parentCode);
}

