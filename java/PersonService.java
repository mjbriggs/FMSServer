package service;
import model.AuthToken;
import model.Person;
import model.User;
import dao.AuthTokenDAO;
import dao.PersonDAO;
import request.PersonRequest;
import result.PersonResult;
import java.util.ArrayList;

/** PersonService will either search for one person or will grab all persons
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/
public class PersonService{
  /**specific person object that is found */
  private Person person;
  /** all person objects in table*/
  private ArrayList<Person> persons;
  /**states if search is for one person or all persons*/
  private boolean onePerson;
  /**Id of person to be searched*/
  private String personID;
  /** String describing error that occurred */
  private String error;

  /**Default Constructor
  */
  public PersonService(){}
  /** will either grab one specific person or all persons, based on person request
  * @param r request that spcifies person or all persons
  * @param token token to be validated for person operation
  * @param user user that is attempting to make person request
  * @return person result object containing one person or all persons
  */
  public PersonResult getPersonOrPersons(PersonRequest r, String token, String username){ 
    AuthTokenDAO authTokenDAO = new AuthTokenDAO();
    PersonDAO personDAO = new PersonDAO();
    PersonResult result = null;
    Person person = null;
    error = "";
    if(username.equals("") && authTokenDAO.getByToken(token) != null){
      System.out.println("Getting username with user");
      username = authTokenDAO.getByToken(token).getUsername();
      System.out.println("Username : " + username);
    }
    if(authTokenDAO.validate(token, username)){
      if(r.getPersonID() == null || r.getPersonID().equals("")){ // getting all events
        result = new PersonResult(personDAO.getPersons(username), error);
      }
      else { // getting one event
        if(personDAO.getPerson(r.getPersonID()) == null){
          error = "Invalid personID parameter";
          result = new PersonResult(person, error);
        }
        else {
          person = personDAO.getPerson(r.getPersonID());
          if(person.getDescendent().equals(username))
            result = new PersonResult(person, error);
          else  {
            person = null;
            error = "Requested person does not belong to this user";
            result = new PersonResult(person, error);
          }       
        }
      }
    }
    else{
      error = "Invalid authToken";
      result = new PersonResult(person, error);
    }
    return result;  
  }
  /** If there is a failure, error message will be set and will describe the error
  * @return String that contains error description
  */
  public String getError(){ 
    return error; 
  }
}


//ENDED ON LOGIN, SHOULD START HERE ON FRIDAY
