package test;

import java.sql.SQLException;
import java.util.*;
import model.User;
import model.Event;
import model.Person;
import dao.EventDAO;
import dao.PersonDAO;
import dao.UserDAO;
import result.FillResult;
import request.FillRequest;
import service.FillService;
import org.junit.*;
import static org.junit.Assert.*;



public class FillTest {
    /** Service object to perform fill operation */
    private FillService fillService;
    /** contains request info */
    private FillRequest request;
    /** contains result info*/
    private FillResult result;
    /** user to add info */
    private String user;
    /** generations to fill */
    private int generations;
    /** user data access object */
    private UserDAO userDAO;
    /** event data access object */
    private EventDAO eventDAO;
    /** person data access object */
    private PersonDAO personDAO;

    @Before
    public void setUp(){
        //creates test user and some fake data to be removed
        fillService = new FillService();
        userDAO = new UserDAO();
        eventDAO = new EventDAO();
        personDAO = new PersonDAO();
        request = null;
        result = null;
        user = "briggs88";
        generations = 4;
        userDAO.addUser(new User("briggs88",  "michael'sFakePassword",  "briggs@mail.org",  "mike",  "briggs",
        "m",  "mike_briggs*&"));
        userDAO.addUser(new User("ryan99",  "ryans'sFakePassword",  "ryan@mail.org",  "ryan",  "briggs",
        "m",  "ryan_briggs&%"));
        personDAO.addPerson(new Person("mike_briggs*&",  "briggs88",  "mike",  "briggs",
        "m",  "Jeff Briggs",  "Rosemarie Briggs",  ""));
        eventDAO.addEvent(new Event("briggsEvent1", "briggs88",  "mike_briggs*&",  -110.123,
        190.787,  "United States",  "Germantown",  "birth",  1997));
    }
    @Test
    public void invalidUsernameTest(){
        request = new FillRequest("absolutelyNoWayThisIsReal", 6);
        result = fillService.fill(request);
        assertEquals(result.resultMessage(),("Invalid username or generations parameter"));
        assertFalse(result.success());
    }
    @Test
    public void invalidGenerationsTest(){
        request = new FillRequest("briggs88", -2);
        result = fillService.fill(request);
        assertEquals(result.resultMessage(),("Invalid username or generations parameter"));
        assertFalse(result.success());
    }
    @Test
    public void validGenerationsTest(){
        request = new FillRequest(user, 1);
        result = fillService.fill(request);
        assertEquals(result.resultMessage(),("Successfully added 3 persons and 8 events to the database."));
        request = new FillRequest(user, generations);
        result = fillService.fill(request);
        //System.out.println(result.resultMessage());
        assertEquals(result.resultMessage(),("Successfully added 31 persons and 92 events to the database."));
        assertTrue(result.success());
        request = new FillRequest("ryan99", 2);
        result = fillService.fill(request);
        //System.out.println(result.resultMessage());
        assertEquals(result.resultMessage(),("Successfully added 7 persons and 20 events to the database."));
        assertTrue(result.success());
        request = new FillRequest(user, generations+1);
        result = fillService.fill(request);
        assertEquals(result.resultMessage(),("Successfully added 63 persons and 188 events to the database."));
        assertTrue(result.success());
    }
    @After
    public void cleanUp(){
        //eventDAO.removeEvents(user);
        userDAO.removeUser(user);
        userDAO.removeUser("ryan99");
        personDAO.removePersons(user);
        personDAO.removePersons("ryan99");
        eventDAO.removeEvents(user);
        eventDAO.removeEvents("ryan99");
    }
}
