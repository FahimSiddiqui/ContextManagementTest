package com.context;
public class TypeMapping {
	private String type;
	private String typeValue;
	private String entityValue;
	public String getEntityValue() {
		return entityValue;
	}
	public void setEntityValue(String value) {
		this.entityValue = value;
	}
	private double score;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String categoryType) {
		this.typeValue = categoryType;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public TypeMapping(String type, String typeValue, String entityValue, double score) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.typeValue = typeValue;
		this.entityValue = entityValue;
		this.score = score;
	}
	void print()
	{
		System.out.println("Type:" + this.type + " TypeValue: " + this.typeValue + " EntityValue: " + this.entityValue + " Score: " + this.score);
	}
}
