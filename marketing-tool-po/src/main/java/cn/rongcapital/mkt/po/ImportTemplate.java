package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ImportTemplate extends BaseQuery {
    private Integer id;

    private String templType;

    private String templName;

    private String fieldName;

    private Byte fieldCode;

    private Boolean selected;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplType() {
        return templType;
    }

    public void setTemplType(String templType) {
        this.templType = templType == null ? null : templType.trim();
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

    public Byte getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(Byte fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
