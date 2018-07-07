package com.jeecg.xzkx.util;

import org.jeecgframework.p3.core.common.utils.StringUtil;

import com.jeecg.xzkx.dao.XzkxMenuDao;

/**
 * 根据 P3 1.0的TableTree 的页面组件规则的ID 生成器
 * (代码逻辑摘抄自 jeecg)
 * @author jg_huangxg
 *
 */
public class SimpleTreeIdBuild {
	
	public String getId(XzkxMenuDao xzkxMenuDao,String parentCode) {
		
		String idCode = null;
		String localMaxCode = null;
		if (StringUtil.isEmpty(parentCode)) {//无上级
			localMaxCode = getMaxLocalCode(xzkxMenuDao,null);
			idCode = YouBianCodeUtil.getNextYouBianCode(localMaxCode);
		}else{//有上级
			localMaxCode = getMaxLocalCode(xzkxMenuDao,parentCode);
			idCode = YouBianCodeUtil.getSubYouBianCode(parentCode, localMaxCode);
		}
		
		return idCode;
		
	}
	
	private synchronized String getMaxLocalCode(XzkxMenuDao xzkxMenuDao,String parentCode){
		if(StringUtil.isEmpty(parentCode)){
			parentCode = "";
		}
		int localCodeLength = parentCode.length() + YouBianCodeUtil.zhanweiLength;

		return xzkxMenuDao.getMaxLocalCode(String.valueOf(localCodeLength), parentCode);
	}
}
