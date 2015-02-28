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
import com.google.appengine.api.datastore.Query.SortDirection;

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
		while(scanner.hasNext()){
			Entity clubEntity = new Entity("Club");
			clubEntity.setProperty("creationDate", date);
			clubEntity.setProperty("lastUpadateDate", date);
			clubEntity.setProperty("mail", scanner.next());
			clubEntity.setProperty("club", scanner.next());
			clubEntity.setProperty("language", language);
			clubs.add(clubEntity);
		}
		scanner.close();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(clubs);
	}

	public List<Entity> getOldestClubEntries() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("Club").addSort("lastUpadateDate", SortDirection.ASCENDING);;
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
}
