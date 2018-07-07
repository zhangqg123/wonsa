UPDATE xzkx_ad
SET 
	   <#if xzkxAd.createName ?exists>
		   create_name = :xzkxAd.createName,
		</#if>
	   <#if xzkxAd.createBy ?exists>
		   create_by = :xzkxAd.createBy,
		</#if>
	    <#if xzkxAd.createDate ?exists>
		   create_date = :xzkxAd.createDate,
		</#if>
	   <#if xzkxAd.title ?exists>
		   title = :xzkxAd.title,
		</#if>
	   <#if xzkxAd.imageHref ?exists>
		   image_href = :xzkxAd.imageHref,
		</#if>
WHERE id = :xzkxAd.id