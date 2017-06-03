package LUISResponse;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "intent", "score" })
public class Intent {

	@JsonProperty("intent")
	private String intent;
	@JsonProperty("score")
	private double score;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("intent")
	public String getIntent() {
		return intent;
	}

	@JsonProperty("intent")
	public void setIntent(String intent) {
		this.intent = intent;
	}

	public Intent withIntent(String intent) {
		this.intent = intent;
		return this;
	}

	@JsonProperty("score")
	public double getScore() {
		return score;
	}

	@JsonProperty("score")
	public void setScore(double score) {
		this.score = score;
	}

	public Intent withScore(double score) {
		this.score = score;
		return this;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public Intent withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}

}
