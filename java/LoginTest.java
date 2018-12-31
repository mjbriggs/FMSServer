package test;

import java.util.*;
import model.User;
import model.AuthToken;
import dao.UserDAO;
import dao.AuthTokenDAO;
import result.LoginResult;
import request.LoginRequest;
import service.LoginService;
import org.junit.*;
import static org.junit.Assert.*;



public class LoginTest {
    /**Login request object */
    private LoginRequest loginRequest;
    /**Login result object */
    private LoginResult loginResult;
    /**Login service object */
    private LoginService loginService;
    /** Object that accesses User data table */
    private UserDAO userDAO;
    /** Object that accesses AuthToken data table */
    private AuthTokenDAO tokenDAO;

    @Before 
    public void setUp(){
        loginRequest = null;
        loginResult = null;
        loginService = new LoginService();
        userDAO = new UserDAO();
        tokenDAO = new AuthTokenDAO();
        userDAO.addUser(new User("loginUser", "loginPassword", "login@login.com", "log", "in", "f", "login_User1"));
        loginRequest = new LoginRequest("loginUser", "loginPassword");
    }
    @Test 
    public void testInvalidLogin(){
        loginRequest = new LoginRequest("loginUser", "wrongPassword");
        loginResult = loginService.login(loginRequest);
        assertEquals(loginResult.getLoginAuthToken(),"");
        assertEquals(loginResult.getLoginUser(),"");
        assertTrue(loginService.getError().equals("Request property missing or has invalid value"));
    }
    @Test 
    public void testValidLogin(){
        loginResult = loginService.login(loginRequest);
        assertTrue(loginResult.getLoginAuthToken().equals(tokenDAO.getMostRecentAuthToken("loginUser").getToken()));
        assertTrue(loginResult.getLoginUser().equals(userDAO.getUser("loginUser").getUsername()));
        assertTrue(loginService.getError().equals(""));
    }
    @After 
    public void cleanUp(){
        userDAO.removeUser("loginUser");
        tokenDAO.removeToken("loginUser");
    }
}