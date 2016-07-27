/**
 * 
 */
package com.mail.sender.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Class responsible for retrieving data.
 * @author Tomasz Smiechowicz
 *
 */
public class MailerDao {
	
	/**
	 * Configurable field how many entries to retrieve from db.
	 */
	private static final int CLUBS_QUANTITY = 8;
	
	public Entity getContentEntity(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("Content");
	    List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
	    return contents.get(0);
	}
	
	public void parseAndSaveData(String content, String language){
		Scanner scanner = new Scanner(content);
		scanner.useDelimiter(";|\n");
		List<Entity> clubs = new ArrayList<Entity>();
		Date date = new Date();
		List<String> allMailAdresses = getAllMailAdresses();
		while(scanner.hasNext()){
			Entity clubEntity = new Entity("Club");
			clubEntity.setProperty("creationDate", date);
			clubEntity.setProperty("lastUpadateDate", date);
			String mail = scanner.next();
			clubEntity.setProperty("mail", mail);
			clubEntity.setProperty("club", ((String)scanner.next()).replaceAll("\r", ""));
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

	public List<Entity> getMockedOldestClubEntries() {
		Entity clubEntity = new Entity("Club");
		clubEntity.setProperty("creationDate", new Date());
		clubEntity.setProperty("lastUpadateDate", new Date());
		clubEntity.setProperty("mail", "smiecho18@interia.pl");
		clubEntity.setProperty("club", "club");
		clubEntity.setProperty("language", "pl");

		Entity clubEntity2 = new Entity("Club");
		clubEntity2.setProperty("creationDate", new Date());
		clubEntity2.setProperty("lastUpadateDate", new Date());
		clubEntity2.setProperty("mail", "error!");
		clubEntity2.setProperty("club", "club");
		clubEntity2.setProperty("language", "pl");

		Entity clubEntity3 = new Entity("Club");
		clubEntity3.setProperty("creationDate", new Date());
		clubEntity3.setProperty("lastUpadateDate", new Date());
		clubEntity3.setProperty("mail", "smiecho18@interia.pl");
		clubEntity3.setProperty("club", "club3");

		return Arrays.asList(new Entity[]{clubEntity, clubEntity2, clubEntity3});
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
