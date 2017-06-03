package com.context;
import java.util.ArrayList;
import java.util.List;

import LUISResponse.Entity;
import LUISResponse.Intent;
import LUISResponse.Root;

public class ResponseGenerator {
	public CompleteResponse Generate(List<TypeMapping> mapping,Root root)
	{
		String matchedPattern= processResponse(mapping);
		List<Intent> rootIntents = root.getIntents();
		List<Entity> rootEntities = root.getEntities();
		List<Intent> intentsToBbAdded = new ArrayList<Intent>();
		List<Entity> entitiesToBbAdded = new ArrayList<Entity>();
		CompleteResponse res = new CompleteResponse();
		res.setQuestion(root.getQuery());
		res.setResponse(matchedPattern);
		for(TypeMapping type: mapping)
		{
			//Get Intent from Root Obj
			if(type.getType().equalsIgnoreCase(Type.Intent))
			{
				//I could create a replica and delete from original list, but for now adding into a new list.
				intentsToBbAdded.add(rootIntents.stream().filter(item -> item.getIntent().equalsIgnoreCase(type.getTypeValue())).findFirst().get());
			}
			//Get Entity from Root Obj
			if(type.getType().equalsIgnoreCase(Type.Entity))
			{
				entitiesToBbAdded.add(rootEntities.stream().filter(item -> item.getType().equalsIgnoreCase(type.getTypeValue())).findFirst().get());
			}	
		}
		res.setEntities(entitiesToBbAdded);
		res.setIntents(intentsToBbAdded);
		return res;	
	}
	
	public String processResponse(List<TypeMapping> mapping)
	{
		String response = "hello";
		try
		{
			//Lets start with Find Intent.Also,for now single sentence at a time.
			//String intent = mapping.stream().filter( x-> x.type.equalsIgnoreCase("intent")).findFirst().get().type;
			/*if(mapping.stream().filter( x-> x.type.equalsIgnoreCase(Type.Intent) && x.value.equalsIgnoreCase("find")).findFirst().isPresent())
			{
				//1. Looking for one Entity quantity/fxRate/price.
			}*/
			
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			throw ex;
		}
		return response;
	}
}
