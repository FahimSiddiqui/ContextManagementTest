package LUISResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"query",
"topScoringIntent",
"intents",
"entities"
})
public class Root {

@JsonProperty("query")
private String query;
@JsonProperty("topScoringIntent")
private TopScoringIntent topScoringIntent;
@JsonProperty("intents")
private List<Intent> intents = null;
@JsonProperty("entities")
private List<Entity> entities = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("query")
public String getQuery() {
return query;
}

@JsonProperty("query")
public void setQuery(String query) {
this.query = query;
}

public Root withQuery(String query) {
this.query = query;
return this;
}

@JsonProperty("topScoringIntent")
public TopScoringIntent getTopScoringIntent() {
return topScoringIntent;
}

@JsonProperty("topScoringIntent")
public void setTopScoringIntent(TopScoringIntent topScoringIntent) {
this.topScoringIntent = topScoringIntent;
}

public Root withTopScoringIntent(TopScoringIntent topScoringIntent) {
this.topScoringIntent = topScoringIntent;
return this;
}

@JsonProperty("intents")
public List<Intent> getIntents() {
return intents;
}

@JsonProperty("intents")
public void setIntents(List<Intent> intents) {
this.intents = intents;
}

public Root withIntents(List<Intent> intents) {
this.intents = intents;
return this;
}

@JsonProperty("entities")
public List<Entity> getEntities() {
return entities;
}

@JsonProperty("entities")
public void setEntities(List<Entity> entities) {
this.entities = entities;
}

public Root withEntities(List<Entity> entities) {
this.entities = entities;
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

public Root withAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
return this;
}

}