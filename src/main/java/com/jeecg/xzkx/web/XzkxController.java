package com.jeecg.xzkx.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeecg.xzkx.xzkxdata.XzkxDataCollectI;
import com.jeecg.xzkx.xzkxdata.impl.XzkxArticleDataCollect;
import com.jeecg.xzkx.xzkxdata.impl.XzkxIndexDataCollect;
import com.jeecg.xzkx.xzkxdata.impl.XzkxMenuDataCollect;
import com.jeecg.xzkx.common.XzkxConstant;
import com.jeecg.xzkx.common.XzkxDataContent;
import com.jeecg.xzkx.dao.XzkxSiteDao;
import com.jeecg.xzkx.dao.XzkxStyleDao;
import com.jeecg.xzkx.entity.XzkxArticle;
import com.jeecg.xzkx.entity.XzkxSite;
import com.jeecg.xzkx.entity.XzkxStyle;
import com.jeecg.xzkx.util.XzkxFreemarkerHelper;


/**
 * CMS微站核心控制器
 * 
 * @author zhangdaihao
 * 
 */
@Controller
@RequestMapping("/p3/xzkx/xzkxController")
public class XzkxController extends BaseController {
	@Autowired
	private XzkxSiteDao xzkxSiteDao;
	
	@Autowired
	private XzkxStyleDao xzkxStyleDao;

	/**
	 * CMS 数据收集器配置
	 */
	private static Map<String, Object> dataCollectContent = new HashMap<String, Object>();
	static {
		// 载CMS首页数据
		dataCollectContent.put(XzkxConstant.XZKX_PAGE_INDEX, new XzkxIndexDataCollect());
		// CMS栏目数据
		dataCollectContent.put(XzkxConstant.XZKX_PAGE_MENU, new XzkxMenuDataCollect());
		// CMS文章数据
		dataCollectContent.put(XzkxConstant.XZKX_PAGE_ARTICLE, new XzkxArticleDataCollect());
	}


	/**
	 * 页面调整引擎
	 * 
	 * @param request
	 * @param response
	 * @param page
	 *            模板页
	 * @param id
	 *            数据ID
	 */
	@RequestMapping(params = "goPage")
	public void goPage(HttpServletRequest request, HttpServletResponse response, @RequestParam String page) {
		Map<String, String> params = paramsToMap(request);
		
		// ---------------------------------------------------------------------------------------------------------
		// 获取站点的网站样式风格 模块根路径
		String rootUrl = XzkxConstant.XZKX_ROOT_PATH;
		String styleUrl = null;
		
		// 站点信息
		XzkxSite xzkxSiteEntity = null;
		List<XzkxSite> xzkxSiteList = xzkxSiteDao.getAll().getResults();
		if (xzkxSiteList.size() > 0) {
			xzkxSiteEntity = xzkxSiteList.get(0);
		}

		// 站点模板样式
		XzkxStyle xzkxStyleEntity = null;
		// 模板名字 add by liuqiang
		String templateName = null;
		// mod by liuqiang 如果是微相册的请求用默认模板default
		if (xzkxSiteEntity != null) {
			if (xzkxSiteEntity.getSiteTemplateStyle() != null) {
				xzkxStyleEntity = xzkxStyleDao.get(xzkxSiteEntity.getId());
			}
			if (xzkxStyleEntity != null) {
				templateName = File.separator + xzkxStyleEntity.getTemplateUrl();
				styleUrl = rootUrl + "/" + xzkxStyleEntity.getTemplateUrl() + XzkxConstant.XZKX_TEMPL_PACKAGE;
				
			} else {
				templateName = XzkxConstant.XZKX_DEFAULT_TEMPLATE;
				styleUrl = rootUrl + XzkxConstant.XZKX_DEFAULT_STYLE;
			}
		} else {
			templateName = XzkxConstant.XZKX_DEFAULT_TEMPLATE;
			styleUrl = rootUrl + XzkxConstant.XZKX_DEFAULT_STYLE;
			
		}
		params.put(XzkxConstant.XZKX_STYLE_NAME, templateName);
		params.put(XzkxConstant.BASEPATH, request.getContextPath());
		// ---------------------------------------------------------------------------------------------------------
		XzkxFreemarkerHelper xzkxFreemarkerHelper = new XzkxFreemarkerHelper();
		// 调用对应的数据收集器数据
		if (dataCollectContent.get(page) != null) {
			if(page.equals("article")){
				@SuppressWarnings("unchecked")
				Map<String, String> userinfo=(Map<String, String>) request.getSession().getAttribute("QY_USERINFO");
				if(userinfo!=null){
					params.put(XzkxConstant.QYUSERID, userinfo.get("userid"));
					params.put("name", userinfo.get("name"));
					params.put("department", userinfo.get("department"));
				}
			}
			XzkxDataCollectI xzkxDataCollect = (XzkxDataCollectI) dataCollectContent.get(page);
			xzkxDataCollect.collect(params);
		}
		String pageurl = null;
		
		if(page.equals("article")){
			XzkxArticle xzkxArticle = (XzkxArticle)XzkxDataContent.get("article");
			if(xzkxArticle.getId()!=null){
			// 将所有容器的数据访问，加上前缀xzkxData，注意大小写
				pageurl = styleUrl + page + XzkxConstant.XZKX_TEMPL_INDEX;		
			}else{
				pageurl = styleUrl + "error" + XzkxConstant.XZKX_TEMPL_INDEX;					
			}
		}else{
			pageurl = styleUrl + page + XzkxConstant.XZKX_TEMPL_INDEX;					
		}
		String html = xzkxFreemarkerHelper.parseTemplate(pageurl, XzkxDataContent.loadContent());
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.println(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 封装request请求参数到Map里
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> paramsToMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		// 得到枚举类型的参数名称，参数名称若有重复的只能得到第一个
		Enumeration<?> em = request.getParameterNames();
		while (em.hasMoreElements()) {
			String paramName = (String) em.nextElement();
			String paramValue = request.getParameter(paramName);
			// 形成键值对应的map
			params.put(paramName, paramValue);
		}
		return params;
	}
}
