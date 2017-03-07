package cn.rongcapital.mkt.usersource.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersourceAvailableIn extends BaseInput {

    @NotNull
    @JsonProperty("id")
    private Long id;
    
    @NotNull
    @JsonProperty("available")
    private Long available;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAvailable() {
		return available;
	}

	public void setAvailable(Long available) {
		this.available = available;
	}
    
}
