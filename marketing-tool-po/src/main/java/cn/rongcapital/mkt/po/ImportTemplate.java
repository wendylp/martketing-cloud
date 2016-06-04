package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ImportTemplate extends BaseQuery {
    private Integer id;

    private Integer templType;

    private String templName;

    private String fieldName;

    private String fieldCode;

    private Boolean selected;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplType() {
        return templType;
    }

    public void setTemplType(Integer templType) {
        this.templType = templType;
    }

    public String getTemplName() {
        return templName;
    }

    public void setTemplName(String templName) {
        this.templName = templName == null ? null : templName.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
