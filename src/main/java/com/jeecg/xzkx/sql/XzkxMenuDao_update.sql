UPDATE xzkx_menu
SET 
	   <#if xzkxMenu.createBy ?exists>
		   CREATE_BY = :xzkxMenu.createBy,
		</#if>
	    <#if xzkxMenu.createDate ?exists>
		   CREATE_DATE = :xzkxMenu.createDate,
		</#if>
	   <#if xzkxMenu.createName ?exists>
		   CREATE_NAME = :xzkxMenu.createName,
		</#if>
	   <#if xzkxMenu.imageHref ?exists>
		   IMAGE_HREF = :xzkxMenu.imageHref,
		</#if>
	   <#if xzkxMenu.imageName ?exists>
		   IMAGE_NAME = :xzkxMenu.imageName,
		</#if>
	   <#if xzkxMenu.name ?exists>
		   NAME = :xzkxMenu.name,
		</#if>
	   <#if xzkxMenu.alias ?exists>
		   ALIAS = :xzkxMenu.alias,
		</#if>
	   <#if xzkxMenu.type ?exists>
		   TYPE = :xzkxMenu.type,
		</#if>
	   <#if xzkxMenu.parentCode ?exists>
		   PARENT_CODE = :xzkxMenu.parentCode,
		</#if>
	   <#if xzkxMenu.href ?exists>
		   HREF = :xzkxMenu.href,
		</#if>
WHERE id = :xzkxMenu.id