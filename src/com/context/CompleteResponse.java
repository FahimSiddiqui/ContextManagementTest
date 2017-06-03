package com.context;

import java.util.List;

import LUISResponse.Entity;
import LUISResponse.Intent;

public class CompleteResponse {
	List<Intent> intents;
	List<Entity> entities;
	String	question;
	String response;

	public List<Intent> getIntents() {
		return intents;
	}
	public void setIntents(List<Intent> intents) {
		this.intents = intents;
	}
	public List<Entity> getEntities() {
		return entities;
	}
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}
