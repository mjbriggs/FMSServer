package dao;
/** EventDAO object intereacts with the event database table
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/

import java.util.ArrayList;
import java.util.Set;
//import model.*;

import model.Event;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manage.ConnectionManager;

public class EventDAO {
  /** set of events */
  private ArrayList<Event> events;
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
  public EventDAO(){
    events = new ArrayList<Event>();
    connectionManager = new ConnectionManager();
    try{
      //connectionManager.closeConnection();
      /*String driver = "org.sqlite.JDBC";
      Class.forName(driver);
      connection = DriverManager.getConnection("jdbc:sqlite:../fmsDataBase/fms.db");*/
      connection = connectionManager.connect();
      connectionStatus = "Connection succeeded";
      this.updateList();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
      connectionStatus = "Connection failed";
    }
  }
  /** Constructor that sets list of events
  * @param eventsIn set of events to be passed in
  */
  public EventDAO(ArrayList<Event> eventsIn) throws SQLException {
    for(Event event : eventsIn){
      addEvent(event);
    }    
    this.updateList();
  }
  public Event getEvent(String eventIDIn){  
    Event returnEvent = null;
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("select * from Events where eventId = ?");
      stmt.setString(1, eventIDIn);
      results = stmt.executeQuery();
      while(results.next()){
        returnEvent = new Event(results.getString(1), results.getString(2), results.getString(3), Double.parseDouble(results.getString(4)), Double.parseDouble(results.getString(5)),
        results.getString(6), results.getString(7), results.getString(8), Integer.parseInt(results.getString(9)));
      }
      results.close();
      stmt.close();
      connection = connectionManager.closeConnection();
      //this.updateList();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    return returnEvent; 
  }
  public ArrayList<Event> getAllEvents(){  
    try{
      this.updateList();
    }
    catch(SQLException ex){
      System.out.println("\n" + ex);
    }
   // System.out.println("Events : " + events.toString());
    return events;  
  }
  public ArrayList<Event> getEvents(String usernameIn){  
    ArrayList<Event> tmpEvents = new ArrayList<Event>();
    Event tmpEvent = null;
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("select * from Events where descendent = ? ");
      stmt.setString(1, usernameIn);
      results = stmt.executeQuery();
      while(results.next()){
        tmpEvent = new Event(results.getString(1), results.getString(2), results.getString(3), Double.parseDouble(results.getString(4)), Double.parseDouble(results.getString(5)),
        results.getString(6), results.getString(7), results.getString(8), Integer.parseInt(results.getString(9)));
        tmpEvents.add(tmpEvent);
      }
      results.close();
      stmt.close();
      connection = connectionManager.closeConnection();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    //System.out.println(tmpEvents.toString());
    return tmpEvents;  
  }
  /** describes DAO's connection status */
  public String getConnection(){
    return connectionStatus;
  }
  /** adds event to event database
  * @param eventIn event to be added to database
  */
  public void addEvent(Event eventIn){
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("insert into Events values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )");
      stmt.setString(1, eventIn.getEventID());
      stmt.setString(2, eventIn.getDescendent());
      stmt.setString(3, eventIn.getPersonID());
      stmt.setString(4, Double.toString(eventIn.getLatitude()));
      stmt.setString(5, Double.toString(eventIn.getLongitude()));
      stmt.setString(6, eventIn.getCountry());
      stmt.setString(7, eventIn.getCity());
      stmt.setString(8, eventIn.getEventType());
      stmt.setString(9, Integer.toString(eventIn.getYear()));
      stmt.executeUpdate();
      stmt.close();
      connection = connectionManager.closeConnection();
      this.updateList();
    }catch(SQLException err){
      System.out.println("\n" + err);
    }
  }
  /** removes event from event database
  * @param eventIn event to be removed from database
  */
  public void removeEvents(String usernameIn){
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("delete from Events where descendent = ?");
      stmt.setString(1, usernameIn);
      stmt.executeUpdate();
      stmt.close();
      connection = connectionManager.closeConnection();
      this.updateList();
    }catch(SQLException err){
      System.out.println("\n" + err);
    }
  }
  /** removes event from event database by eventID
  * @param eventIDIn eventID to identify event to be removed
  */
  public void removeEvent(String eventIDIn){
    try{
      connection = connectionManager.connect();
      stmt = connection.prepareStatement("delete from Events where eventId = ?");
      stmt.setString(1, eventIDIn);
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
  /** updates Events list with new database content
   */
  private void updateList() throws SQLException {
    events.clear();
    Event tmpEvent = null;
    connection = connectionManager.connect();
    stmt = connection.prepareStatement("select * from Events");
    results = stmt.executeQuery();
    while(results.next()){
      tmpEvent = new Event(results.getString(1), results.getString(2), results.getString(3), Double.parseDouble(results.getString(4)), Double.parseDouble(results.getString(5)),
      results.getString(6), results.getString(7), results.getString(8), Integer.parseInt(results.getString(9)));
      events.add(tmpEvent);
    }
    results.close();
    stmt.close();
    connection = connectionManager.closeConnection();
  }
}
