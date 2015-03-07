/**
 * 
 */
package com.mail.sender.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.mail.sender.model.MailHistory;

/**
 * Komponent odpowiedzialny za pobieranie danych.
 * @author Tomek
 *
 */
public class MailerDao {
	
	/**
	 * Konfigurowalna wartość ile ostatnich klubów wyciągnąć.
	 */
	private static final int CLUBS_QUANTITY = 2;
	
	public Entity getContentEntity(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("Content");
	    List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
	    return contents.get(0);
	}
	
	public void parseAndSaveData(String content, String language){
		Scanner scanner = new Scanner(content);
		scanner.useDelimiter(";|\\s+");
		List<Entity> clubs = new ArrayList<Entity>();
		Date date = new Date();
		List<String> allMailAdresses = getAllMailAdresses();
		while(scanner.hasNext()){
			Entity clubEntity = new Entity("Club");
			clubEntity.setProperty("creationDate", date);
			clubEntity.setProperty("lastUpadateDate", date);
			String mail = scanner.next();
			clubEntity.setProperty("mail", mail);
			clubEntity.setProperty("club", scanner.next());
			clubEntity.setProperty("language", language);
			
			if(!allMailAdresses.contains(mail) && mail.contains("@") ){
				clubs.add(clubEntity);
			}
		}
		scanner.close();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(clubs);
	}

	private List<String> getAllMailAdresses() {
		List<String> mailAdresses = new ArrayList<String>();
		List<Entity> allClubEntries = getAllClubEntries();
		if(allClubEntries != null){
			for(Entity e : allClubEntries){
				mailAdresses.add((String) e.getProperty("mail"));
			}
		}
		return mailAdresses;
	}

	public List<Entity> getOldestClubEntries() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("Club").addSort("lastUpadateDate", SortDirection.ASCENDING);
	    List<Entity> entries = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(CLUBS_QUANTITY));
	    return entries;
	}

	public List<Entity> getAllClubEntries() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("Club");
	    List<Entity> entries = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	    return entries;
	}

	public void upDate(List<Entity> clubs) {
		for(Entity entity : clubs){
			entity.setProperty("lastUpadateDate", new Date());
		}
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(clubs);
	}

	public List<Entity> getLastSendClubs(Date date) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter =	new FilterPredicate("lastUpadateDate",
				                      FilterOperator.GREATER_THAN_OR_EQUAL,
				                      date);

	    Query query = new Query("Club").setFilter(propertyFilter).addSort("lastUpadateDate", SortDirection.ASCENDING);
	    return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	}
}
