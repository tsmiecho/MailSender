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

/**
 * @author tsmiecho
 *
 */
public class MailerDao {
	
	public List<Entity> getEntries(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("Content");
	    List<Entity> contents = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
	    return contents;
	}
	
	public void saveData(String content){
		Scanner scanner = new Scanner(content);
		scanner.useDelimiter(";|\\s+");
		List<Entity> clubs = new ArrayList<Entity>();
		Date date = new Date();
		while(scanner.hasNext()){
			Entity clubEntity = new Entity("Club");
			clubEntity.setProperty("creationDate", date);
			clubEntity.setProperty("mail", scanner.next());
			clubEntity.setProperty("club", scanner.next());
			clubs.add(clubEntity);
		}
		scanner.close();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(clubs);
	}
}
