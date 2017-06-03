package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphContainer {
	private List<EntityNode> allEntities;
	private List<Relation> allRelations;
	
	public List<EntityNode> getAllEntities() {
		return allEntities;
	}
	public void setAllEntities(List<EntityNode> allEntities) {
		this.allEntities = allEntities;
	}
	public List<Relation> getAllRelations() {
		return allRelations;
	}
	public void setAllRelations(List<Relation> allRelations) {
		this.allRelations = allRelations;
	}

	public GraphContainer() {
		// TODO Auto-generated constructor stub
		allEntities = new ArrayList<EntityNode>();
		allRelations = new ArrayList<Relation>();
	}
	public EntityNode addEntity(String name)
	{
		//check if entity exists
		//yes then throw
		//No then create
		EntityNode entity = null;
		try
		{
			entity = new EntityNode(name);
			allEntities.add(entity);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return entity;
	}
	public void addRelation(String name, EntityNode first, EntityNode second)
	{
		Relation relation = new Relation();
		relation.setFirstRole(first);
		relation.setSecondRole(second);
		relation.setName(name);
		allRelations.add(relation);
		
		//Update relations in entity
		//firstEntity
		EntityNode firstEntity = allEntities.stream().filter(item-> item.name.equalsIgnoreCase(first.name)).findAny().get();
		allEntities.remove(firstEntity);
		firstEntity.addRelation(relation);
		
		//secondEntity
		EntityNode secondEntity = allEntities.stream().filter(item-> item.name.equalsIgnoreCase(second.name)).findAny().get();
		allEntities.remove(secondEntity);
		secondEntity.addRelation(relation);
		
		//Add back to original entity list 
		allEntities.add(firstEntity);
		allEntities.add(secondEntity);
	}
	
	public void printEntities()
	{
		allEntities.forEach(item->{
			System.out.println(item.name);
			List<Relation> relationships = item.relations;
			/*System.out.println("Relation:");
			relationships.forEach(x -> {
				System.out.println("Name: " +x.name + " First: "+ x.getFirstRole().name + " Second: "+ x.getSecondRole().name);
			});
*/		});
	}
}
