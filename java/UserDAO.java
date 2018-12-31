package dao;
/** UserDAO object intereacts with the user database table
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/
import java.util.ArrayList;
import model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manage.ConnectionManager;


public class UserDAO {
  /** set of users */
  private ArrayList<User> users;
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


  /** Default Constructor */
  public UserDAO(){
    users = new ArrayList<User>();
    connectionManager = new ConnectionManager();
    try{
      connection = connectionManager.connect();
      connectionStatus = "Connection succeeded";
      this.updateList();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
      connectionStatus = "Connection failed";
    }
  }
  /** Constructor that sets list of users
  * @param usersIn set of users to be put into user list
  */
  public UserDAO(ArrayList<User> usersIn) throws SQLException{
    //users = usersIn;
    users = new ArrayList<User>();
    connection = connectionManager.connect();
    connectionStatus = "Connection succeeded";
    for(User user : usersIn){
      this.addUser(user);
    }
    this.updateList();
  }
  public ArrayList<User> getAllUsers() {  
    return users;  
  }
  public User getUser(String usernameIn) {  
    User returnUser = null;
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("select * from Users where username = ?");
      stmt.setString(1, usernameIn);
      results = stmt.executeQuery();
      while(results.next()){
        returnUser = new User(results.getString(1), results.getString(2), results.getString(3), results.getString(4), 
        results.getString(5), results.getString(6), results.getString(7));
      }
      results.close();
      stmt.close();
      connection = connectionManager.closeConnection();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    return returnUser;  
  }
  /** adds user to user database
  * @param userIn user to be added to database
  */
  public void addUser(User userIn){
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("insert into Users values ( ?, ?, ?, ?, ?, ?, ? )");
      stmt.setString(1, userIn.getUsername());
      stmt.setString(2, userIn.getPassword());
      stmt.setString(3, userIn.getEmail());
      stmt.setString(4, userIn.getFirstName());
      stmt.setString(5, userIn.getLastName());
      stmt.setString(6, userIn.getGender());
      stmt.setString(7, userIn.getPersonID());
      stmt.executeUpdate();
      stmt.close();
      connection = connectionManager.closeConnection();
      this.updateList();
    }catch(SQLException err){
      System.out.println("\n" + err);
    }
  }
  /** removes user from user database
  * @param userIn user to be removed from user database
  */
  public void removeUser(User userIn){}
  /** removes user from user database by username
  * @param usernameIn username that identifies user to be removed
  */
  public void removeUser(String usernameIn){
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("delete from Users where username = ?");
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
  public boolean tableIsEmpty() throws SQLException{
    this.updateList();
    return users.size() == 0;
  }
  /** describes DAO's connection status */
  public String getConnection(){
    return connectionStatus;
  }
  /** updates Users list with new database content
   */
  private void updateList() throws SQLException {
    users.clear();
    User tmpUser = null;
    connection = connectionManager.connect();
    stmt = connection.prepareStatement("select * from Users");
    results = stmt.executeQuery();
    while(results.next()){
      tmpUser = new User(results.getString(1), results.getString(2), results.getString(3), results.getString(4), 
      results.getString(5), results.getString(6), results.getString(7));
      users.add(tmpUser);
    }
    results.close();
    stmt.close();
    connection = connectionManager.closeConnection();
  }

}
