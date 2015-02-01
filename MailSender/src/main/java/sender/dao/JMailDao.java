package sender.dao;


import java.util.List;

import sender.model.MailModel;


/**
 * @author Tomek
 *
 */
public interface JMailDao {

	List<MailModel> getMessages();

}
