UPDATE xzkx_article_record
SET 
	   <#if xzkxArticleRecord.userid ?exists>
		   userid = :xzkxArticleRecord.userid,
		</#if>
	   <#if xzkxArticleRecord.name ?exists>
		   name = :xzkxArticleRecord.name,
		</#if>
	   <#if xzkxArticleRecord.department ?exists>
		   department = :xzkxArticleRecord.department,
		</#if>
	   <#if xzkxArticleRecord.articleid ?exists>
		   articleid = :xzkxArticleRecord.articleid,
		</#if>
	   <#if xzkxArticleRecord.accountid ?exists>
		   accountid = :xzkxArticleRecord.accountid,
		</#if>
	   <#if xzkxArticleRecord.createName ?exists>
		   create_name = :xzkxArticleRecord.createName,
		</#if>
	   <#if xzkxArticleRecord.createBy ?exists>
		   create_by = :xzkxArticleRecord.createBy,
		</#if>
	    <#if xzkxArticleRecord.createDate ?exists>
		   create_date = :xzkxArticleRecord.createDate,
		</#if>
	   <#if xzkxArticleRecord.updateName ?exists>
		   update_name = :xzkxArticleRecord.updateName,
		</#if>
	   <#if xzkxArticleRecord.updateBy ?exists>
		   update_by = :xzkxArticleRecord.updateBy,
		</#if>
	    <#if xzkxArticleRecord.updateDate ?exists>
		   update_date = :xzkxArticleRecord.updateDate,
		</#if>
WHERE id = :xzkxArticleRecord.id