package graph;

import java.util.ArrayList;
import java.util.List;

public class EntityNode {
	String name;
	List<Relation> relations = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Relation> getRelations() {
		return relations;
	}
	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}
	public EntityNode(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		if(relations==null)
			relations = new ArrayList<Relation>();
	}
	public EntityNode() {
		// TODO Auto-generated constructor stub
		if(relations==null)
			relations = new ArrayList<Relation>();
	}
	
	public EntityNode addName(String name)throws Exception
	{
		EntityNode newEntity = new EntityNode();
		try
		{
			//search something exists of the same name.
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return newEntity;
	}
	public void addRelation(Relation relation)
	{
		//if(! relations.stream().filter(x -> x.name.equalsIgnoreCase(relation.name)).findAny().isPresent())
			relations.add(relation);
		//else
			//System.out.println("Relation already exists");
	}
}
