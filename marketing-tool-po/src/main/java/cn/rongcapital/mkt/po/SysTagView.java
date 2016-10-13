package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SysTagView extends BaseQuery {
    private Integer id;

    private String viewName;

    private String viewDesc;
    
    private int status;

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

}