		<#if (xzkxMenu.createBy)?? && xzkxMenu.createBy ?length gt 0>
		    /* CREATE_BY */
			and cm.CREATE_BY like CONCAT('%', :xzkxMenu.createBy ,'%') 
		</#if>
	    <#if (xzkxMenu.createDate)??>
		    /* CREATE_DATE */
			and cm.CREATE_DATE = :xzkxMenu.createDate
		</#if>
		<#if (xzkxMenu.createName)?? && xzkxMenu.createName ?length gt 0>
		    /* CREATE_NAME */
			and cm.CREATE_NAME like CONCAT('%', :xzkxMenu.createName ,'%') 
		</#if>
		<#if (xzkxMenu.imageHref)?? && xzkxMenu.imageHref ?length gt 0>
		    /* IMAGE_HREF */
			and cm.IMAGE_HREF like CONCAT('%', :xzkxMenu.imageHref ,'%') 
		</#if>
		<#if (xzkxMenu.imageName)?? && xzkxMenu.imageName ?length gt 0>
		    /* IMAGE_NAME */
			and cm.IMAGE_NAME like CONCAT('%', :xzkxMenu.imageName ,'%') 
		</#if>
		<#if (xzkxMenu.name)?? && xzkxMenu.name ?length gt 0>
		    /* NAME */
			and cm.NAME like CONCAT('%', :xzkxMenu.name ,'%') 
		</#if>
		<#if (xzkxMenu.type)?? && xzkxMenu.type ?length gt 0>
		    /* 类型 */
			and cm.TYPE like CONCAT('%', :xzkxMenu.type ,'%') 
		</#if>
		<#if (xzkxMenu.parentCode)?? && xzkxMenu.parentCode ?length gt 0>
		    /* PARENT_CODE */
			and cm.PARENT_CODE like CONCAT('%', :xzkxMenu.parentCode ,'%') 
		</#if>
		<#if (xzkxMenu.href)?? && xzkxMenu.href ?length gt 0>
		    /* HREF */
			and cm.HREF like CONCAT('%', :xzkxMenu.href ,'%') 
		</#if>
