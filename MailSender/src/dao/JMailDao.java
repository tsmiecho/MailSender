/**
 * 
 */
package dao;

import java.util.List;

import model.MailModel;

/**
 * @author Tomek
 *
 */
public interface JMailDao {

	List<MailModel> getMessages();

}
