UPDATE xzkx_site
SET 
	   <#if xzkxSite.companyTel ?exists>
		   COMPANY_TEL = :xzkxSite.companyTel,
		</#if>
	    <#if xzkxSite.createDate ?exists>
		   CREATE_DATE = :xzkxSite.createDate,
		</#if>
	   <#if xzkxSite.createName ?exists>
		   CREATE_NAME = :xzkxSite.createName,
		</#if>
	   <#if xzkxSite.siteLogo ?exists>
		   SITE_LOGO = :xzkxSite.siteLogo,
		</#if>
	   <#if xzkxSite.siteName ?exists>
		   SITE_NAME = :xzkxSite.siteName,
		</#if>
	   <#if xzkxSite.siteTemplateStyle ?exists>
		   SITE_TEMPLATE_STYLE = :xzkxSite.siteTemplateStyle,
		</#if>
	    <#if xzkxSite.updateDate ?exists>
		   UPDATE_DATE = :xzkxSite.updateDate,
		</#if>
	   <#if xzkxSite.updateName ?exists>
		   UPDATE_NAME = :xzkxSite.updateName,
		</#if>
		<#if xzkxSite.agentId ?exists>
		   AGENT_ID = :xzkxSite.agentId,
		</#if>

WHERE id = :xzkxSite.id