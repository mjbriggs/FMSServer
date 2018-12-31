package service;
/** PersonService will either search for one person or will grab all persons
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/
public class PersonService{
  /**specific person object that is found */
  private Person person;
  /** all person objects in table*/
  private Person persons[];
  /**states if search is for one person or all persons*/
  private boolean onePerson;
  /**Id of person to be searched*/
  private String personID;

  /**Default Constructor
  */
  public PersonService(){}
  /**Constructor that will specify personID to be searched
  *@param r request object containing information regarding the search of a specific person or all persons
  */
  public PersonService(PersonRequest r){}
  public Person getOnePerson(){}
  public Person[] getAllPersons(){}
  public String getPersonID(){}
}
