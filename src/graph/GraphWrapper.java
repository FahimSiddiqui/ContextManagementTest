package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphWrapper {
	GraphContainer container = new GraphContainer();
	public void createGraph()
	{
		EntityNode security = container.addEntity("security");
		EntityNode issuer = container.addEntity("issuer");
		EntityNode exchange = container.addEntity("exchange");
		EntityNode position = container.addEntity("position");
		EntityNode portfolio = container.addEntity("portfolio");
		EntityNode country = container.addEntity("country");
		container.addRelation("issues", issuer, security);
		container.addRelation("creates", security, position);
		container.addRelation("builds", position, portfolio);
		container.addRelation("tradedOn", security, exchange);
		container.addRelation("has", security, country);
		//container.printEntities();
	}
	
	public GraphContainer getGraph(String entityName)
	{
		GraphContainer smallGraph = new GraphContainer();
		if(container.getAllEntities().stream().filter(item -> item.name.equalsIgnoreCase(entityName)).findAny().isPresent())
		{
			//smallGraph.setAllEntities();
			EntityNode entity = container.getAllEntities().stream().filter(x -> x.name.equalsIgnoreCase(entityName)).findAny().get();
			List<Relation> relations = entity.getRelations();
			//Set Relations of given entity in smallGraph.
			smallGraph.setAllRelations(relations);
			//Set relevant entities too.
			smallGraph.setAllEntities(getOtherEntities(entity));
		}
		return smallGraph;
	}
	private List<EntityNode> getOtherEntities(EntityNode entity)
	{
		List<Relation> relationList = entity.getRelations();
		List<EntityNode> entityList = new ArrayList<EntityNode>();
		for(Relation relation:relationList)
		{
			if(relation.getFirstRole().name.equalsIgnoreCase(entity.name))
			{
				entityList.add(relation.getSecondRole());
			}
			if(relation.getSecondRole().name.equalsIgnoreCase(entity.name))
			{
				entityList.add(relation.getFirstRole());
			}
		}
		return entityList;
		//return entity.getRelations();
	}
}