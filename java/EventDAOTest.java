package test;

import java.util.*;
import model.Event;
import dao.EventDAO;
import org.junit.*;
import static org.junit.Assert.*;
import java.beans.Transient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manage.ConnectionManager;

public class EventDAOTest {
    /** object that accesses the Events Table */
    EventDAO eventDaoObj;
    /** Event arraylist to compare to DAO */
    private ArrayList<Event> testEvents;
    /** Object that creates a sql connection */
    private Connection connection;
    /**object that contains sql commands */
    PreparedStatement stmt = null;
    /** object that contains sql results */
    ResultSet results = null;
    /** manages db connection */
    ConnectionManager connectionManager = null;
    
    @Before
    public void setUp(){
        connectionManager = new ConnectionManager();
        testEvents = new ArrayList<Event>();
        eventDaoObj = new EventDAO();
        Event tmpEvent = null;
        try{
            connection = connectionManager.connect();
             //System.out.println("Connection succeeded");
            stmt = connection.prepareStatement("select * from Events");
            results = stmt.executeQuery();
            while(results.next()){
                tmpEvent = new Event(results.getString(1), results.getString(2), results.getString(3), Double.parseDouble(results.getString(4)), Double.parseDouble(results.getString(5)),
                results.getString(6), results.getString(7), results.getString(8), Integer.parseInt(results.getString(9)));
                testEvents.add(tmpEvent);
            }
            results.close();
            stmt.close();
        }
        catch(SQLException err){
            System.out.println("\n" + err);
        // System.out.println("Connection failed");
        }
    }
    //@Test 
    public ArrayList<Event> testGetAllEvents(){
        ArrayList<Event> testEvents = new ArrayList<Event>();
        Event tmpEvent = null;
        try{
            stmt = connection.prepareStatement("select * from Events");
            results = stmt.executeQuery();
            while(results.next()){
                tmpEvent = new Event(results.getString(1), results.getString(2), results.getString(3), Double.parseDouble(results.getString(4)), Double.parseDouble(results.getString(5)),
                results.getString(6), results.getString(7), results.getString(8), Integer.parseInt(results.getString(9)));
                testEvents.add(tmpEvent);
            }
            results.close();
            stmt.close();
            }
        catch(SQLException err){
            System.out.println("\n" + err);
        }
        //System.out.println(eventDaoObj.getAllEvents().toString());
        assertTrue(eventDaoObj.getAllEvents().equals(testEvents));
        return testEvents;
    }
    @Test 
    public void testGetEventByID(){
        String validEventID = testEvents.get(0).getEventID();
        assertTrue(eventDaoObj.getEvent(validEventID).equals(testEvents.get(0)));
        assertFalse(eventDaoObj.getEvent(validEventID).equals(testEvents.get(testEvents.size()-1)));
        assertFalse(eventDaoObj.getEvent(validEventID).equals(null));
    }
    @Test 
    public void testGetEventsByDescendent(){
        String validDescendent = testEvents.get(0).getDescendent();
        ArrayList<Event> validEventList = new ArrayList<Event>();
        ArrayList<Event> invalidEventList = new ArrayList<Event>();
        for(Event event : testEvents){
            if(event.getDescendent().equals(validDescendent))
                validEventList.add(event);
            else 
                invalidEventList.add(event);

        }
        assertTrue(eventDaoObj.getEvents(validDescendent).equals(validEventList));
        assertFalse(eventDaoObj.getEvents(validDescendent).equals(invalidEventList));
        assertFalse(eventDaoObj.getEvents(validDescendent).equals(null));
    }
    //@Test 
    public void testAddEvent(){
        Event newEvent = new Event("new_eventID", "Jim", "jim_bob", 100, 300, "Russia", "moscow", "The cold war", 2019);
        Event newerEvent = new Event("newer_eventID", "Jim", "jim_bob", 100, 300, "Russia", "moscow", "The coldest war", 2020);
        Event newestEvent = new Event("newest_eventID", "Jim", "jim_bob", 100, 300, "Russia", "moscow", "The more coldest war", 2021);
        int oldTableSize = eventDaoObj.getAllEvents().size();
        eventDaoObj.addEvent(newEvent);
        eventDaoObj.addEvent(newerEvent);
        eventDaoObj.addEvent(newestEvent);
        //System.out.println("test add event :: \n" + testGetAllEvents().toString() + "\n\n\n");
        //System.out.println(eventDaoObj.getAllEvents().toString());
        assertTrue(eventDaoObj.getAllEvents().size() > oldTableSize && testGetAllEvents().size() > oldTableSize);
    }
    @Test 
    public void testRemoveEvent(){
        testAddEvent();
        int oldTableSize = eventDaoObj.getAllEvents().size();
        eventDaoObj.removeEvent("new_eventID");
        assertTrue(eventDaoObj.getAllEvents().size() < oldTableSize && testGetAllEvents().size() < oldTableSize);
        oldTableSize = eventDaoObj.getAllEvents().size();
        //System.out.println("test remove event before :: \n" + testGetAllEvents().toString() + "\n\n\n");
        //System.out.println(eventDaoObj.getAllEvents().toString());
        eventDaoObj.removeEvents("Jim");
        //System.out.println("\n\n\ntest remove event after :: \n" + testGetAllEvents().toString() + "\n\n\n");
        //System.out.println(eventDaoObj.getAllEvents().toString());
        assertTrue(eventDaoObj.getAllEvents().size() < oldTableSize && testGetAllEvents().size() < oldTableSize);
    }



}