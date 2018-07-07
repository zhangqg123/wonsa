		<#if ( xzkxArticleRecord.userid )?? && xzkxArticleRecord.userid ?length gt 0>
		    /* 账号 */
			and nar.userid like CONCAT('%', :xzkxArticleRecord.userid ,'%') 
		</#if>
		<#if ( xzkxArticleRecord.name )?? && xzkxArticleRecord.name ?length gt 0>
		    /* 姓名 */
			and nar.name like CONCAT('%', :xzkxArticleRecord.name ,'%') 
		</#if>
		<#if ( xzkxArticleRecord.department )?? && xzkxArticleRecord.department ?length gt 0>
		    /* 部门 */
			and nar.department like CONCAT('%', :xzkxArticleRecord.department ,'%') 
		</#if>
		<#if ( xzkxArticleRecord.articleid )?? && xzkxArticleRecord.articleid ?length gt 0>
		    /* 文章编号 */
			and nar.articleid like CONCAT('%', :xzkxArticleRecord.articleid ,'%') 
		</#if>
		<#if ( xzkxArticleRecord.accountid )?? && xzkxArticleRecord.accountid ?length gt 0>
		    /* 微信账号ID */
			and nar.accountid like CONCAT('%', :xzkxArticleRecord.accountid ,'%') 
		</#if>
		<#if ( xzkxArticleRecord.createName )?? && xzkxArticleRecord.createName ?length gt 0>
		    /* 创建人名称 */
			and nar.create_name like CONCAT('%', :xzkxArticleRecord.createName ,'%') 
		</#if>
		<#if ( xzkxArticleRecord.createBy )?? && xzkxArticleRecord.createBy ?length gt 0>
		    /* 创建人登录名称 */
			and nar.create_by like CONCAT('%', :xzkxArticleRecord.createBy ,'%') 
		</#if>
	    <#if ( xzkxArticleRecord.createDate )??>
		    /* 创建日期 */
			and nar.create_date = :xzkxArticleRecord.createDate
		</#if>
		<#if ( xzkxArticleRecord.updateName )?? && xzkxArticleRecord.updateName ?length gt 0>
		    /* 更新人名称 */
			and nar.update_name like CONCAT('%', :xzkxArticleRecord.updateName ,'%') 
		</#if>
		<#if ( xzkxArticleRecord.updateBy )?? && xzkxArticleRecord.updateBy ?length gt 0>
		    /* 更新人登录名称 */
			and nar.update_by like CONCAT('%', :xzkxArticleRecord.updateBy ,'%') 
		</#if>
	    <#if ( xzkxArticleRecord.updateDate )??>
		    /* 更新日期 */
			and nar.update_date = :xzkxArticleRecord.updateDate
		</#if>
