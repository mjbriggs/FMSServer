package service;

import model.*;
import dao.*;
import result.RegisterResult;
import request.RegisterRequest;
import GSON.JSONDecoder;
import GSON.Location;
import service.FillService;
import request.FillRequest;
import result.FillResult;
import java.util.ArrayList;
import java.util.Random;


/** RegisterService will add user to the database and provide user with an AuthToken
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/
public class RegisterService {
  /** user to be registered */
  private User user;
  /** String describing error that occurred */
  private String error;

  /** Default Constructor
  */
  public RegisterService(){
    user = null;
    error = "";
  }
  /** Will register declared user into database, creates a user row and an AuthToken for userIn
  * @param r Request object specifying the user to be added to the database
  * @return RegisterResult object that specifies whether or not registration was successful
  */
  public RegisterResult register(RegisterRequest r){
    RegisterResult result = null;
    UserDAO userDAO = new UserDAO();
    AuthTokenDAO tokenDAO = new AuthTokenDAO();
    JSONDecoder jsonDecoder = new JSONDecoder();
    user = r.getUser();
    System.out.println("In register");
    if(user != null && user.validUser()){
      System.out.println("User format is valid");
      if(userDAO.getUser(user.getUsername()) == null){
        userDAO.addUser(user);
        jsonDecoder.loadLocalFiles();
        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill(new FillRequest(user.getUsername(), 4));//4 is default generation
        if(fillResult.resultMessage().equals("Successfully added 31 persons and 92 events to the database.")){
          error = "";
          tokenDAO.addAuthToken(user.getUsername());
        }
        else{
          error = fillResult.resultMessage();
          user = null;
        }
      }
      else{
        user = null;
        error = "Username already taken by another user";
      }
    }
    else{
      System.out.println("User format is Invalid");
      user = null;
      error = "Request property missing or has invalid value";
    }
    result = new RegisterResult(user, error);
    return result;
  }
  /** If there is a failure, error message will be set and will describe the error
  * @return String that contains error description
  */
  public String getError(){
    return error;
  }

}
