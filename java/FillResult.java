package result;
import request.*;
/** FillResult is result object to fill the database for specified username
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class FillResult {
  /**  String that will describe success or failure*/
  private String message;
  /** boolean that will state the success of the fill request*/
  private boolean success;
  /** number of persons added to database */
  private int persons;
  /** number of events that have been added */
  private int events;

  /**Default constructor
  */
  public FillResult(){
    message = "";
    success = false;
    persons = -1;
    events = -1;
  }
  /**Constructor that sets the number of persons and events added to databas
  * @param personsIn number of persons added to database
  * @param eventsIn number of events addded to database  
  * @param error Potential error message
  */
  public FillResult(int personsIn, int eventsIn, String error){
    persons = personsIn;
    events = eventsIn;
    message = error;
    success = false;
    if(message.length() == 0)
      success = true;
  }
  /** provides description of the fill operation performed
  * @return String message
  */
  public String resultMessage(){ 
    if(message.length() == 0)
      message = "Successfully added " + persons + " persons and " + events + " events to the database.";
    return message;  
  }
  /** represents whether or not fill was successful
  * @return boolean stating that fill was successful
  */
  public boolean success(){ 
    return success; 
  }
}
