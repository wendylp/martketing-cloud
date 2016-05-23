/*
 * Copyright (c) 2016 www.rongcapital.cn All rights reserved.
 */
package cn.rongcapital.mkt.po.base;

/**
 * 领域模型基类(常规公共字段)<br/>
 * 一律使用引用类型
 * @author songshitao
 */
public class BaseQuery {
	
	private static final long serialVersionUID = 1L;
	
	private transient Integer startIndex;// 开始索引
	
	private transient Integer pageSize;//pageSize
	
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
