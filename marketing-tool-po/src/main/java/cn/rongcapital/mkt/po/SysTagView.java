package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;
/*************************************************
 * @功能简述: 系统标签名称与数据库视图名称映射实体
 * @项目名称: marketing cloud
 * @see: 
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/10/13
 * @复审人: 
*************************************************/
public class SysTagView extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String viewName;	//数据库视图名称

    private String viewDesc;	//视图描述
    
    private int status;			//状态
    
    private String tagName;		//标签名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName == null ? null : viewName.trim();
    }

    public String getViewDesc() {
        return viewDesc;
    }

    public void setViewDesc(String viewDesc) {
        this.viewDesc = viewDesc == null ? null : viewDesc.trim();
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}