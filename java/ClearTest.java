package test;

import java.util.*;
import model.*;
import dao.*;
import service.ClearService;
import result.ClearResult;
import org.junit.*;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClearTest{
    /** will perform server clear operation */
    private ClearService clearService; 
    @Before
    public void setUp(){
       clearService = new ClearService();
    }
    @Test 
    public void testClearService() throws SQLException{
        EventDAO eventDAO = new EventDAO();
        eventDAO.addEvent(new Event("eventIDIn",  "descendentIn",  "personIDIn",  0,
        1,  "countryIn",  "cityIn",  "eventTypeIn",  2));
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(new User( "usernameIn",  "passwordIn",  "firstNameIn",  "lastNameIn",
        "genderIn",  "emailIn",  "personIDIn"));
        PersonDAO personDAO = new PersonDAO();
        personDAO.addPerson(new Person( "personIDIn",  "descendentIn",  "firstNameIn",  "lastNameIn",
        "genderIn",  "fatherIn",  "motherIn",  "spouseIn"));
        AuthTokenDAO authtokenDAO = new AuthTokenDAO();
        authtokenDAO.addAuthToken("usernameIn");
        ClearResult clearResult = clearService.clear();
        assertTrue(clearResult.success() == true);
        assertTrue(clearResult.resultMessage().equals("Clear succeeded"));
    }

}