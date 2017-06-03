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
@JsonPropertyOrder({ "entity", "type", "startIndex", "endIndex", "score", "resolution" })
public class Entity {

	@JsonProperty("entity")
	private String entity;
	@JsonProperty("type")
	private String type;
	@JsonProperty("startIndex")
	private int startIndex;
	@JsonProperty("endIndex")
	private int endIndex;
	@JsonProperty("score")
	private double score;
	@JsonProperty("resolution")
	private Resolution resolution;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("entity")
	public String getEntity() {
		return entity;
	}

	@JsonProperty("entity")
	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Entity withEntity(String entity) {
		this.entity = entity;
		return this;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	public Entity withType(String type) {
		this.type = type;
		return this;
	}

	@JsonProperty("startIndex")
	public int getStartIndex() {
		return startIndex;
	}

	@JsonProperty("startIndex")
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public Entity withStartIndex(int startIndex) {
		this.startIndex = startIndex;
		return this;
	}

	@JsonProperty("endIndex")
	public int getEndIndex() {
		return endIndex;
	}

	@JsonProperty("endIndex")
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public Entity withEndIndex(int endIndex) {
		this.endIndex = endIndex;
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

	public Entity withScore(double score) {
		this.score = score;
		return this;
	}

	@JsonProperty("resolution")
	public Resolution getResolution() {
		return resolution;
	}

	@JsonProperty("resolution")
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Entity withResolution(Resolution resolution) {
		this.resolution = resolution;
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

	public Entity withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}
}
