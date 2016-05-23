package cn.rongcapital.mkt.vo;

/**
 * \u8F93\u5165
 **/

import com.fasterxml.jackson.annotation.JsonProperty;
@javax.xml.bind.annotation.XmlRootElement
public class SegmentTitleIn
//extends BaseOutput
{
  
  private String name = null;
  private String operator = null;
  private Integer segmentId = null;
  private Integer status = null;
//  SegmentTitleIn(){}
//  public SegmentTitleIn(int code, String msg){
//    super(code,msg);
//  }
  
  
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  
  @JsonProperty("operator")
  public String getOperator() {
    return operator;
  }
  public void setOperator(String operator) {
    this.operator = operator;
  }
  
  
  @JsonProperty("segmentId")
  public Integer getSegmentId() {
    return segmentId;
  }
  public void setSegmentId(Integer segmentId) {
    this.segmentId = segmentId;
  }
  
  
  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  }

