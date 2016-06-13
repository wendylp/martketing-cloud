package cn.rongcapital.mkt.vo.in;


import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignDecisionTagIn {
	
    private Integer id;

    private String name;

    private List<TagIn> tags;

    private Byte rule;

	 @JsonProperty("id")
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("tags")
    public List<TagIn> getTags() {
		return tags;
	}

	public void setTags(List<TagIn> tags) {
		this.tags = tags;
	}

	@JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("rule")
    public Byte getRule() {
        return rule;
    }

    public void setRule(Byte rule) {
        this.rule = rule;
    }
}
