SELECT * FROM xzkx_article AS qa WHERE 1=1
<#if coulmnId ?exists && coulmnId?length gt 0>
	and column_id = :coulmnId
</#if>
order by qa.PUBLISH_DATE desc