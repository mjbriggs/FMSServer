package dao;
/** AuthTokenDAO object intereacts with the AuthToken database table
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manage.ConnectionManager;
import java.util.Random;

public class AuthTokenDAO {
  /** set of the AuthTokens in the AuthToken table */
  private ArrayList<AuthToken> AuthTokens;
  /** connection status */
  private String connectionStatus;
  /** Connection object */
  private Connection connection;
  /**object that contains sql commands */
  PreparedStatement stmt = null;
  /** object that contains sql results */
  ResultSet results = null;
  /** handles database connection */
  ConnectionManager connectionManager = null;
  /** Default Constructor
  */
  public AuthTokenDAO(){
    AuthTokens = new ArrayList<AuthToken>();
    connectionManager = new ConnectionManager();
    try{
      /*String driver = "org.sqlite.JDBC";
      Class.forName(driver);
      connection = DriverManager.getConnection("jdbc:sqlite:../fmsDataBase/fms.db");*/
      connection = connectionManager.connect();
      connectionStatus = "Connection succeeded";
      this.updateList();
      //connection = connectionManager.closeConnection();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
      connectionStatus = "Connection failed";
    }

  }
  /** Constructor that sets container of AuthTokens
  * @param AuthTokensIn a set of all the AuthTokens
  */
  public AuthTokenDAO(ArrayList<AuthToken> AuthTokensIn){
    AuthTokens = AuthTokensIn;
  }
  public AuthToken getMostRecentAuthToken(String usernameIn){ 
    ArrayList<AuthToken> userTokens = this.getAuthTokens(usernameIn);
    return userTokens.get(userTokens.size() - 1);  
  }
  public AuthToken getByToken(String token){ 
    AuthToken returnToken = null;
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("select * from AuthTokens where token = ?");
      stmt.setString(1, token);
      results = stmt.executeQuery();
      while(results.next()){
        returnToken = new AuthToken(results.getString(1), results.getString(2));
      }
      results.close();
      stmt.close();
      connection = connectionManager.closeConnection();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    return returnToken;   
  }
  public ArrayList<AuthToken> getAuthTokens(String usernameIn){  
    ArrayList<AuthToken> userTokens = new ArrayList<AuthToken>();
    AuthToken tmpToken = null;
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("select * from AuthTokens where username = ?");
      stmt.setString(1, usernameIn);
      results = stmt.executeQuery();
      while(results.next()){
        tmpToken = new AuthToken(results.getString(1), results.getString(2));
        userTokens.add(tmpToken);
      }
      results.close();
      stmt.close();
      connection = connectionManager.closeConnection();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    return userTokens;  
  }//only gets rows of specific username
  public ArrayList<AuthToken> getAllAuthTokens(){  
    return AuthTokens;  
  }
  /** describes DAO's connection status */
  public String getConnection(){
    return connectionStatus;
  }
  /** adds an AuthToken row to the AuthToken database
  * @param usernameIn user that is getting a new AuthToken
  */
  public void addAuthToken(String usernameIn){
    AuthToken newToken = new AuthToken(usernameIn, generateRandomToken());
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("insert into AuthTokens values ( ?, ? )");
      stmt.setString(1, usernameIn);
      stmt.setString(2, newToken.getToken());
      stmt.executeUpdate();
      stmt.close();
      connection = connectionManager.closeConnection();
      this.updateList();
    }catch(SQLException err){
      System.out.println("\n" + err);
    }

  }
  /** removes AuthToken row
  * @param tokenIn token to be removed
  */
  public void removeToken(AuthToken tokenIn){
    //not implemented right now
  }
  /** removes AuthToken row(s) by username
  * @param usernameIn username to be removed
  */
  public void removeToken(String usernameIn){
    try{
    connection = connectionManager.connect();
    stmt = connection.prepareStatement("delete from AuthTokens where username = ?");
    stmt.setString(1, usernameIn);
    stmt.executeUpdate();
    stmt.close();
    connection = connectionManager.closeConnection();
    this.updateList();
  }catch(SQLException err){
  System.out.println("\n" + err);
  }
}
  /** states whether table has data or not
  * @return boolean true if empty, false if not
  */
  public boolean tableIsEmpty(){
    if(AuthTokens.size() != 0)
      return true;
    return false;
  }
  /** generates a random token for user
  * @return random 8 letter String
  */
  private String generateRandomToken(){
    int wordLength = 8;
    String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!_-+~";
    Random random = new Random();
    String tokenOut = "";
    for(int i = 0; i < 8; i++){
      tokenOut += availableChars.charAt(random.nextInt(availableChars.length()));
    }

    return tokenOut;  
  }
  /** will check if AuthToken is in database and is associated with correct user
  * @param tokenIn token to be validated
  * @param userIn user that is requesting to have token validated
  * @return states whether or not AuthToken exists and is associated with requesting user
  */
  public boolean validate(String tokenIn, String userIn){
    AuthToken validationToken = new AuthToken(userIn, tokenIn);
    try{
      this.updateList();
    }
    catch(SQLException ex){
      System.out.println("\n" + ex);
    }
    if(AuthTokens.contains(validationToken))
      return true;
    return false;
  }
  public void closeConnection() throws SQLException{
    if(connection != null)
      connection.close();
  }
  /** updates AuthTokens list with new database content
   */
  private void updateList() throws SQLException {
   // System.out.println("Updating authToken List");
    AuthTokens.clear();
    AuthToken tmpToken = null;
    connection = connectionManager.connect();
    stmt = connection.prepareStatement("select * from AuthTokens");
    results = stmt.executeQuery();
    while(results.next()){
      tmpToken = new AuthToken(results.getString(1), results.getString(2));
      AuthTokens.add(tmpToken);
    }
    results.close();
    stmt.close();
    connection = connectionManager.closeConnection();
  }
}
