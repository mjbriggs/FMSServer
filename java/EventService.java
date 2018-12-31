package service;
import request.*;
import dao.EventDAO;
import dao.UserDAO;
import dao.AuthTokenDAO;
import model.Event;
import model.AuthToken;
import model.User;
import result.EventResult;
import java.util.ArrayList;

/** EventService will either grab one event by ID or all events
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class EventService{
  /** true if one event, false if all events */
  private boolean gettingOneEvent;
  /** eventID of event to be grabbed */
  private String eventID;
  /** event to be returned */
  private Event event;
  /** all events */
  private Event[] allEvents;
  /** String describing error that occured */
  private String error;

  /** Default Constructor
  */
  public EventService(){
    gettingOneEvent = false;
    eventID = "";
    event = null;
    allEvents = null;
    error = "";
  }
  /** will return one event or all events, specified by the EventRequest
  * @param r request that will ask for one event or all events
  * @param token token to be validated
  * @param username user that is requesting to have token validated
  * @return event specified by EventRequest
  */
  public EventResult getEventOrEvents(EventRequest r,String token, String username){  
    System.out.println("in getEventOrEvents");
    AuthTokenDAO authTokenDAO = new AuthTokenDAO();
    EventDAO eventDAO = new EventDAO();
    EventResult result = null;
    Event event = null;
    if(username.equals("") && authTokenDAO.getByToken(token) != null){
      System.out.println("Getting username with user");
      username = authTokenDAO.getByToken(token).getUsername();
      System.out.println("Username : " + username);
    }
    if(authTokenDAO.validate(token, username)){
      //System.out.println("Validated events");
      if(r.getEventID() == null){ // getting all events
        //System.out.println("Getting all events");
        result = new EventResult(eventDAO.getEvents(username), error);
      }
      else { // getting one event
        System.out.println("Getting one event");
        if(eventDAO.getEvent(r.getEventID()) == null){
          error = "Invalid eventID parameter";
          result = new EventResult(event, error);
        }
        else {
          event = eventDAO.getEvent(r.getEventID());
          if(event.getDescendent().equals(username))
            result = new EventResult(event, error);
          else  {
            event = null;
            error = "Requested event does not belong to this user";
            result = new EventResult(event, error);
          }       
        }
      }
    }
    else{
     // System.out.println("Invalid authToken");
      error = "Invalid authToken";
      result = new EventResult(event, error);
    }
    //System.out.println("Returning event result");
    return result;  
  }
  /** If there is a failure, error message will be set and will describe the error
  * @return String that contains error description
  */
  public String getError(){ 
    return error;  
  }
}
