package cn.rongcapital.mkt.vo;

/**
 * \u8F93\u5165
 **/

import com.fasterxml.jackson.annotation.JsonProperty;
public class SegmentTitle   {
  
  private String name = null;
  private String operator = null;
  private Integer status = null;

  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getOperator() {
    return operator;
  }
  public void setOperator(String operator) {
    this.operator = operator;
  }

  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
}

