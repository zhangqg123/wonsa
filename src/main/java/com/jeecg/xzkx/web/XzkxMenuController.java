package com.jeecg.xzkx.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.common.utils.StringUtil;
import org.jeecgframework.p3.core.page.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.xzkx.dao.XzkxMenuDao;
import com.jeecg.xzkx.entity.User;
import com.jeecg.xzkx.entity.XzkxMenu;
import com.jeecg.xzkx.util.SimpleTreeIdBuild;
import com.jeecg.xzkx.util.XzkxResourceUtil;

 /**
 * 描述：</b>XzkxMenuController<br>
 * @author p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Controller
@RequestMapping("/p3/xzkx/xzkxMenu")
public class XzkxMenuController extends BaseController{
	@Autowired
	private XzkxMenuDao xzkxMenuDao;
  
	/**
	  * 列表页面
	  * @return
	  */
	@RequestMapping(params = "list",method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute XzkxMenu query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		try {
		 	LOG.info(request, " back list");
		 	//分页数据
			MiniDaoPage<XzkxMenu> list =  xzkxMenuDao.getAll(query,pageNo,pageSize);
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("xzkxMenu",query);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
			String viewName = "xzkx/xzkxMenu-list.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

	 /**
	  * 详情
	  * @return
	  */
	@RequestMapping(params="toDetail",method = RequestMethod.GET)
	public void xzkxMenuDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "xzkx/xzkxMenu-detail.vm";
		XzkxMenu xzkxMenu = xzkxMenuDao.get(id);
		velocityContext.put("xzkxMenu",xzkxMenu);
		ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(params = "toAdd",method ={RequestMethod.GET, RequestMethod.POST})
	public void toAddDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String roleid = XzkxResourceUtil.getRoleid();
		List<User> users = xzkxMenuDao.getUsers(roleid); 
		VelocityContext velocityContext = new VelocityContext();
		String sessionid = request.getSession().getId();
		velocityContext.put("sessionid", sessionid);
		velocityContext.put("users", users);
		String viewName = "xzkx/xzkxMenu-add.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 保存信息
	 * @return
	 */
	@RequestMapping(params = "doAdd",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doAdd(@ModelAttribute XzkxMenu xzkxMenu){
		AjaxJson j = new AjaxJson();
		try {
		    if (StringUtil.isEmpty(xzkxMenu.getParentCode())) {//无上级
		    	xzkxMenu.setId(new SimpleTreeIdBuild().getId(this.xzkxMenuDao,null));
		    	xzkxMenu.setParentCode(null);
			}else{//有上级
				xzkxMenu.setId(new SimpleTreeIdBuild().getId(this.xzkxMenuDao,xzkxMenu.getParentCode()));
			}
			xzkxMenuDao.insert(xzkxMenu);
			j.setMsg("保存成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存失败");
		}
		return j;
	}

	/**
	 * 跳转到编辑页面
	 * @return
	 */
	@RequestMapping(params="toEdit",method = RequestMethod.GET)
	public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		String roleid = XzkxResourceUtil.getRoleid();
		List<User> users = xzkxMenuDao.getUsers(roleid); 
		VelocityContext velocityContext = new VelocityContext();
		String sessionid = request.getSession().getId();
		velocityContext.put("sessionid", sessionid);
		XzkxMenu xzkxMenu = xzkxMenuDao.get(id);
		velocityContext.put("xzkxMenu",xzkxMenu);
		velocityContext.put("users", users);
		String viewName = "xzkx/xzkxMenu-edit.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(params = "doEdit",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doEdit(@ModelAttribute XzkxMenu xzkxMenu){
		AjaxJson j = new AjaxJson();
		try {
			xzkxMenuDao.update(xzkxMenu);
			j.setMsg("编辑成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("编辑失败");
		}
		return j;
	}


	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(params="doDelete",method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id){
		AjaxJson j = new AjaxJson();
		try {
		    XzkxMenu xzkxMenu = new XzkxMenu();
			xzkxMenu.setId(id);
			xzkxMenuDao.delete(xzkxMenu);
			j.setMsg("删除成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
	}
	@RequestMapping(params = "tree")
	@ResponseBody
	public List<XzkxMenu> tree() {
		MiniDaoPage<XzkxMenu> list = xzkxMenuDao.getTree();
		List<XzkxMenu> xzkxMenuList = list.getResults();
		return xzkxMenuList;
	}
}

