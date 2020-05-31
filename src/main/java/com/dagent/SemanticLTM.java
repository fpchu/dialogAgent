package com.dagent;


import java.util.HashMap;


public class SemanticLTM {
	
	private static SemanticLTM instance = null;
	
	private HashMap<String, Object> frames;
	
	private HashMap<String, HashMap<String, String>> knownPeople;
	
	private HashMap<String, HashMap<String, String>> knownActivities;
	
	private HashMap<String, HashMap<String, String>> metaKnownPeople;
	
	private HashMap<String, HashMap<String, String>> metaKnownActivities;
	
	private HashMap<String, HashMap<String, String>> knownRestaurant;
	
	
	private SemanticLTM() {
		
		knownPeople = new HashMap<String, HashMap<String, String>>();
		
		knownActivities = new HashMap<String, HashMap<String, String>>();
		
		knownRestaurant = new HashMap<String, HashMap<String, String>>();
		
		metaKnownPeople = new HashMap<String, HashMap<String, String>>();
		
		metaKnownActivities = new HashMap<String, HashMap<String, String>>();
		
		frames = new HashMap<String, Object>();
	}
	
	public HashMap<String, String> getKnownPerson(String name) { return knownPeople.get(name); }
	
	public HashMap<String, HashMap<String, String>> getActType() { return knownActivities; }
	
	public HashMap<String, String> getKnownActivities(String activity) { return knownActivities.get(activity); }
	
	public HashMap<String, String> getKnownRestaurant(String type) { return knownRestaurant.get(type); }
	
	public HashMap<String, HashMap<String, String>> getFoodType() { return knownRestaurant; }
	
	public HashMap<String, String> getMetaKnownPerson(String name) { return metaKnownPeople.get(name); }
	
	public HashMap<String, String> getMetaKnownActivities(String activity) { return metaKnownActivities.get(activity); }
	
	public void setKnownPerson(String name, HashMap<String, String> attr) { knownPeople.put(name, attr); }
	
	public void setKnownActivities(String name, HashMap<String, String> attr) { knownActivities.put(name, attr); }
	
	public void setKnownResturant(String type, HashMap<String, String> restaurant) { knownRestaurant.put(type, restaurant); }
	
	public void setMetaKnownPerson(String name, HashMap<String, String> attr) { knownPeople.put(name, attr); }
	
	public void setMetaKnownActivitiesc(String name, HashMap<String, String> attr) { knownActivities.put(name, attr); }
	
	
	public Object getFrame(String field) { return frames.get(field); }
	
	public void setFrame(String field, Object obj) { frames.put(field, obj); }
	
	
	public static SemanticLTM getInstance() {
		if (instance == null)
			instance = new SemanticLTM();
		return instance;
	}
}

