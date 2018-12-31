package test;

import java.util.*;
import model.Event;
import model.AuthToken;
import dao.EventDAO;
import dao.AuthTokenDAO;
import result.EventResult;
import request.EventRequest;
import service.EventService;
import org.junit.*;
import static org.junit.Assert.*;



public class EventServiceTest {
    /** event service object */
    private EventService eventService;
    /** accesses auth token database*/
    private AuthTokenDAO authTokenDAO;
    /** accesses events database */
    private EventDAO eventDAO;
    /** valid token to be used */
    private AuthToken validToken;
    /** event request */
    private EventRequest request;
    /** result of event service */
    private EventResult result;

    @Before
    public void setUp(){
        eventService = new EventService();
        authTokenDAO = new AuthTokenDAO();
        eventDAO = new EventDAO();
        validToken = null;
        request = null;
        result = null;
        resetTables();
    }
    public void updateTables(){ 
        authTokenDAO.addAuthToken("validUser");
        validToken = authTokenDAO.getAuthTokens("validUser").get(0);
        eventDAO.addEvent(new Event("eventIDIn",  "validUser",  "personIDIn",  0,
         1,  "countryIn",  "cityIn",  "eventTypeIn",  3));
         eventDAO.addEvent(new Event("eventIDIn2",  "validUser",  "personIDIn",  0,
         1,  "countryIn",  "cityIn",  "eventTypeIn",  3));
         eventDAO.addEvent(new Event("eventIDIn3",  "validUser",  "personIDIn",  0,
         1,  "countryIn",  "cityIn",  "eventTypeIn",  3));
    }
    public void resetTables(){
        authTokenDAO.removeToken("invalidUser");
        authTokenDAO.removeToken("validUser");
        eventDAO.removeEvents("validUser");
    }
    @Test
    public void testAllEventService(){
        //assumes that database is populated, will need to use fill service for future tests
        //if full database is needed, read in familyMapTables.sql 
        request = new EventRequest();
        updateTables();
        result = eventService.getEventOrEvents(request, validToken.getToken(), "");
        assertTrue(result.getAllEvents().size() > 2);
        resetTables();
    }
    @Test 
    public void testOneEventService(){
        updateTables();
        request = new EventRequest("eventIDIn2");
        result = eventService.getEventOrEvents(request, validToken.getToken(), validToken.getUsername());
        assertTrue(result.getOneEvent().equals(eventDAO.getEvent("eventIDIn2")));
        resetTables();
    }
    @Test 
    public void testInvalidToken(){
        updateTables();
        result = eventService.getEventOrEvents(request, validToken.getToken() + "sike", validToken.getUsername());
        assertFalse(result.success());
        assertTrue(result.errorMessage().equals("Invalid authToken"));
        resetTables();
    }
    @Test 
    public void testInvalidEventID(){
        updateTables();
        request = new EventRequest("eventIDInF4ke");
        result = eventService.getEventOrEvents(request, validToken.getToken(), validToken.getUsername());
        assertFalse(result.success());
        assertTrue(result.errorMessage().equals("Invalid eventID parameter"));
        resetTables();
    }
    @Test 
    public void testInvalidUser(){
        updateTables();
        authTokenDAO.addAuthToken("invalidUser");
        AuthToken invalidToken = authTokenDAO.getAuthTokens("invalidUser").get(0);
        request = new EventRequest("eventIDIn");
        result = eventService.getEventOrEvents(request, invalidToken.getToken(), invalidToken.getUsername());
        assertFalse(result.success());
        assertTrue(result.errorMessage().equals("Requested event does not belong to this user"));
        resetTables();
    }
}