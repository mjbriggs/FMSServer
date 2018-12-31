package request;
/** FillRequest is request object to fill the database for specified username
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/
public class FillRequest {
  /**username for fill request*/
  private String username;
  /** number of generations to be filled*/
  private int generations;

  /**Default Constructor
  */
  public FillRequest(){
    username = "";
    generations = 4;
  }
  /**Constructor that sets username and number of generations
  * @param usernameIn username we will fill with data
  * @param generationsIn sets the number of generations to be filled, default is 4
  */
  public FillRequest(String usernameIn, int generationsIn){
    username = usernameIn;
    generations = generationsIn;
  }
  public String getUsername(){ 
    return username; 
  }
  public int getGenerations(){ 
    return generations;  
  }


}
