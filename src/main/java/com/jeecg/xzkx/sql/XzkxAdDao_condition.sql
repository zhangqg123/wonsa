		<#if (xzkxAd.createName)?? && xzkxAd.createName ?length gt 0>
		    /* createName */
			and ca.create_name like CONCAT('%', :xzkxAd.createName ,'%') 
		</#if>
		<#if (xzkxAd.createBy)?? && xzkxAd.createBy ?length gt 0>
		    /* createBy */
			and ca.create_by like CONCAT('%', :xzkxAd.createBy ,'%') 
		</#if>
	    <#if (xzkxAd.createDate)??>
		    /* createDate */
			and ca.create_date = :xzkxAd.createDate
		</#if>
		<#if (xzkxAd.title)?? && xzkxAd.title ?length gt 0>
		    /* 标题 */
			and ca.title like CONCAT('%', :xzkxAd.title ,'%') 
		</#if>
		<#if (xzkxAd.imageHref)?? && xzkxAd.imageHref ?length gt 0>
		    /* 图片地址 */
			and ca.image_href like CONCAT('%', :xzkxAd.imageHref ,'%') 
		</#if>
