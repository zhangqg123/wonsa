SELECT * FROM xzkx_article AS na where 1=1
<#if xzkxArticle.department ?exists && xzkxArticle.department ?length gt 0>
	and concat(',',na.department,',') regexp concat(',(',replace(:xzkxArticle.department,',','|'),'),')
</#if> 
order by na.PUBLISH_DATE desc