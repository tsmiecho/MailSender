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
import com.mail.sender.model.Language;

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

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public Entity getContentEntity(){
	    Query query = new Query("Content");
	    List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
	    return contents.get(0);
	}

	/**
	 * Method responsible for parsing input data.
	 * @param content - input for parsing
	 * @param language - language option
     * @return list of Entity
     */
	public List<Entity> parseData(String content, Language language){
		Scanner scanner = new Scanner(content);
		scanner.useDelimiter(";|\n");
		List<Entity> clubs = new ArrayList<>();
		Date date = new Date();
		while(scanner.hasNext()){
			Entity clubEntity = new Entity("Club");
			clubEntity.setProperty("creationDate", date);
			clubEntity.setProperty("lastUpadateDate", date);
			String mail = scanner.next();
			clubEntity.setProperty("mail", mail);
			clubEntity.setProperty("club", (scanner.next()).replaceAll("\r", ""));
			clubEntity.setProperty("language", language.name());
			
			if(mail.contains("@") ){
				clubs.add(clubEntity);
			}
		}
		scanner.close();
        return clubs;
	}

	/**
	 * Method responsible for saving entries in DB.
	 * @param entities - list of Entity
     */
	public void saveEntries(List<Entity> entities) {
		datastore.put(entities);
	}

	public List<String> getAllMailAdresses() {
		List<String> mailAdresses = new ArrayList<>();
		List<Entity> allClubEntries = getAllClubEntries();
		if(allClubEntries != null){
			for(Entity e : allClubEntries){
				mailAdresses.add((String) e.getProperty("mail"));
			}
		}
		return mailAdresses;
	}

	public List<Entity> getOldestClubEntries() {
	    Query query = new Query("Club").addSort("lastUpadateDate", SortDirection.ASCENDING);
	    return datastore.prepare(query).asList(FetchOptions.Builder.withLimit(CLUBS_QUANTITY));
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
	    Query query = new Query("Club");
	    return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	}

	public void upDate(List<Entity> clubs) {
		for(Entity entity : clubs){
			entity.setProperty("lastUpadateDate", new Date());
		}
		saveEntries(clubs);
	}

	public List<Entity> getLastSendClubs(Date date) {

		Filter propertyFilter =	new FilterPredicate("lastUpadateDate",
				                      FilterOperator.GREATER_THAN_OR_EQUAL,
				                      date);

	    Query query = new Query("Club").setFilter(propertyFilter).addSort("lastUpadateDate", SortDirection.ASCENDING);
	    return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	}
}
