package graph;

public class Relation {
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EntityNode getFirstRole() {
		return firstRole;
	}
	public void setFirstRole(EntityNode firstRole) {
		this.firstRole = firstRole;
	}
	public EntityNode getSecondRole() {
		return secondRole;
	}
	public void setSecondRole(EntityNode secondRole) {
		this.secondRole = secondRole;
	}
	EntityNode firstRole;
	EntityNode secondRole;
}
