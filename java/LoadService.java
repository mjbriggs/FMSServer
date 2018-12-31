package service;
import model.User;
import model.Person;
import model.Event;
import request.LoadRequest;
import result.LoadResult;
import dao.UserDAO;
import dao.EventDAO;
import dao.PersonDAO;

import java.sql.SQLException;
import java.util.ArrayList;

/** LoadService will clear database and then load all of the posted data into the database
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class LoadService{
  /**array of users to be loaded*/
  private ArrayList<User> users;
  /**array of persons to be loaded*/
  private ArrayList<Person> persons;
  /**array of events to be loaded*/
  private ArrayList<Event> events;
  /**boolean stating success of load request*/
  private boolean success;
  /** String describing error that occurred */
  private String error;

  /**Default Constructor
  */
  public LoadService(){
    users = new ArrayList<>();
    persons = new ArrayList<>();
    events = new ArrayList<>();
    success = false;
    error = "";
  }
  /** Loads user,person and event arrays into the database. Sets the success flag if successful
  * @param r Load request that specifies the data to be loaded
  * @return result object containing information of all added data
  */
  public LoadResult load(LoadRequest r) {  
    ClearService clearService = new ClearService();
    try{
      clearService.clear();
      if(r.getUsers() == null || r.getPersons() == null || r.getEvents() == null){
        error = "Invalid Request Data";
        success = false;
      }
      else{
        UserDAO userDAO = new UserDAO();
        PersonDAO personDAO = new PersonDAO();
        EventDAO eventDAO = new EventDAO();
        for(User user : r.getUsers()){
          if(user.validUser())
            userDAO.addUser(user);
          else{
            error = "Invalid Request Data";
            success = false;
            return new LoadResult(null, null, null, error, success);  
          }

        }
        for(Person person : r.getPersons()){
          if(person.validPerson())
            personDAO.addPerson(person);
          else {
            error = "Invalid Request Data";
            success = false;
            return new LoadResult(null, null, null, error, success);  
          }  
        }
        for(Event event : r.getEvents()){
          eventDAO.addEvent(event);
        }
        users = userDAO.getAllUsers();
        persons = personDAO.getAllPersons();
        events = eventDAO.getAllEvents();
        success = true;
        error = "";
      }
    }catch(SQLException ex){
      System.out.println("\n" + ex + "\n");
    }
    catch(Exception ex){
      System.out.println("\n" + ex + "\n");
    }
    return new LoadResult(users, persons, events, error, success);  
  }
  /** If there is a failure, error message will be set and will describe the error
  * @return String that contains error description
  */
  public String getError(){ 
    return error; 
  }
}
