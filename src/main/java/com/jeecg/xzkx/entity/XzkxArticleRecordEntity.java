package com.jeecg.xzkx.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 描述：公告管理
 * @author: www.jeecg.org
 * @since：2017年01月27日 11时22分16秒 星期五 
 * @version:1.0
 */
public class XzkxArticleRecordEntity implements Serializable{
	private static final long serialVersionUID = 1L;
		/**	 *主键	 */	private String id;	/**	 *账号	 */	private String userid;	/**	 *姓名	 */	private String name;	/**	 *部门	 */	private String department;	/**	 *文章编号	 */	private String articleid;	/**	 *微信账号ID	 */	private String accountid;	/**	 *创建人名称	 */	private String createName;	/**	 *创建人登录名称	 */	private String createBy;	/**	 *创建日期	 */	private Date createDate;	/**	 *更新人名称	 */	private String updateName;	/**	 *更新人登录名称	 */	private String updateBy;	/**	 *更新日期	 */	private Date updateDate;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getUserid() {	    return this.userid;	}	public void setUserid(String userid) {	    this.userid=userid;	}	public String getName() {	    return this.name;	}	public void setName(String name) {	    this.name=name;	}	public String getDepartment() {	    return this.department;	}	public void setDepartment(String department) {	    this.department=department;	}	public String getArticleid() {	    return this.articleid;	}	public void setArticleid(String articleid) {	    this.articleid=articleid;	}	public String getAccountid() {	    return this.accountid;	}	public void setAccountid(String accountid) {	    this.accountid=accountid;	}	public String getCreateName() {	    return this.createName;	}	public void setCreateName(String createName) {	    this.createName=createName;	}	public String getCreateBy() {	    return this.createBy;	}	public void setCreateBy(String createBy) {	    this.createBy=createBy;	}	public Date getCreateDate() {	    return this.createDate;	}	public void setCreateDate(Date createDate) {	    this.createDate=createDate;	}	public String getUpdateName() {	    return this.updateName;	}	public void setUpdateName(String updateName) {	    this.updateName=updateName;	}	public String getUpdateBy() {	    return this.updateBy;	}	public void setUpdateBy(String updateBy) {	    this.updateBy=updateBy;	}	public Date getUpdateDate() {	    return this.updateDate;	}	public void setUpdateDate(Date updateDate) {	    this.updateDate=updateDate;	}
}

