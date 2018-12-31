package result;
/** ClearResult will specify report on status of clearing database
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class ClearResult {
  /**output message*/
  private String message;
  /** states whether clear was successful*/
  private boolean success;

  /** Default Constructor
  */
  public ClearResult(){}
  /** Constructor that clarifies the success of the clear operation
  * @param successIn states whether or not clear was successful
  */
  public ClearResult(boolean successIn){
    success = successIn;
  }
  /*clears the entire database
  public void clear(){}
  */

  /** provides description of the clear operation performed
  * @return String message
  */
  public String resultMessage(){ 
    if(success)
      message = "Clear succeeded";
    else  
      message = "Internal Server Error. Clear Service failed";  
    return message;  
  }
  /** represents whether or not fill was successful
  * @return boolean stating that fill was successful
  */
  public boolean success(){ 
    return success; 
  }

}
