package request;
import model.User;
import model.Person;

import java.util.ArrayList;

import model.Event;
 

/** LoadService will clear database and then load all of the posted data into the database
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class LoadRequest{
  /**array of users to be loaded*/
  private ArrayList<User> users;
  /**array of persons to be loaded*/
  private ArrayList<Person> persons;
  /**array of events to be loaded*/
  private ArrayList<Event> events;
  /**boolean stating success of load request*/

  /**Default Constructor
  */
  public LoadRequest(){
    users = null;
    persons = null;
    events = null;
  }
  /** Constructor that will declase the users, persons, and events to be added
  *@param usersIn user array of new users
  *@param personsIn person array of new persons
  *@param eventsIn event array of new events
  */
  public LoadRequest(ArrayList<User> usersIn, ArrayList<Person> personsIn, ArrayList<Event> eventsIn){
    users = usersIn;
    persons = personsIn;
    events = eventsIn;

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
}
