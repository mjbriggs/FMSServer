package request;
/** PersonRequest will contain personID to be searched, will specify if search is for one person or all persons
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class PersonRequest {
  /** PersonID to be searched*/
  private String personID;

  /**Default Constructor
  */
  public PersonRequest(){}
  /**Constructor that sets PersonID to be searched
  */
  public PersonRequest(String personIDIn){}
  /** returns true for one person, false for all people
  *@return boolean value describing if there is one person to search for
  */
  public boolean personOrPeople(){  return false; }
  public String getPersonID(){  return""; }


}
