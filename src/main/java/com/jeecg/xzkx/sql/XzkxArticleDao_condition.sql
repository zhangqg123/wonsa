		<#if (xzkxArticle.title)?? && xzkxArticle.title ?length gt 0>
		    /* 标题 */
			and ca.title like CONCAT('%', :xzkxArticle.title ,'%') 
		</#if>
		<#if (xzkxArticle.imageHref)?? && xzkxArticle.imageHref ?length gt 0>
		    /* 图片地址 */
			and ca.image_href like CONCAT('%', :xzkxArticle.imageHref ,'%') 
		</#if>
		<#if (xzkxArticle.summary)?? && xzkxArticle.summary ?length gt 0>
		    /* summary */
			and ca.summary like CONCAT('%', :xzkxArticle.summary ,'%') 
		</#if>
		<#if (xzkxArticle.content)?? && xzkxArticle.content ?length gt 0>
		    /* 内容 */
			and ca.content like CONCAT('%', :xzkxArticle.content ,'%') 
		</#if>
		<#if (xzkxArticle.columnId)?? && xzkxArticle.columnId ?length gt 0>
		    /* 栏目id */
			and ca.column_id like CONCAT('%', :xzkxArticle.columnId ,'%') 
		</#if>
		<#if (xzkxArticle.createName)?? && xzkxArticle.createName ?length gt 0>
		    /* 创建人 */
			and ca.create_name like CONCAT('%', :xzkxArticle.createName ,'%') 
		</#if>
		<#if (xzkxArticle.createBy)?? && xzkxArticle.createBy ?length gt 0>
		    /* 创建人id */
			and ca.create_by like CONCAT('%', :xzkxArticle.createBy ,'%') 
		</#if>
	    <#if (xzkxArticle.createDate)??>
		    /* 创建日期 */
			and ca.create_date = :xzkxArticle.createDate
		</#if>
		<#if (xzkxArticle.publish)?? && xzkxArticle.publish ?length gt 0>
		    /* publish */
			and ca.publish like CONCAT('%', :xzkxArticle.publish ,'%') 
		</#if>
	    <#if (xzkxArticle.publishDate)??>
		    /* PUBLISH_DATE */
			and ca.PUBLISH_DATE = :xzkxArticle.publishDate
		</#if>
		<#if (xzkxArticle.author)?? && xzkxArticle.author ?length gt 0>
		    /* AUTHOR */
			and ca.AUTHOR like CONCAT('%', :xzkxArticle.author ,'%') 
		</#if>
		<#if (xzkxArticle.department)?? && xzkxArticle.department ?length gt 0>
		    /* AUTHOR */
			and ca.department like CONCAT('%', :xzkxArticle.department ,'%') 
		</#if>
		<#if (xzkxArticle.label)?? && xzkxArticle.label ?length gt 0>
		    /* LABEL */
			and ca.LABEL like CONCAT('%', :xzkxArticle.label ,'%') 
		</#if>
