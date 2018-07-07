	    <#if (xzkxStyle.createDate)??>
		    /* CREATE_DATE */
			and cs.CREATE_DATE = :xzkxStyle.createDate
		</#if>
		<#if (xzkxStyle.createName)?? && xzkxStyle.createName ?length gt 0>
		    /* CREATE_NAME */
			and cs.CREATE_NAME like CONCAT('%', :xzkxStyle.createName ,'%') 
		</#if>
		<#if (xzkxStyle.reviewImgUrl)?? && xzkxStyle.reviewImgUrl ?length gt 0>
		    /* REVIEW_IMG_URL */
			and cs.REVIEW_IMG_URL like CONCAT('%', :xzkxStyle.reviewImgUrl ,'%') 
		</#if>
		<#if (xzkxStyle.templateName)?? && xzkxStyle.templateName ?length gt 0>
		    /* TEMPLATE_NAME */
			and cs.TEMPLATE_NAME like CONCAT('%', :xzkxStyle.templateName ,'%') 
		</#if>
		<#if (xzkxStyle.templateUrl)?? && xzkxStyle.templateUrl ?length gt 0>
		    /* TEMPLATE_URL */
			and cs.TEMPLATE_URL like CONCAT('%', :xzkxStyle.templateUrl ,'%') 
		</#if>
	    <#if (xzkxStyle.updateDate)??>
		    /* UPDATE_DATE */
			and cs.UPDATE_DATE = :xzkxStyle.updateDate
		</#if>
		<#if (xzkxStyle.updateName)?? && xzkxStyle.updateName ?length gt 0>
		    /* UPDATE_NAME */
			and cs.UPDATE_NAME like CONCAT('%', :xzkxStyle.updateName ,'%') 
		</#if>
