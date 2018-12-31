package result;
/** PersonResult will either contain information for one person or all persons
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class PersonResult {
  /**person to be returned*/
  private Person person;
  /**persons to be returned*/
  private Person persons[];
  /**output message*/
  private String message;
  /**states the success of the request*/
  private boolean success;
  /**states whether a single person was searched*/
  private boolean singlePerson;

  /*Default Constructor
  */
  public PersonResult(){}
  /*Constructor that will set either person object or will grab all persons from service class
  */
  public PersonResult(String personIDIn){}
  public Person getPerson(){}
  public Person[] getPerson(){}
  public String getPersonID(){}
  /** will create success mesaage
  *@return String of success message
  */
  public String successMessage(){ return ""; }
  /** will create error errorMessage
  *@return String of errorMessage
  */
  public String errorMessage(){ return "";  }


}
