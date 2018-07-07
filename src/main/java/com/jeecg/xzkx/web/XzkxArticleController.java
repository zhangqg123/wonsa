package com.jeecg.xzkx.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.page.PaginatedList;
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

import com.alibaba.fastjson.JSONObject;
import com.jeecg.xzkx.dao.XzkxArticleDao;
import com.jeecg.xzkx.dao.XzkxMenuDao;
import com.jeecg.xzkx.dao.XzkxSiteDao;
import com.jeecg.xzkx.entity.Depart;
import com.jeecg.xzkx.entity.XzkxArticle;
import com.jeecg.xzkx.entity.XzkxMenu;
import com.jeecg.xzkx.entity.XzkxSite;
import com.jeecg.xzkx.util.XzkxResourceUtil;
import com.jeecg.qywx.account.service.AccountService;
import com.jeecg.qywx.api.core.common.AccessToken;
import com.jeecg.qywx.api.message.JwMessageAPI;
import com.jeecg.qywx.api.message.vo.News;
import com.jeecg.qywx.api.message.vo.NewsArticle;
import com.jeecg.qywx.api.message.vo.NewsEntity;
import com.jeecg.qywx.base.dao.QywxMessagelogDao;
import com.jeecg.qywx.base.entity.QywxMessagelog;
import com.jeecg.qywx.sucai.dao.QywxNewsitemDao;
import com.jeecg.qywx.sucai.dao.QywxNewstemplateDao;
import com.jeecg.qywx.sucai.entity.QywxNewsitem;
import com.jeecg.qywx.sucai.entity.QywxNewstemplate;
//import com.jeecg.qywx.util.ConfigUtil;

 /**
 * 描述：</b>XzkxArticleController<br>
 * @author p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Controller
@RequestMapping("/p3/xzkx/xzkxArticle")
public class XzkxArticleController extends BaseController{
	@Autowired
	private XzkxArticleDao xzkxArticleDao;
	@Autowired
	private XzkxMenuDao xzkxMenuDao;
	@Autowired
	private XzkxSiteDao xzkxSiteDao;
	@Autowired
	private QywxNewstemplateDao qywxNewstemplateDao;
	@Autowired
	private QywxNewsitemDao qywxNewsitemDao;
	@Autowired 
	private AccountService accountService;
	@Autowired
	private QywxMessagelogDao qywxMessagelogDao;

	/**
	  * 列表页面
	  * @return
	  */
	@RequestMapping(params = "list",method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute XzkxArticle query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		try {
		 	LOG.info(request, " back list");
			String xzkxUserName=(String) request.getSession().getAttribute("loginUserName");		
			String roles=(String) request.getSession().getAttribute("roles");		
			VelocityContext velocityContext = new VelocityContext();
			XzkxMenu xzkxMenu = xzkxMenuDao.getMenuAlias(xzkxUserName);
			List<XzkxMenu> xzkxMenuList = new ArrayList<XzkxMenu>();
			if(xzkxMenu!=null){
				query.setColumnId(xzkxMenu.getId());
				xzkxMenuList.add(xzkxMenu);
			}else{
				if(roles.contains("管理员")){
					MiniDaoPage<XzkxMenu> menuList = xzkxMenuDao.getTree();
					xzkxMenuList = menuList.getResults();
				}else{				
					query.setColumnId("meiyou");
					velocityContext.put("meiyou","meiyou");
				}
			}
			MiniDaoPage<XzkxArticle> list =  xzkxArticleDao.getAll(query,pageNo,pageSize);
			velocityContext.put("xzkxArticle",query);
			velocityContext.put("xzkxMenuList",xzkxMenuList);
			velocityContext.put("domain", XzkxResourceUtil.getDomain());
//				PaginatedList pagelist = SystemTools.convertPaginatedList(list);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
			String viewName = "xzkx/xzkxArticle-list.vm";
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
	public void xzkxArticleDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
			VelocityContext velocityContext = new VelocityContext();
			String viewName = "xzkx/xzkxArticle-detail.vm";
			XzkxArticle xzkxArticle = xzkxArticleDao.get(id);
			velocityContext.put("xzkxArticle",xzkxArticle);
			ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(params = "toAdd",method ={RequestMethod.GET, RequestMethod.POST})
	public void toAddDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String xzkxUserName=(String) request.getSession().getAttribute("loginUserName");		
		String departId=(String) request.getSession().getAttribute("departId");		
		String roles=(String) request.getSession().getAttribute("roles");		
		XzkxMenu xzkxMenu = xzkxMenuDao.getMenuAlias(xzkxUserName);
		if(xzkxMenu==null){
			if(roles.contains("管理员")){
				xzkxMenu=new XzkxMenu();
			}
		}
		Depart depart = xzkxMenuDao.getDepart(departId);
		//分页数据
		String agentId=null;
		if(depart.getParentDepartId()!=null&&depart.getParentDepartId().equals(XzkxResourceUtil.getConfigByName("departId"))){
			agentId=XzkxResourceUtil.getConfigByName(xzkxUserName);
		}else{
			agentId=XzkxResourceUtil.getAgentId();
		}
		if(xzkxMenu!=null){
			MiniDaoPage<XzkxMenu> menuList =  xzkxMenuDao.getAll(xzkxMenu,1,199);
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("menuList",SystemTools.convertPaginatedList(menuList));
			String sessionid = request.getSession().getId();
			velocityContext.put("sessionid", sessionid);
			velocityContext.put("agentId", agentId);

			String viewName = "xzkx/xzkxArticle-add.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
		}
	}

	/**
	 * 保存信息
	 * @return
	 */
	@RequestMapping(params = "doAdd",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doAdd(@ModelAttribute XzkxArticle xzkxArticle){
		AjaxJson j = new AjaxJson();
		try {
		    String randomSeed = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		    xzkxArticle.setId(randomSeed);
//			XzkxSite xzkxSite = xzkxSiteDao.getAll().getResults().get(0);
//		    xzkxArticle.setAgentId(XzkxResourceUtil.getAgentId());
			xzkxArticleDao.insert(xzkxArticle);
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
		String xzkxUserName=(String) request.getSession().getAttribute("loginUserName");		
		String roles=(String) request.getSession().getAttribute("roles");		
		XzkxMenu xzkxMenu = xzkxMenuDao.getMenuAlias(xzkxUserName);
		if(xzkxMenu==null){
			if(roles.contains("管理员")){
				xzkxMenu=new XzkxMenu();
			}
		}
		if(xzkxMenu!=null){
			MiniDaoPage<XzkxMenu> menuList =  xzkxMenuDao.getAll(xzkxMenu,1,199);
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("menuList",SystemTools.convertPaginatedList(menuList));
			XzkxArticle xzkxArticle = xzkxArticleDao.get(id);
			velocityContext.put("xzkxArticle",xzkxArticle);
			String sessionid = request.getSession().getId();
			velocityContext.put("sessionid", sessionid);
			String viewName = "xzkx/xzkxArticle-edit.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
		}
	}

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(params = "doEdit",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doEdit(@ModelAttribute XzkxArticle xzkxArticle){
		AjaxJson j = new AjaxJson();
		try {
//			if(xzkxArticle.getAgentId()==null){
//				xzkxArticle.setAgentId(XzkxResourceUtil.getAgentId());
//		    }
			xzkxArticleDao.update(xzkxArticle);
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
			    XzkxArticle xzkxArticle = new XzkxArticle();
				xzkxArticle.setId(id);
				xzkxArticleDao.delete(xzkxArticle);
				j.setMsg("删除成功");
			} catch (Exception e) {
			    log.info(e.getMessage());
				j.setSuccess(false);
				j.setMsg("删除失败");
			}
			return j;
	}
	
	// 群发信息
	@RequestMapping(params = "toGroupTextSend", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public AjaxJson toGroupTextSend(HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			JSONObject receive = null;
			String msgtype = "news";// 类型
			String param = request.getParameter("param");// 文本框的内容
			String[] xzkx = param.split("@");
			String xzkxUrl = xzkx[0];
			String XzkxArticeId = xzkx[1];
			XzkxArticle xzkxArticle = xzkxArticleDao.get(XzkxArticeId);
			String toparty = xzkxArticle.getDepartment();// 部门Id
			String topartys = toparty.replaceAll(",", "|");

			QywxMessagelog allmessage = new QywxMessagelog();
//			// 发送图文信信息
//			String templateId = request.getParameter("templateId");
//			QywxNewstemplate qywxNewstemplate=qywxNewstemplateDao.getName("农情快递");
				// 获取图文信息
//			List<QywxNewsitem> item = qywxNewsitemDao.getALLNews(qywxNewstemplate.getId());
			News news = new News();
			news.setToparty(topartys);// 部门Id
			news.setTouser("");//用户列表id群发
			news.setMsgtype("news");// 参数类型
			news.setAgentid(Integer.valueOf(xzkxArticle.getAgentId()));// 企业应用id整型
			String domain = XzkxResourceUtil.getDomain();

			List<NewsArticle> ls = new ArrayList<NewsArticle>();
//			for (int i = 0; i < item.size(); i++) {
//				String picurl = item.get(i).getImagePath();
				String picurl = "upload/img/xzkx/"+xzkxArticle.getImageHref();
				String title = xzkxArticle.getTitle();
				String description = xzkxArticle.getSummary();
				String url = xzkxUrl;
				if(xzkxArticle.getExtUrl().contains("http://")||xzkxArticle.getExtUrl().contains("https://")){
					url=xzkxArticle.getExtUrl();
				}
				NewsArticle newsarticle = new NewsArticle();
				newsarticle.setDescription(description);
				// 在配置文件中配置路径
				newsarticle.setPicurl(domain + "/" + picurl);
		    	System.out.println("....picurl..."+newsarticle.getPicurl());
				
				newsarticle.setTitle(title);
//				if(qywxNewstemplate.getTemplateType().equals("common")){
//					newsarticle.setUrl(domain + "/qywx/qywxNewsitem.do?goContent&id=" + item.get(i).getId());
//				}
//				if(qywxNewstemplate.getTemplateType().equals("cl")){
					newsarticle.setUrl(url);
//				}
				ls.add(newsarticle);
//			}
			NewsEntity newsEntity = new NewsEntity();
			newsEntity.setArticles(ls.toArray(new NewsArticle[ls.size()]));
			news.setNews(newsEntity);
			//--update---author:scott-----date:20161217------------for:获取企业号TOKERN方式改成活的--
			AccessToken accessToken = accountService.getAccessToken(xzkxArticle.getAgentId());
			//--update---author:scott-----date:20161217------------for:获取企业号TOKERN方式改成活的--
	    	System.out.println("....accessToken..."+accessToken);

			receive = JwMessageAPI.sendNewsMessage(news, accessToken.getAccesstoken());
        	System.out.println("....receive..."+receive);
			log.info("message+sendTextMessage",receive );
			// update-begin--Author:malimei Date:2016525 for：添加日志记录
			allmessage.setTopartysId(xzkxArticle.getDepartment());// 部门id多个用逗号隔开
			allmessage.setWxAgentId(xzkxArticle.getAgentId());// 应用id
			allmessage.setMessageType(msgtype);// 消息类型
//			allmessage.setContentId(qywxNewstemplate.getId());// 图文消息的模板id
			allmessage.setContentId("xzkx");// 图文消息的模板id
			allmessage.setReceiveMessage(receive.toJSONString());
			allmessage.setCreateDate(new Date());
			String randomSeed = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			allmessage.setId(randomSeed);
			qywxMessagelogDao.insert(allmessage);// 插入日志
			// update-begin--Author:malimei Date:2016525 for：添加日志记录
			String code = receive.getString("errcode");
			if("0".equals(code)){
				j.setSuccess(true);
				j.setObj("sucess");
				j.setMsg("发送成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			j.setSuccess(false);
			j.setMsg("发送失败");
		}
		return j;

	}


}

