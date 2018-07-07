package com.jeecg.xzkx.web;

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

import com.jeecg.xzkx.dao.XzkxArticleDao;
import com.jeecg.xzkx.entity.XzkxArticle;
import com.jeecg.xzkx.entity.XzkxArticleRecordEntity;
import com.jeecg.xzkx.service.XzkxArticleRecordService;

 /**
 * 描述：公告管理
 * @author: www.jeecg.org
 * @since：2017年01月27日 11时22分16秒 星期五 
 * @version:1.0
 */
@Controller
@RequestMapping("/p3/xzkx/xzkxArticleRecord")
public class XzkxArticleRecordController extends BaseController{
  @Autowired
  private XzkxArticleRecordService xzkxArticleRecordService;
  @Autowired
  private XzkxArticleDao xzkxArticleDao;
  
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	  * 列表页面
	  * @return
	  */
	@RequestMapping(params = "list",method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute XzkxArticleRecordEntity query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		try {
		 	LOG.info(request, " back list");
		 	String articleId = query.getArticleid();
		 	XzkxArticle noticleArticle = xzkxArticleDao.get(articleId);
		 	
		 	//分页数据
			MiniDaoPage<XzkxArticleRecordEntity> list =  xzkxArticleRecordService.getAll(query,pageNo,pageSize);
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("xzkxArticleRecord",query);
			velocityContext.put("title",noticleArticle.getTitle());
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
			String viewName = "xzkx/xzkxArticleRecord-list.vm";
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
	public void xzkxArticleRecordDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
			VelocityContext velocityContext = new VelocityContext();
			String viewName = "xzkx/xzkxArticleRecord-detail.vm";
			XzkxArticleRecordEntity xzkxArticleRecord = xzkxArticleRecordService.get(id);
			velocityContext.put("xzkxArticleRecord",xzkxArticleRecord);
			ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(params = "toAdd",method ={RequestMethod.GET, RequestMethod.POST})
	public void toAddDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 String viewName = "teamrank/core/xzkxArticleRecord-add.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 保存信息
	 * @return
	 */
	@RequestMapping(params = "doAdd",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doAdd(@ModelAttribute XzkxArticleRecordEntity xzkxArticleRecord){
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
	 * 跳转到编辑页面
	 * @return
	 */
	@RequestMapping(params="toEdit",method = RequestMethod.GET)
	public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
			 VelocityContext velocityContext = new VelocityContext();
			 XzkxArticleRecordEntity xzkxArticleRecord = xzkxArticleRecordService.get(id);
			 velocityContext.put("xzkxArticleRecord",xzkxArticleRecord);
			 String viewName = "teamrank/core/xzkxArticleRecord-edit.vm";
			 ViewVelocity.view(request,response,viewName,velocityContext);
	}
	

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(params = "doEdit",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doEdit(@ModelAttribute XzkxArticleRecordEntity xzkxArticleRecord){
		AjaxJson j = new AjaxJson();
		try {
			xzkxArticleRecordService.update(xzkxArticleRecord);
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
			    XzkxArticleRecordEntity xzkxArticleRecord = new XzkxArticleRecordEntity();
				xzkxArticleRecord.setId(id);
				xzkxArticleRecordService.delete(xzkxArticleRecord);
				j.setMsg("删除成功");
			} catch (Exception e) {
			    log.info(e.getMessage());
				j.setSuccess(false);
				j.setMsg("删除失败");
			}
			return j;
	}


}

