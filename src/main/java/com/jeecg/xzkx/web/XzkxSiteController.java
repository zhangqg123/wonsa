package com.jeecg.xzkx.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
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

import com.jeecg.xzkx.dao.XzkxSiteDao;
import com.jeecg.xzkx.entity.XzkxSite;
import com.jeecg.xzkx.util.XzkxResourceUtil;

 /**
 * 描述：</b>XzkxSiteController<br>
 * @author p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Controller
@RequestMapping("/p3/xzkx/xzkxSite")
public class XzkxSiteController extends BaseController{
	@Autowired
	private XzkxSiteDao xzkxSiteDao;
  
	/**
	  * 列表页面
	  * @return
	  */
	@RequestMapping(params = "list",method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute XzkxSite query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		try {
		 	LOG.info(request, " back list");
		 	//分页数据
			MiniDaoPage<XzkxSite> list =  xzkxSiteDao.getAll(query,pageNo,pageSize);
			List<Map> agentList = (List<Map>) request.getSession().getAttribute("QYWX_AGENTS");
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("xzkxSite",query);
			velocityContext.put("agentList",agentList);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
			String viewName = "xzkx/xzkxSite-list.vm";
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
	public void xzkxSiteDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "xzkx/xzkxSite-detail.vm";
		XzkxSite xzkxSite = xzkxSiteDao.get(id);
		velocityContext.put("xzkxSite",xzkxSite);
		ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(params = "toAdd",method ={RequestMethod.GET, RequestMethod.POST})
	public void toAddDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 String sessionid = request.getSession().getId();
		 velocityContext.put("sessionid", sessionid);
		 String viewName = "xzkx/xzkxSite-add.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 保存信息
	 * @return
	 */
	@RequestMapping(params = "doAdd",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doAdd(@ModelAttribute XzkxSite xzkxSite){
		AjaxJson j = new AjaxJson();
		try {
		    String randomSeed = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		    xzkxSite.setId(randomSeed);
			xzkxSite.setAgentId(XzkxResourceUtil.getAgentId());
			xzkxSiteDao.insert(xzkxSite);
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
		 VelocityContext velocityContext = new VelocityContext();
		 XzkxSite xzkxSite = xzkxSiteDao.get(id);
		 velocityContext.put("xzkxSite",xzkxSite);
		 String sessionid = request.getSession().getId();
		 velocityContext.put("sessionid", sessionid);
//		 List<Map> agentList = (List<Map>) request.getSession().getAttribute("QYWX_AGENTS");
//		 velocityContext.put("agentList", agentList);
		 String viewName = "xzkx/xzkxSite-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(params = "doEdit",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doEdit(@ModelAttribute XzkxSite xzkxSite){
		AjaxJson j = new AjaxJson();
		try {
			xzkxSite.setAgentId(XzkxResourceUtil.getAgentId());
			xzkxSiteDao.update(xzkxSite);
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
		    XzkxSite xzkxSite = new XzkxSite();
			xzkxSite.setId(id);
			xzkxSiteDao.delete(xzkxSite);
			j.setMsg("删除成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
	}
}

