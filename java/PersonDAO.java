package dao;
import java.util.ArrayList;
/** PersonDAO object intereacts with the person database table
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/
import java.util.ArrayList;
import model.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import manage.ConnectionManager;

public class PersonDAO {
  /** ArrayList of persons */
  private ArrayList<Person> persons;
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
  public PersonDAO(){
    persons = new ArrayList<Person>();
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
  /** Constructor that will Set the persons list
  * @param personsIn ArrayList of persons to be added
  */
  public PersonDAO(ArrayList<Person> personsIn){
    persons = personsIn;
  }
  public String getConnectionStatus(){  
    return connectionStatus;
  }
  public ArrayList<Person> getAllPersons(){  
    return persons;  
  }
  public Person getPerson(String personIDIn){  
    Person returnPerson = null;
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("select * from Persons where personId = ?");
      stmt.setString(1, personIDIn);
      results = stmt.executeQuery();
      while(results.next()){
        returnPerson = new Person(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5)
      , results.getString(6), results.getString(7), results.getString(8));
      }
      results.close();
      stmt.close();
      connection = connectionManager.closeConnection();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    return returnPerson;  
  }
  public ArrayList<Person> getPersons(String usernameIn){ 
    ArrayList<Person> tmpPersons = new ArrayList<Person>();
    Person tmpPerson = null;
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("select * from Persons where descendent = ?");
      stmt.setString(1, usernameIn);
      results = stmt.executeQuery();
      while(results.next()){
        tmpPerson = new Person(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5)
      , results.getString(6), results.getString(7), results.getString(8));
      tmpPersons.add(tmpPerson);
      }
      results.close();
      stmt.close();
      connection = connectionManager.closeConnection();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    return tmpPersons;    
  }
  /** adds a Person object to person database
  * @param personIn person to be added to database
  */
  public void addPerson(Person personIn){
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("insert into Persons values ( ?, ?, ?, ?, ?, ?, ?, ? )");
      stmt.setString(1, personIn.getPersonID());
      stmt.setString(2, personIn.getDescendent());
      stmt.setString(3, personIn.getFirstName());
      stmt.setString(4, personIn.getLastName());
      stmt.setString(5, personIn.getGender());
      stmt.setString(6, personIn.getFather());
      stmt.setString(7, personIn.getMother());
      stmt.setString(8, personIn.getSpouse());
      stmt.executeUpdate();
      stmt.close();
      connection = connectionManager.closeConnection();
      this.updateList();
    }catch(SQLException err){
      System.out.println("\n" + err);
    }
  }
  /** removes a person in database
  * @param personIn person to be removed from database
  */
  public void removePersons(String usernameIn){
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("delete from Persons where descendent = ?");
      stmt.setString(1, usernameIn);
      stmt.executeUpdate();
      stmt.close();
      connection = connectionManager.closeConnection();
      this.updateList();
    }catch(SQLException err){
      System.out.println("\n" + err);
    }
  }
  /** removes a person from database by their personID
  * @param personIDIn personID identifying person to be removed
  */
  public void removePerson(String personIDIn){
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("delete from Persons where personId = ?");
      stmt.setString(1, personIDIn);
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
    return false;
  }
  private void updateList() throws SQLException {
    persons.clear();
    Person tmpPerson = null;
    connection = connectionManager.connect();
    stmt = connection.prepareStatement("select * from Persons");
    results = stmt.executeQuery();
    while(results.next()){
      tmpPerson = new Person(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5)
      , results.getString(6), results.getString(7), results.getString(8));
      persons.add(tmpPerson);
    }
    results.close();
    stmt.close();
    connection = connectionManager.closeConnection();
  }

}
