package test;

import java.sql.SQLException;
import java.util.*;
import model.User;
import model.Event;
import model.Person;
import dao.AuthTokenDAO;
import dao.EventDAO;
import dao.PersonDAO;
import dao.UserDAO;
import result.LoadResult;
import request.LoadRequest;
import service.LoadService;
import org.junit.*;
import static org.junit.Assert.*;



public class LoadTest {
    /** will perform load operation */
    LoadService loadService = null;
    /**Contains request information */
    LoadRequest loadRequest = null;
    /**Contains result information */
    LoadResult loadResult = null;
    /** Users to be added */
    ArrayList<User> usersToAdd = null;
    /** Users to be added */
    ArrayList<Person> personsToAdd = null;
    /** Users to be added */
    ArrayList<Event> eventsToAdd = null;

    @Before 
    public void setUp(){
        usersToAdd = new ArrayList<>();
        personsToAdd = new ArrayList<>();
        eventsToAdd = new ArrayList<>();
        usersToAdd.add(new User("user1", "password1", "user1@mail.com" ,"first1", "last1", "m", "personID1"));
        usersToAdd.add(new User("user2", "password2", "user2@mail.com", "first2", "last2", "f", "personID2"));
        usersToAdd.add(new User("user3", "password3",  "user3@mail.com", "first3", "last3", "m", "personID3"));
        personsToAdd.add(new Person("personID1", "user1", "first1", "last1", "m", "father1", "mother1", "spouse1"));
        personsToAdd.add(new Person("personID2", "user2", "first2", "last2", "f", "father2", "mother2", "spouse2"));
        personsToAdd.add(new Person("personID3", "user3", "first3", "last3", "m", "father3", "mother3", "spouse3"));
        eventsToAdd.add(new Event("EventID1", "user1", "personID1", 123, 456, "Russia", "Moscow", "Marriage", 1000));
        eventsToAdd.add(new Event("EventID2", "user2", "personID2", 124, 457, "El salvador", "San Salvador", "Birth", 2000));
        eventsToAdd.add(new Event("EventID3", "user3", "personID3", 183, 496, "United States of America", "New York City", "Death", 3000));
        loadRequest = new LoadRequest(usersToAdd, personsToAdd, eventsToAdd);
        loadService = new LoadService();
    }
    public void invalidLoad(){
            UserDAO userDAO = null;
            PersonDAO personDAO = null;
            EventDAO eventDAO = null;
            loadResult = loadService.load(new LoadRequest(null, null, null));
            userDAO = new UserDAO();
            personDAO = new PersonDAO();
            eventDAO = new EventDAO();
            assertFalse(loadResult.success());
            assertTrue(loadResult.resultMessage().equals("Invalid Request Data"));
            assertTrue(userDAO.getAllUsers().size() == 0);
            assertTrue(personDAO.getAllPersons().size() == 0);
            assertTrue(eventDAO.getAllEvents().size() == 0);
        
    }
    @Test 
    public void loadTest() {
            invalidLoad();
            UserDAO userDAO = null;
            PersonDAO personDAO = null;
            EventDAO eventDAO = null;
            boolean clear = true;
            loadResult = loadService.load(loadRequest);
            userDAO = new UserDAO();
            personDAO = new PersonDAO();
            eventDAO = new EventDAO();
            assertTrue(loadResult.success());       
            assertTrue(loadResult.resultMessage().equals("Successfully added 3 users, 3 persons, and 3 events to the Database")); 
            //System.out.println("\n\n get result Users : \n" + loadResult.getUsers().toString());
            //System.out.println("\n\n get users from dao : \n" + userDAO.getAllUsers().toString());
            assertTrue(loadResult.getUsers().equals(usersToAdd));
            assertTrue(loadResult.getPersons().equals(personsToAdd));
            assertTrue(loadResult.getEvents().equals(eventsToAdd));
            assertTrue(userDAO.getAllUsers().equals(loadResult.getUsers()));
            assertTrue(personDAO.getAllPersons().equals(loadResult.getPersons()));
            assertTrue(eventDAO.getAllEvents().equals(loadResult.getEvents()));
        
        /** align USER DAO and user model with table info or vice versa */
    }
    @After 
    public void cleanUp(){
        //AuthTOken table is not reloaded, so we reload it here
        AuthTokenDAO tokenDAO = new AuthTokenDAO();
        tokenDAO.addAuthToken("user1");
        tokenDAO.addAuthToken("user3");
    }


}
