package com.context;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import LUISResponse.Root;
import LUISResponse.Entity;
import LUISResponse.Intent;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import javax.tools.ToolProvider;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import LUISResponse.TopScoringIntent;
import graph.EntityNode;
import graph.GraphWrapper;
import graph.GraphContainer;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.*;

import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/Main")
public class Start {
	Stack<CompleteResponse> stack = new Stack<CompleteResponse>();
	CompleteResponse localCache = null;//new CompleteResponse();
	GraphWrapper wrapper = new GraphWrapper();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/GetContext")
	public String GetContext(String input) //This function will be called by Rahul. It will accept JSON input and return JSON output
	{
		String result = "";
		try {
			Root rootObj = null;
			rootObj = convertJSONtoJava(input);
			return processing(rootObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/GetStatus")
	public String GetStatus()
	{
		return "Whats up Fahim";
	}
	public Root convertJSONtoJava(String data) throws Exception
	{
		Root root =null;
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			root = mapper.readValue(data, Root.class);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			throw ex;
		}
		return root;
	}

	public String sendRequest(String queryString) throws Exception {

		String url = "https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/03cfd60b-dde9-426f-b437-11c2278dd72c?subscription-key=ab3581dac63c46ac9b755d803f477fbd&verbose=true&timezoneOffset=330&spellCheck=true&q="
				+ queryString;
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// con.setRequestMethod("POST");
		con.setRequestProperty("Ocp-Apim-Subscription-Key", "ab3581dac63c46ac9b755d803f477fbd");
		con.setRequestProperty("versionId", "0.1");
		con.setRequestProperty("appId", "3cfd60b-dde9-426f-b437-11c2278dd72c");

		// Send request
		con.setDoOutput(true);
		con.connect();

		int responseCode = con.getResponseCode();
		/*System.out.println("\nSending request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
*/
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		return response.toString();
	}

	public Root getJSONDDataFromFile(String filePath) throws Exception
	{
		Root root =null;
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			root = mapper.readValue(new File(filePath), Root.class);
			System.out.println(root);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			throw ex;
		}
		return root;
	}
	public void printIntents(Root rootObj)
	{
		List<Intent> intents = rootObj.getIntents();
		intents.forEach(item ->{
			System.out.println("Intent: " + item.getIntent() + "\tScore:"+item.getScore());
		});
	}
	public void printEntities(Root rootObj)
	{
		List<Entity> entities = rootObj.getEntities();
		entities.forEach(item ->{
			System.out.println("Type:" + item.getType() + "\tEntity: " + item.getEntity() + "\tScore:"+item.getScore());
		});		
	}

	public String processing(Root root)
	{
		String response = "";
		List<TypeMapping> typeMapping = new ArrayList<TypeMapping>();
		long countIntent=0,countEntity =0;
		ResponseGenerator resGenerator = new ResponseGenerator();
		try
		{
			//Step 01: Get Intents
			List<Intent> intents = root.getIntents();
			//Step 02: Get Entities
			List<Entity> entities = root.getEntities();
			//Step 03: Check if entity score 0.5 or more for each intent, add it
			entities.forEach(item ->{
				if(item.getScore()>=0.0)
					typeMapping.add(new TypeMapping(Type.Entity,item.getType(), item.getEntity(), item.getScore()));
			});
			
			//Get TopScoringIntent
			TopScoringIntent top = root.getTopScoringIntent();
			if(top!=null && top.getScore()<0.8)
			{
				//Step 03: Check if entity score 0.5 or more for each intent, add it(In case when top intent is less than 90%)
				intents.forEach(item ->{
					if(item.getScore()>=0.5 && ! item.getIntent().equalsIgnoreCase("None")){
						typeMapping.add(new TypeMapping(Type.Intent,item.getIntent(), "", item.getScore()));
					}
				});
				countIntent = typeMapping.stream().filter(item -> item.getType().equalsIgnoreCase(Type.Intent)).count();
			}
			else
			{
				typeMapping.add(new TypeMapping(Type.Intent,top.getIntent(), "", top.getScore()));
				countIntent = 1;
			}
			/*System.out.println("In Processing");
			printTypeMapping(typeMapping);*/
			//Or I could take count from intent/entity list. if consider confidence then below looks good.
			countEntity = typeMapping.stream().filter(item -> item.getType().equalsIgnoreCase(Type.Entity)).count();
	
			if(countIntent>= 1 && countEntity >= 1)
			{
				CompleteResponse result = resGenerator.Generate(typeMapping,root); //Newquestion
				/*Not sure if I need Top Scoring Intent for special processing..Need to check*/
				//Add response,Intents,Entity and question in the cache.
				this.localCache= result;
				//response = result.response;
				response = convertTypeMappingToJSON(typeMapping);
			}
			else if(countIntent < 1 && countEntity < 1)
			{
				System.out.println("Low confidence."); //Need to handle this
			}
			else
			{
				if(countIntent>=1)
				{
					//Missing Entity
					response = processMissingEntity(typeMapping);
				}
				else if(countEntity>=1)
				{
					//Missing Intents
					response = processMissingIntent(typeMapping);
				}
				else
				{
					//This will never happen
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			throw ex;
		}
		return response;
	}
	
	public String processMissingEntity(List<TypeMapping> typeMapping)
	{
		//Step0: Make sure there is no Intent available in typeMapping// Ideally there should be none
		List<String> mappedIntents = new ArrayList<String>();
		ResponseGenerator resGenerator = new ResponseGenerator();
		String result="";
		//Step 01: Get Intents from localCache/stack
		if(localCache!=null)
		{
			List<Entity> localEntities = localCache.getEntities();
			List<Intent> localIntents = localCache.getIntents();
			//Step 02: Check how many Intents from current question are matching with previous Intent
			for(TypeMapping currentIntents: typeMapping)
			{
				if(localIntents.stream().filter(item->item.getIntent().contains(currentIntents.getTypeValue())).findAny().isPresent())
				{
					mappedIntents.add(currentIntents.getTypeValue());
				}
			}
			//Step 03: If all the entities are matching then add previous and send it to response generator
			if(mappedIntents.size()==typeMapping.size())
			{
				//All the entities are matching
				//Find out the best Intent.
/*				Entity entity = localEntities.stream()
						.sorted((Entity e1,Entity e2) -> new Double(e1.getScore()).compareTo(new Double(e2.getScore())))
							.findFirst().get();;*/
				//.stream().sorted().findFirst().get();
				for(Entity entity: localEntities)
				{
					typeMapping.add(new TypeMapping(Type.Entity, entity.getType(), entity.getEntity(), entity.getScore()));
				}
				printTypeMapping(typeMapping);
				//result = resGenerator.processResponse(typeMapping);
				result = convertTypeMappingToJSON(typeMapping);
			}
			else
			{
				//FindOut how many are matching, need to think about it.
			}
		}
		return result;
	}
	
	public String processMissingIntent(List<TypeMapping> typeMapping)
	{
		//Step0: Make sure there is no Intent available in typeMapping// Ideally there should be none
		List<String> mappedEntity = new ArrayList<String>();
		ResponseGenerator resGenerator = new ResponseGenerator();
		String result="";
		HashMap<String,GraphContainer> smallGraphs = null;
		//System.out.println("In ProcessMissingIntent");
		//Step 01: Get entities from localCache
		if(localCache!=null)
		{
			List<Entity> localEntities = localCache.getEntities();
			List<Intent> localIntents = localCache.getIntents();
			//Step 02: Check how many entities from current question are matching with previous entities
			
			for(TypeMapping currentEntities: typeMapping)
			{
				if(localEntities.stream().filter(item->item.getType().contains(currentEntities.getTypeValue())).findAny().isPresent())
				{
					mappedEntity.add(currentEntities.getTypeValue());
				}
			}
			
			//Step 03: If all the entities are matching then add previous and send it to response generator
			if(mappedEntity.size()>0)
			{
				//All the entities are matching
				Intent intent = localIntents.stream().sorted().findFirst().get();
				typeMapping.add(new TypeMapping(Type.Intent,intent.getIntent(), "", intent.getScore()));
				
				//Adding all prev entites, to make search easier.
				for(Entity prevLocalEntity:localEntities)
				{
					if(! typeMapping.stream().filter(x-> x.getType().equalsIgnoreCase(Type.Entity) 
							&& x.getTypeValue().equalsIgnoreCase(prevLocalEntity.getType())).findAny().isPresent()
							)
						typeMapping.add(new TypeMapping(Type.Entity, prevLocalEntity.getType(), prevLocalEntity.getEntity(), prevLocalEntity.getScore()));
				}
				printTypeMapping(typeMapping);
				//result = resGenerator.processResponse(typeMapping);
				result = convertTypeMappingToJSON(typeMapping);
			}
			else
			{
				smallGraphs = GetGraphsForPreviousEntities(localEntities);
				//Try to map it with current entities.
				HashMap<String,String> foundRelationship = findRelationshipBetweenEntities(smallGraphs,typeMapping);
				if(foundRelationship.size()>0)
				{
					//Add Entities
					for(Entity prevLocalEntity:localEntities)
					{
						if(! typeMapping.stream().filter(x-> x.getType().equalsIgnoreCase(Type.Entity) 
								&& x.getTypeValue().equalsIgnoreCase(prevLocalEntity.getType())).findAny().isPresent()
								)
							typeMapping.add(new TypeMapping(Type.Entity, prevLocalEntity.getType(), prevLocalEntity.getEntity(), prevLocalEntity.getScore()));
					}
					//Add Intents
					Intent intent = localIntents.stream().sorted().findFirst().get();
					typeMapping.add(new TypeMapping(Type.Intent,intent.getIntent(), "", intent.getScore()));
					System.out.println("\nOutput to be provided:\n");
					printTypeMapping(typeMapping);
					//result = resGenerator.processResponse(typeMapping);
					result = convertTypeMappingToJSON(typeMapping);
					//System.out.println("Added");
				}
				else
				{
					//Ask question to the user.
				}				
			}
		}
		return result;
	}
	
	private HashMap<String,GraphContainer> GetGraphsForPreviousEntities(List<Entity> localEntities)
	{
		HashMap<String,GraphContainer> allPrevGraphs = new HashMap<String,GraphContainer>(); 
		//Get the graph of previous entities.
		System.out.println("\nGet local graphs for previous question's entities:");
		for(Entity prevEntity: localEntities)
		{
			String variable = prevEntity.getType();
			GraphContainer smallGraph = wrapper.getGraph(variable);
			allPrevGraphs.put(variable, smallGraph);
			System.out.println("LocalGraph for "+variable);
			smallGraph.printEntities();
		}
		return allPrevGraphs;
	}
	
	private HashMap<String,String> findRelationshipBetweenEntities(HashMap<String,GraphContainer> smallGraphs,List<TypeMapping> typeMapping)
	{
		HashMap<String,String> relationshipExists = new HashMap<String,String>(); //First prev entity name, current entity name.
		try {
			//System.out.println("In findRelationshipBetweenEntities \n\n");
			for (Map.Entry<String, GraphContainer> graph : smallGraphs.entrySet()) {
				List<EntityNode> entities = graph.getValue().getAllEntities();
				//graph.getValue().printEntities();
				for (TypeMapping newEntity : typeMapping) {
					// Check for each entity.. How many are matching in the graph.
					if (entities.stream().filter(x -> x.getName().equalsIgnoreCase(newEntity.getTypeValue())).findAny()
							.isPresent()) {
						relationshipExists.put(graph.getKey(), newEntity.getTypeValue());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		//System.out.println("Hello");
		return relationshipExists;
	}
	void printTypeMapping(List<TypeMapping> typeMapping)
	{
		typeMapping.forEach( item ->{
			System.out.println("Type: " + item.getType() + "\tTypeValue:" + item.getTypeValue() + "\tEntityValue: " + item.getEntityValue());
		});
	}
	private String convertTypeMappingToJSON(List<TypeMapping> typeMapping)
	{
		String jsonFile = "";
		try
		{
			final ObjectMapper mapper = new ObjectMapper();
			jsonFile = mapper.writeValueAsString(typeMapping);
			//.writeValue(new File("C:\\AI\\NLP\\TestLUIS\\TestFiles\\TypeMapping.json"),
				//	typeMapping);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return jsonFile;
	}
}