package test;

import java.util.*;
import model.User;
import model.AuthToken;
import dao.*;
import result.RegisterResult;
import request.RegisterRequest;
import service.RegisterService;
import org.junit.*;
import static org.junit.Assert.*;



public class RegisterTest {
    /** Service object that will register the user */
    private RegisterService registerService;
    /** Request object containing user to be registered */
    private RegisterRequest request;
    /** Result object containing valid token */
    private RegisterResult result;
    /** Object that accesses User data table */
    private UserDAO userDAO;
    /** Object that accesses AuthToken data table */
    private AuthTokenDAO tokenDAO;
    /** Object that accesses Person data table */
    private PersonDAO personDAO;
    /** Object that accesses Event data table */
    private EventDAO eventDAO;
    /**Valid User to add */
    private User validUser;
    /**existing User to add */
    private User existingUser;

    @Before
    public void setUp(){
        registerService = new RegisterService();
        request = null;
        result = null;
        userDAO = new UserDAO();
        tokenDAO = new AuthTokenDAO();
        personDAO = new PersonDAO();
        eventDAO = new EventDAO();
        existingUser = new User("existingUser", "p", "e", "E", "U", "m" , "EU");
        userDAO.addUser(existingUser);
        validUser = new User("usernamee", "ppassword",  "username@mail.yeet",  "use", "rname",
        "f",  "user_name_e");
    }
    @Test
    public void invalidUserTest(){
        request = new RegisterRequest(existingUser);
        result = registerService.register(request);
        assertEquals(registerService.getError(), "Username already taken by another user");
        request = new RegisterRequest(null);
        result = registerService.register(request);
        assertEquals(registerService.getError(), "Request property missing or has invalid value");
        request = new RegisterRequest(new User("existingUser", "", "", "", "", "m" , ""));
        result = registerService.register(request);
        assertEquals(registerService.getError(), "Request property missing or has invalid value");
        request = new RegisterRequest(new User("existingUser", "p", "e", "E", "U", "meat" , "EU"));
        result = registerService.register(request);
        assertEquals(registerService.getError(), "Request property missing or has invalid value");
    }
    @Test
    public void ValidUser(){
        request = new RegisterRequest(validUser);
        result = registerService.register(request);
        assertTrue(result.getUser() != null);
        System.out.println("result user " + result.getUser());
        assertTrue(result.getUser().equals(validUser.getUsername()));
        assertTrue(result.getAuthToken().equals(tokenDAO.getMostRecentAuthToken(validUser.getUsername()).getToken()));
        assertTrue(personDAO.getPersons(validUser.getUsername()).size() == 31);
        assertTrue(eventDAO.getEvents(validUser.getUsername()).size() == 92);
    }
    @After
    public void cleanUp(){
        userDAO.removeUser(validUser.getUsername());
        userDAO.removeUser(existingUser.getUsername());
        tokenDAO.removeToken(validUser.getUsername());
        personDAO.removePersons(validUser.getUsername());
        eventDAO.removeEvents(validUser.getUsername());
    }

}
