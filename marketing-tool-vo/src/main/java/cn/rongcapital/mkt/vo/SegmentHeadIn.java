package cn.rongcapital.mkt.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
@javax.xml.bind.annotation.XmlRootElement
public class SegmentHeadIn extends BaseInput
{
  private Integer publishStatus = null;
  private String segmentName = null;
  private String userToken = null;
  
  @JsonProperty("publish_status")
  public Integer getPublishStatus() {
    return publishStatus;
  }
  public void setPublishStatus(Integer publishStatus) {
    this.publishStatus = publishStatus;
  }
  
  
  @JsonProperty("segment_name")
  public String getSegmentName() {
    return segmentName;
  }
  public void setSegmentName(String segmentName) {
    this.segmentName = segmentName;
  }
  
  
  @JsonProperty("user_token")
  public String getUserToken() {
    return userToken;
  }
  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }
  
  }

