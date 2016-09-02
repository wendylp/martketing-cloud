package cn.rongcapital.mkt.vo.in;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class WechatQrcodeIn{

	@NotEmpty
    private String wx_name;
    
	private String qrcode_name;
	
    private Integer ch_code;
//    @NotNull
    private Byte is_audience;
    @NotEmpty
    private String fixed_audience;
    @NotEmpty
    private List<AssociationTag> association_tags;
    @NotEmpty
    private String comments;
    
    private Byte status;
    
    private String qrcode_pic;
    
    private String qrcode_url;
 
    private String ticket;

    @JsonProperty("wx_name")
	public String getWx_name() {
		return wx_name;
	}

	public void setWx_name(String wx_name) {
		this.wx_name = wx_name;
	}

	@JsonProperty("ch_code")
	public Integer getCh_code() {
		return ch_code;
	}

	public void setCh_code(Integer ch_code) {
		this.ch_code = ch_code;
	}

	@JsonProperty("is_audience")
	public Byte getIs_audience() {
		return is_audience;
	}

	public void setIs_audience(Byte is_audience) {
		this.is_audience = is_audience;
	}

	public String getFixed_audience() {
		return fixed_audience;
	}

	public void setFixed_audience(String fixed_audience) {
		this.fixed_audience = fixed_audience;
	}

	@JsonProperty("association_tags")
	public List<AssociationTag> getAssociation_tags() {
		return association_tags;
	}

	public void setAssociation_tags(List<AssociationTag> association_tags) {
		this.association_tags = association_tags;
	}

	@JsonProperty("comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@JsonProperty("status")
	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@JsonProperty("qrcode_pic")
	public String getQrcode_pic() {
		return qrcode_pic;
	}

	public void setQrcode_pic(String qrcode_pic) {
		this.qrcode_pic = qrcode_pic;
	}

	@JsonProperty("qrcode_url")
	public String getQrcode_url() {
		return qrcode_url;
	}

	public void setQrcode_url(String qrcode_url) {
		this.qrcode_url = qrcode_url;
	}

	@JsonProperty("ticket")
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getQrcode_name() {
		return qrcode_name;
	}

	public void setQrcode_name(String qrcode_name) {
		this.qrcode_name = qrcode_name;
	}    
	
}
