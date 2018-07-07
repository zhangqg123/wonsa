UPDATE xzkx_article
SET 
	   <#if xzkxArticle.title ?exists>
		   title = :xzkxArticle.title,
		</#if>
	   <#if xzkxArticle.imageHref ?exists>
		   image_href = :xzkxArticle.imageHref,
		</#if>
	   <#if xzkxArticle.summary ?exists>
		   summary = :xzkxArticle.summary,
		</#if>
	   <#if xzkxArticle.content ?exists>
		   content = :xzkxArticle.content,
		</#if>
	   <#if xzkxArticle.columnId ?exists>
		   column_id = :xzkxArticle.columnId,
		</#if>
	   <#if xzkxArticle.createName ?exists>
		   create_name = :xzkxArticle.createName,
		</#if>
	   <#if xzkxArticle.createBy ?exists>
		   create_by = :xzkxArticle.createBy,
		</#if>
	    <#if xzkxArticle.createDate ?exists>
		   create_date = :xzkxArticle.createDate,
		</#if>
	   <#if xzkxArticle.publish ?exists>
		   publish = :xzkxArticle.publish,
		</#if>
	    <#if xzkxArticle.publishDate ?exists>
		   PUBLISH_DATE = :xzkxArticle.publishDate,
		</#if>
	   <#if xzkxArticle.author ?exists>
		   AUTHOR = :xzkxArticle.author,
		</#if>
	   <#if xzkxArticle.department ?exists>
		   department = :xzkxArticle.department,
		</#if>
	   <#if xzkxArticle.label ?exists>
		   LABEL = :xzkxArticle.label,
		</#if>
	   <#if xzkxArticle.agentId ?exists>
		   AGENT_ID = :xzkxArticle.agentId,
		</#if>		
	   <#if xzkxArticle.extUrl ?exists>
		   ext_url = :xzkxArticle.extUrl,
		</#if>		
WHERE id = :xzkxArticle.id