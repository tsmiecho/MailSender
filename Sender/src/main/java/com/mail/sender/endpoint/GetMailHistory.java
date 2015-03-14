package com.mail.sender.endpoint;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.Entity;
import com.mail.sender.dao.MailerDao;
import com.mail.sender.model.MailHistory;

@Api(name = "myApi",
     version = "v1",
     namespace = @ApiNamespace(ownerDomain = "tsmiecho.appspot.com",
                                ownerName = "tsmiecho.appspot.com",
                                packagePath=""))
public class GetMailHistory {

    @ApiMethod(name = "getMailHistory", httpMethod = "get")
    public List<MailHistory> getMailHistory(@Named("dayBefore") String dayBefore) {
    	Calendar instance = Calendar.getInstance();
    	instance.add(Calendar.DATE, - Integer.valueOf(dayBefore));
    	MailerDao dao = new MailerDao();
    	List<MailHistory> response = new ArrayList<MailHistory>();
        List<Entity> clubs = dao.getLastSendClubs(instance.getTime());
        for(Entity e : clubs){
        	MailHistory mh = new MailHistory();
        	mh.setDateOfSent((Date) e.getProperty("lastUpadateDate"));
        	mh.setMail((String) e.getProperty("mail"));
        	mh.setName((String) e.getProperty("club"));
        	response.add(mh);
        }
        return response;
    }

}