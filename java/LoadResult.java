package result;
import model.User;
import model.Person;
import model.Event;
import java.util.ArrayList;


/** LoadResult will return all of the loaded information
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class LoadResult {
  /**array of all users that were loaded*/
  private ArrayList<User> users;
  /**array of all persons that were loaded*/
  private ArrayList<Person> persons;
  /**array of all events that were loaded*/
  private ArrayList<Event> events;
  /**message describing suceess of outputs or error*/
  private String message;
  /**boolean stating the success of the load request*/
  private boolean success;

  /** Default Constructor
  */
  public LoadResult(){
    users = null;
    persons = null;
    events = null;
    message = null;
    success = false;
  }
  /** sets the users, persons, and events that will be returned
  * @param usersIn set of users to be returned
  * @param personsIn set of persons to be returned
  * @param eventsIn set of events to be returned
  * @param messageIn String of resulting message
  * @param successIn boolean value representing a succesful load 
  */
  public LoadResult(ArrayList<User> usersIn, ArrayList<Person> personsIn, ArrayList<Event> eventsIn, String messageIn, boolean successIn){
    users = usersIn;
    persons = personsIn;
    events = eventsIn;
    message = messageIn;
    success = successIn;
  }
  /** provides description of the load operation performed
  * @return String message
  */
  public String resultMessage(){ 
    if(message.length() == 0)
      message = "Successfully added " + users.size() + " users, " + persons.size() + " persons, and " + events.size() + " events to the Database";
    return message;  
  }
  public ArrayList<User> getUsers(){ 
    return users;  
  }
  public ArrayList<Person> getPersons(){ 
    return persons;  
  }
  public ArrayList<Event> getEvents(){ 
    return events;  
  }
  /** represents whether or not fill was successful
  * @return boolean stating that fill was successful
  */
  public boolean success(){ 
    return success; 
  }


}
