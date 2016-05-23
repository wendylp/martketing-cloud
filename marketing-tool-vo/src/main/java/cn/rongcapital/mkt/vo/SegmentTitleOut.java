package cn.rongcapital.mkt.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
@javax.xml.bind.annotation.XmlRootElement
public class SegmentTitleOut
extends BaseOutput
{
  
  private String name = null;
  private String oper = null;
  private Integer segmentId = null;
  private Integer status = null;
  SegmentTitleOut(){}
  public SegmentTitleOut(int code, String msg){
    super(code,msg);
  }
  
  
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  
  @JsonProperty("oper")
  public String getOper() {
    return oper;
  }
  public void setOper(String oper) {
    this.oper = oper;
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

