package service;
import dao.*;
import model.*;
import result.ClearResult;
import java.util.ArrayList;
import java.sql.SQLException;

/** ClearResult will specify report on status of clearing database
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class ClearService{
  /**States whether database was successfully cleared*/
  private boolean cleared;

  /** Default Constructor
  */
  public ClearService(){
    cleared = false;
  }
  /** Will clear the database of all information
  * @return will return result object
  */
  public ClearResult clear() throws SQLException{ 
    EventDAO eventDAO = new EventDAO();
    ArrayList<Event> events = new ArrayList<Event>();
    //events = eventDAO.getAllEvents();
    for(Event event : eventDAO.getAllEvents()){
      events.add(new Event(event.getEventID(), event.getDescendent(), event.getPersonID(), event.getLatitude(), event.getLongitude(),
      event.getCountry(), event.getCity(), event.getEventType(), event.getYear()));
    }
    for(Event event : events){
      eventDAO.removeEvent(event.getEventID());
    }
    
    UserDAO userDAO = new UserDAO();
    ArrayList<User> users = new ArrayList<User>();
    for(User user : userDAO.getAllUsers()){
      users.add(new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(),
      user.getGender(), user.getPersonID()));
    }
    for(User user : users){
      userDAO.removeUser(user.getUsername());
    }
    
    PersonDAO personDAO = new PersonDAO();
    ArrayList<Person> persons = new ArrayList<Person>();
    for(Person person : personDAO.getAllPersons()){
      persons.add(new Person(person.getPersonID(), person.getDescendent(), person.getFirstName(), person.getLastName(), person.getGender(),
      person.getFather(), person.getMother(), person.getSpouse()));
    }
    for(Person person : persons){
      personDAO.removePerson(person.getPersonID());
    }
  


    AuthTokenDAO authtokenDAO = new AuthTokenDAO();
    ArrayList<AuthToken> authTokens = new ArrayList<AuthToken>();
    for(AuthToken token : authtokenDAO.getAllAuthTokens()){
      authTokens.add(new AuthToken(token.getUsername(), token.getToken()));
    }
    for(AuthToken token : authTokens){
      authtokenDAO.removeToken(token.getUsername());
    }
    
    /*
    if(eventDAO.getAllEvents().size() == 0 && userDAO.getAllUsers().size() == 0 && 
    personDAO.getAllPersons().size() == 0 && authtokenDAO.getAllAuthTokens().size() == 0)
      cleared = true;  */
    cleared = eventDAO.getAllEvents().size() == 0 && userDAO.getAllUsers().size() == 0 && 
    personDAO.getAllPersons().size() == 0 && authtokenDAO.getAllAuthTokens().size() == 0;
    return new ClearResult(cleared); 
  }
}
