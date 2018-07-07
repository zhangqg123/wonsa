package com.jeecg.xzkx.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.page.PaginatedList;
import org.jeecgframework.p3.core.page.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.jeecg.xzkx.dao.XzkxArticleDao;
import com.jeecg.xzkx.dao.XzkxMenuDao;
import com.jeecg.xzkx.entity.Depart;
import com.jeecg.xzkx.entity.XzkxArticle;
import com.jeecg.xzkx.entity.XzkxArticleRecordEntity;
import com.jeecg.xzkx.entity.XzkxMenu;
import com.jeecg.xzkx.service.XzkxArticleRecordService;
import com.jeecg.xzkx.util.XzkxResourceUtil;
import com.jeecg.xzkx.util.ImageUtil;
import com.jeecg.qywx.account.service.AccountService;
import com.jeecg.qywx.api.core.common.AccessToken;
import com.jeecg.qywx.api.message.JwMessageAPI;
import com.jeecg.qywx.api.message.vo.News;
import com.jeecg.qywx.api.message.vo.NewsArticle;
import com.jeecg.qywx.api.message.vo.NewsEntity;
import com.jeecg.qywx.base.dao.QywxGroupDao;
import com.jeecg.qywx.base.dao.QywxGzuserinfoDao;
import com.jeecg.qywx.base.dao.QywxMessagelogDao;
import com.jeecg.qywx.base.entity.QywxGroup;
import com.jeecg.qywx.base.entity.QywxGzuserinfo;
import com.jeecg.qywx.base.entity.QywxMessagelog;

 /**
 * 描述：首页
 * @author Alex
 * @version:1.0
 */
@Controller
@RequestMapping("/p3/xzkx/index")
public class XzkxIndexController extends BaseController{
	@Autowired
	private XzkxArticleRecordService xzkxArticleRecordService;
	@Autowired
	private XzkxMenuDao xzkxMenuDao;
	@Autowired
	private XzkxArticleDao xzkxArticleDao;
	@Autowired
	private QywxGzuserinfoDao qywxGzuserinfoDao;
	@Autowired
	private QywxGroupDao qywxGroupDao;
	@Autowired 
	private AccountService accountService;
	@Autowired
	private QywxMessagelogDao qywxMessagelogDao;

	/**
	 * 保存信息
	 * @return
	 */
	@RequestMapping(params = "addRecord",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson addRecord(@ModelAttribute XzkxArticleRecordEntity xzkxArticleRecord){
		AjaxJson j = new AjaxJson();
		try {	
		    String randomSeed = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			java.util.Date  date=new java.util.Date();
//			java.sql.Date createTime=new java.sql.Date(date.getTime());
			xzkxArticleRecord.setId(randomSeed);
			xzkxArticleRecord.setCreateDate(date);
			xzkxArticleRecordService.insert(xzkxArticleRecord);
			j.setMsg("签收成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("签收失败");
		}
		return j;
	}

	/**
	  * 列表页面
	  * @return
	  */
	@RequestMapping(params = "articleList",method = {RequestMethod.GET,RequestMethod.POST})
	public void articleList(@ModelAttribute XzkxArticle query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
							
			try {
			 	LOG.info(request, " back list");
				String xzkxUserName=(String) request.getSession().getAttribute("userName");		
				String roles=(String) request.getSession().getAttribute("roles");		
				VelocityContext velocityContext = new VelocityContext();
				XzkxMenu xzkxMenu = xzkxMenuDao.getMenuAlias(xzkxUserName);
				if(xzkxMenu!=null){
					query.setColumnId(xzkxMenu.getId());
				}else{
					if(roles.contains("admin")){
//						MiniDaoPage<XzkxMenu> menuList = xzkxMenuDao.getTree();
//						xzkxMenuList = menuList.getResults();
					}else{				
						query.setColumnId("meiyou");
						velocityContext.put("meiyou", "meiyou");
					}
				}
			 	//分页数据
				MiniDaoPage<XzkxArticle> list =  xzkxArticleDao.getAll(query,pageNo,pageSize);
//				VelocityContext velocityContext = new VelocityContext();
				velocityContext.put("domain", XzkxResourceUtil.getDomain());
//				velocityContext.put("agentId", XzkxResourceUtil.getAgentId());
				velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
				String viewName = "xzkx/main/xzkxArticleList.vm";
				ViewVelocity.view(request,response,viewName,velocityContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping(params = "toAdd",method ={RequestMethod.GET, RequestMethod.POST})
	public void toAddDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String viewName=null;
		String xzkxUserName=(String) request.getSession().getAttribute("userName");		
		String departId=(String) request.getSession().getAttribute("departId");		
		String roles=(String) request.getSession().getAttribute("roles");		
		XzkxMenu xzkxMenu = xzkxMenuDao.getMenuAlias(xzkxUserName);
		if(xzkxMenu==null){
			if(roles.contains("admin")){
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
		
			viewName = "xzkx/main/xzkxArticleAdd.vm";
			velocityContext.put("menuList",SystemTools.convertPaginatedList(menuList));
			velocityContext.put("agentId", agentId);
			ViewVelocity.view(request,response,viewName,velocityContext);
		}
	}

	@RequestMapping(params = "doAdd",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doAdd(@ModelAttribute XzkxArticle xzkxArticle){
		AjaxJson j = new AjaxJson();
		try {
		    String randomSeed = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		    xzkxArticle.setId(randomSeed);
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
	@RequestMapping(params="toEdit",method = RequestMethod.GET)
	public void toEdit(@RequestParam(required = true, value = "id" ) String id, String type,
			HttpServletResponse response,HttpServletRequest request) throws Exception{
		String xzkxUserName=(String) request.getSession().getAttribute("userName");		
		String roles=(String) request.getSession().getAttribute("roles");		
		XzkxMenu xzkxMenu = xzkxMenuDao.getMenuAlias(xzkxUserName);
		if(xzkxMenu==null){
			if(roles.contains("admin")){
				xzkxMenu=new XzkxMenu();
			}
		}
		if(xzkxMenu!=null){
			MiniDaoPage<XzkxMenu> menuList =  xzkxMenuDao.getAll(xzkxMenu,1,199);
	
			VelocityContext velocityContext = new VelocityContext();
			String viewName = null;
			velocityContext.put("menuList",SystemTools.convertPaginatedList(menuList));
			XzkxArticle xzkxArticle = xzkxArticleDao.get(id);
			velocityContext.put("xzkxArticle",xzkxArticle);
				viewName = "xzkx/main/xzkxArticleEdit.vm";
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
			xzkxArticleDao.update(xzkxArticle);
			j.setMsg("编辑成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("编辑失败");
		}
		return j;
	}

	@RequestMapping(params="doDelete",method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id,HttpServletRequest request){
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
			String xzkxArticeId = xzkx[1];
			XzkxArticle xzkxArticle = xzkxArticleDao.get(xzkxArticeId);
			String toparty = xzkxArticle.getDepartment();// 部门Id
			String topartys = toparty.replaceAll(",", "|");

			QywxMessagelog allmessage = new QywxMessagelog();
//			// 发送图文信信息
//			String templateId = request.getParameter("templateId");
//			QywxNewstemplate qywxNewstemplate=qywxNewstemplateDao.getName("通知公告");
				// 获取图文信息
//			List<QywxNewsitem> item = qywxNewsitemDao.getALLNews(qywxNewstemplate.getId());
			News news = new News();
			news.setToparty(topartys);// 部门Id
			news.setTouser("");//用户列表id群发
			news.setMsgtype("news");// 参数类型
			news.setAgentid(Integer.valueOf(xzkxArticle.getAgentId()));// 企业应用id整型

			List<NewsArticle> ls = new ArrayList<NewsArticle>();
//			for (int i = 0; i < item.size(); i++) {
//				String picurl = item.get(i).getImagePath();
				String picurl = "upload/img/xzkx/"+xzkxArticle.getImageHref();
				String title = xzkxArticle.getTitle();
				String description = xzkxArticle.getSummary();
				String url = xzkxUrl;
				if(xzkxArticle.getExtUrl().contains("http://")){
					url=xzkxArticle.getExtUrl();
				}
				NewsArticle newsarticle = new NewsArticle();
				newsarticle.setDescription(description);
				// 在配置文件中配置路径
			    System.out.println("-----newarticle------"+newsarticle);
				String domain = XzkxResourceUtil.getDomain();
		    	System.out.println("-----domain----"+domain);
				newsarticle.setPicurl(domain + "/" + picurl);
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

//			AccessToken accessToken = accountService.getAccessToken();
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

	@RequestMapping(params = "recordList",method = {RequestMethod.GET,RequestMethod.POST})
	public void recordList(@ModelAttribute XzkxArticleRecordEntity query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		try {
		 	LOG.info(request, " back list");

		 	String articleId = query.getArticleid();
		 	XzkxArticle noticleArticle = xzkxArticleDao.get(articleId);
		 	String mainPageNo = request.getParameter("mainPageNo");
		 	//分页数据
			MiniDaoPage<XzkxArticleRecordEntity> list =  xzkxArticleRecordService.getAll(query,pageNo,pageSize);
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("title",noticleArticle.getTitle());
			velocityContext.put("mainPageNo",mainPageNo);
			velocityContext.put("xzkxArticleRecord",query);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
			String viewName = "xzkx/main/xzkxArticleRecordList.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

	
	@RequestMapping(params = "noRecordList",method = {RequestMethod.GET,RequestMethod.POST})
	public void noRecordList(@ModelAttribute XzkxArticleRecordEntity query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		try {
		 	LOG.info(request, " back list");
		 	String mainPageNo = request.getParameter("mainPageNo");
		 	String arId = query.getArticleid();
		 	XzkxArticle noticleArticle = xzkxArticleDao.get(arId);
		 	String articleId = "\""+arId+"\"";
			String deps = noticleArticle.getDepartment();
			Map<String,String> depMap=new HashMap<String,String>();
			StringBuffer sbgroup = new StringBuffer();
			String[] dep = deps.split(",");
			for(int i=0;i<deps.split(",").length;i++){
				List<QywxGroup> childrenGroups = qywxGroupDao.getChildrenGroup(dep[i]);				
				for (QywxGroup qywxGroup: childrenGroups) {
					if (qywxGroup != null) {
						String id = qywxGroup.getId();
						sbgroup.append(id+",");
						depMap.put(qywxGroup.getId(),qywxGroup.getName());
					}
				}
			}
			
			String departments = sbgroup.toString().substring(0,sbgroup.toString().length()-1);
			
			QywxGzuserinfo qywxGzuserinfo=new QywxGzuserinfo();
			qywxGzuserinfo.setDepartmentSql(departments);

			MiniDaoPage<QywxGzuserinfo> list = qywxGzuserinfoDao.getNotin(qywxGzuserinfo, articleId, pageNo,pageSize);

			//分页数据
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("depMap",depMap);
			velocityContext.put("title",noticleArticle.getTitle());
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
			velocityContext.put("mainPageNo",mainPageNo);
			String viewName = "xzkx/main/xzkxArticleNoRecordList.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "myArticleList",method = {RequestMethod.GET,RequestMethod.POST})
	public void myArticleList(@ModelAttribute XzkxArticle query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		try {
		 	LOG.info(request, " back list");
		 	//分页数据
			Map<String, String> userinfo=(Map<String, String>) request.getSession().getAttribute("QY_USERINFO");

			String userid = userinfo.get("userid");
			String depId=userinfo.get("depId");
//			String userid = "lifei";
//			String depId= "80129";
		 	
			List<QywxGroup> parentGroups = qywxGroupDao.getParentGroup(depId);

			StringBuffer sbgroup = new StringBuffer();
			for (QywxGroup qywxGroup: parentGroups) {
				if (qywxGroup != null) {
					String id = qywxGroup.getId();
					sbgroup.append(id+",");
				}
			}
			
			String departments = sbgroup.toString().substring(0,sbgroup.toString().length()-1);
			query.setDepartment(departments);

			MiniDaoPage<XzkxArticle> list =  xzkxArticleDao.getMyXzkxArticle(query,pageNo,pageSize);
			for(XzkxArticle na:list.getResults()){
				if( xzkxArticleRecordService.getArticleRecord(na.getId(), userid).size()>0){
					na.setQianshou("qianshou");
				}
			}
			
			VelocityContext velocityContext = new VelocityContext();
			PaginatedList aa = SystemTools.convertPaginatedList(list);
//			log.info("index:"+aa.getIndex()+aa.getEndRow()+aa.getNextPage()+aa.getPageSize()+
//					aa.getPreviousPage()+aa.getStartRow()+aa.getTotalItem()+aa.getTotalPage());
			velocityContext.put("domain",XzkxResourceUtil.getDomain());
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
			String viewName = "xzkx/main/myXzkxArticleList.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * 上传文件信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "doUpload", method = RequestMethod.POST)
    @ResponseBody
    public  AjaxJson doUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
    	AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		try {
			String basePath = request.getSession().getServletContext().getRealPath("/");
			//获取所有文件名称  
			Iterator<String> it = request.getFileNames();  
			while(it.hasNext()){  
			    //根据文件名称取文件  
			    MultipartFile multifile = request.getFile(it.next());  
//			    String fileName = multifile.getOriginalFilename(); 
		        String realFilename=multifile.getOriginalFilename();
		        String fileExtension = realFilename.substring(realFilename.lastIndexOf("."));
				String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";// 初始化种子
		        String fileName=RandomStringUtils.random(8, str)+System.currentTimeMillis()+fileExtension;
			    String filePath = "upload/img/xzkx/"; 
			    File file = new File(basePath+filePath);
				if (!file.exists()) {
					file.mkdir();// 创建文件根目录
				}
				filePath = filePath+fileName;
			    String savePath = basePath+filePath;
			    System.out.println(savePath);
			    File newFile = new File(savePath);  
			    //上传的文件写入到指定的文件中  
			    multifile.transferTo(newFile);  
				ImageUtil.zoomImageScale(newFile, savePath,300);
			    attributes.put("url", filePath);
			    attributes.put("fileKey", fileName);
			}
			j.setMsg("文件上传成功");
			j.setAttributes(attributes);
		}  catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("文件上传失败");
		}  

		return j;
    }
	
	/**
	 * 跳转到欢迎页
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public void index(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception{
		 try {
//			 LOG.info(request, " back home");
			 VelocityContext velocityContext = new VelocityContext();

			 String viewName = "xzkx/main/index.vm";
			 ViewVelocity.view(request,response,viewName,velocityContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

