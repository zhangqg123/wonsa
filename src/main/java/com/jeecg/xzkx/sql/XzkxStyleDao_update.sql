UPDATE xzkx_style
SET 
	    <#if xzkxStyle.createDate ?exists>
		   CREATE_DATE = :xzkxStyle.createDate,
		</#if>
	   <#if xzkxStyle.createName ?exists>
		   CREATE_NAME = :xzkxStyle.createName,
		</#if>
	   <#if xzkxStyle.reviewImgUrl ?exists>
		   REVIEW_IMG_URL = :xzkxStyle.reviewImgUrl,
		</#if>
	   <#if xzkxStyle.templateName ?exists>
		   TEMPLATE_NAME = :xzkxStyle.templateName,
		</#if>
	   <#if xzkxStyle.templateUrl ?exists>
		   TEMPLATE_URL = :xzkxStyle.templateUrl,
		</#if>
	    <#if xzkxStyle.updateDate ?exists>
		   UPDATE_DATE = :xzkxStyle.updateDate,
		</#if>
	   <#if xzkxStyle.updateName ?exists>
		   UPDATE_NAME = :xzkxStyle.updateName,
		</#if>
WHERE id = :xzkxStyle.id