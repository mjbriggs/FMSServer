package test;

import java.util.*;
import model.Person;
import model.AuthToken;
import dao.PersonDAO;
import dao.AuthTokenDAO;
import result.PersonResult;
import request.PersonRequest;
import service.PersonService;
import org.junit.*;
import static org.junit.Assert.*;



public class PersonServiceTest {
    /** person service object */
    private PersonService personService;
    /** accesses auth token database*/
    private AuthTokenDAO authTokenDAO;
    /** accesses persons database */
    private PersonDAO personDAO;
    /** valid token to be used */
    private AuthToken validToken;
    /** person request */
    private PersonRequest request;
    /** result of person service */
    private PersonResult result;

    @Before
    public void setUp(){
        personService = new PersonService();
        authTokenDAO = new AuthTokenDAO();
        personDAO = new PersonDAO();
        validToken = null;
        request = null;
        result = null;
        resetTables();
    }
    public void updateTables(){ 
        authTokenDAO.addAuthToken("validUser");
        validToken = authTokenDAO.getMostRecentAuthToken("validUser");
        personDAO.addPerson(new Person("personIDIn",  "validUser",  "first1",  "last1", "f",  "father1",  "mother1",  "spouse1"));
        personDAO.addPerson(new Person("personIDIn2",  "validUser",  "first2",  "last2", "m",  "father2",  "",  "spouse2"));
        personDAO.addPerson(new Person("personIDIn3",  "validUser",  "first",  "last", "f",  "father3",  "mother1",  ""));

    }
    public void resetTables(){
        authTokenDAO.removeToken("invalidUser");
        authTokenDAO.removeToken("validUser");
        personDAO.removePersons("validUser");
    }
    @Test
    public void testAllPersonService(){
        //assumes that database is populated, will need to use fill service for future tests
        //if full database is needed, read in familyMapTables.sql 
        request = new PersonRequest();
        updateTables();
        result = personService.getPersonOrPersons(request, validToken.getToken(), validToken.getUsername());
        assertTrue(result.getAllPersons().size() > 2);
        assertTrue(result.getAllPersons().equals(personDAO.getPersons("validUser")));
        resetTables();
    }
    @Test 
    public void testOnePersonService(){
        updateTables();
        request = new PersonRequest("personIDIn2");
        result = personService.getPersonOrPersons(request, validToken.getToken(), validToken.getUsername());
        assertTrue(result.success());
        assertTrue(result.getOnePerson().equals(personDAO.getPerson("personIDIn2")));
        resetTables();
    }
    @Test 
    public void testInvalidToken(){
        updateTables();
        result = personService.getPersonOrPersons(request, validToken.getToken() + "sike", validToken.getUsername());
        assertFalse(result.success());
        assertTrue(result.errorMessage().equals("Invalid authToken"));
        assertTrue(result.getAllPersons() == null);
        assertTrue(result.getOnePerson() == null);
        resetTables();
    }
    @Test 
    public void testInvalidPersonID(){
        updateTables();
        request = new PersonRequest("personIDInF4ke");
        result = personService.getPersonOrPersons(request, validToken.getToken(), validToken.getUsername());
        assertFalse(result.success());
        assertTrue(result.errorMessage().equals("Invalid personID parameter"));
        assertTrue(result.getAllPersons() == null);
        assertTrue(result.getOnePerson() == null);
        resetTables();
    }
    @Test 
    public void testInvalidUser(){
        updateTables();
        authTokenDAO.addAuthToken("invalidUser");
        AuthToken invalidToken = authTokenDAO.getAuthTokens("invalidUser").get(0);
        request = new PersonRequest("personIDIn");
        result = personService.getPersonOrPersons(request, invalidToken.getToken(), invalidToken.getUsername());
        assertFalse(result.success());
        assertTrue(result.errorMessage().equals("Requested person does not belong to this user"));
        assertTrue(result.getAllPersons() == null);
        assertTrue(result.getOnePerson() == null);
        resetTables();
    }
}
