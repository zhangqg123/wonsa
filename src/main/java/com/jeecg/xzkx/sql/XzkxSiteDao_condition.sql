		<#if (xzkxSite.companyTel)?? && xzkxSite.companyTel ?length gt 0>
		    /* COMPANY_TEL */
			and cs.COMPANY_TEL like CONCAT('%', :xzkxSite.companyTel ,'%') 
		</#if>
	    <#if (xzkxSite.createDate)??>
		    /* CREATE_DATE */
			and cs.CREATE_DATE = :xzkxSite.createDate
		</#if>
		<#if (xzkxSite.createName)?? && xzkxSite.createName ?length gt 0>
		    /* CREATE_NAME */
			and cs.CREATE_NAME like CONCAT('%', :xzkxSite.createName ,'%') 
		</#if>
		<#if (xzkxSite.siteLogo)?? && xzkxSite.siteLogo ?length gt 0>
		    /* SITE_LOGO */
			and cs.SITE_LOGO like CONCAT('%', :xzkxSite.siteLogo ,'%') 
		</#if>
		<#if (xzkxSite.siteName)?? && xzkxSite.siteName ?length gt 0>
		    /* SITE_NAME */
			and cs.SITE_NAME like CONCAT('%', :xzkxSite.siteName ,'%') 
		</#if>
		<#if (xzkxSite.siteTemplateStyle)?? && xzkxSite.siteTemplateStyle ?length gt 0>
		    /* SITE_TEMPLATE_STYLE */
			and cs.SITE_TEMPLATE_STYLE like CONCAT('%', :xzkxSite.siteTemplateStyle ,'%') 
		</#if>
	    <#if (xzkxSite.updateDate)??>
		    /* UPDATE_DATE */
			and cs.UPDATE_DATE = :xzkxSite.updateDate
		</#if>
		<#if (xzkxSite.updateName)?? && xzkxSite.updateName ?length gt 0>
		    /* UPDATE_NAME */
			and cs.UPDATE_NAME like CONCAT('%', :xzkxSite.updateName ,'%') 
		</#if>
