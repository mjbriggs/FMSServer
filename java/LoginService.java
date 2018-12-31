package service;
import model.User;
import model.AuthToken;
import dao.UserDAO;

import java.util.ArrayList;

import dao.AuthTokenDAO;
import request.LoginRequest;
import result.LoginResult;
/** LoginService will check user credentials and generate an AuthToken if login is successful
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

public class LoginService{
  /**username of request user*/
  private String username;
  /**password of request user*/
  private String password;
  /** String describing error that occurred */
  private String error;

  /** Default Constructor
  */
  public LoginService(){
    username = "";
    password = "";
    error = "";
  }
  /** performs login operation
  * @param r request object containing login credentials
  *@return LoginResult object containing user's AuthToken
  */
  public LoginResult login(LoginRequest r){  
    LoginResult loginResult = null;
    ArrayList<AuthToken> allUserTokens = new ArrayList<>();
    AuthToken token = null;
    UserDAO userDAO = new UserDAO();
    AuthTokenDAO tokenDAO = new AuthTokenDAO();
    User userToFind = userDAO.getUser(r.getUsername());
    if(userToFind != null && userToFind.getPassword().equals(r.getPassword())){
      tokenDAO.addAuthToken(r.getUsername());
      allUserTokens = tokenDAO.getAuthTokens(r.getUsername());
      token = allUserTokens.get(allUserTokens.size() - 1); //gets most recent token
      error = "";
    }
    else{
      userToFind = null;
      error = "Request property missing or has invalid value";
    }
    loginResult = new LoginResult(token, userToFind, error);
    return loginResult;  
  }
  /** If there is a failure, error message will be set and will describe the error
  * @return String that contains error description
  */
  public String getError(){ 
    return error; 
  }
}
