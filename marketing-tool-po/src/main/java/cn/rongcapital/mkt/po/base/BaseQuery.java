/*************************************************
 * @功能简述: PO基类(常规公共字段)
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.po.base;

import cn.rongcapital.mkt.common.constant.ApiConstant;

public class BaseQuery {
	
    private transient Integer startIndex = ApiConstant.PAGE_START_INDEX_DEFAULT;// 开始索引
	
	private transient Integer pageSize = ApiConstant.PAGE_PAGE_SIZE_DEFAULT;//pageSize
	
	private transient String orderField;// 排序字段
	
	private transient String orderFieldType;// 排序字段类型:asc,desc
	
	
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	
	public String getOrderFieldType() {
		if("DESC".equalsIgnoreCase(orderFieldType) || "ASC".equalsIgnoreCase(orderFieldType)) {
			return orderFieldType.toUpperCase();
		}
		return null;
	}

	public void setOrderFieldType(String orderFieldType) {
		this.orderFieldType = orderFieldType;
	}

}
