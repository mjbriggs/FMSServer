package test;

import java.util.*;
import model.User;
import dao.UserDAO;
import org.junit.*;
import static org.junit.Assert.*;
import java.beans.Transient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manage.ConnectionManager;

public class UserDAOTest {
    /** object that accesses the Events Table */
    UserDAO userDaoObj;
    /** User arraylist to compare to DAO */
    private ArrayList<User> testUsers;
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
        testUsers = new ArrayList<User>();
        userDaoObj = new UserDAO();
        User tmpUser = null;
        try{
            connection = connectionManager.connect();
            // System.out.println("Connection succeeded");
            stmt = connection.prepareStatement("select * from Users");
            results = stmt.executeQuery();
            while(results.next()){
                tmpUser = new User(results.getString(1), results.getString(2), results.getString(3), results.getString(4), 
                results.getString(5), results.getString(6), results.getString(7));
                testUsers.add(tmpUser);
              }
            results.close();
            stmt.close();
        }
        catch(SQLException err){
            System.out.println("\n" + err);
        // System.out.println("Connection failed");
        }
    }
   // @Test 
    public ArrayList<User> testGetAllUsers(){
        ArrayList<User> testUsers = new ArrayList<User>();
        User tmpUser = null;
        try{
            connection = connectionManager.connect();
            // System.out.println("Connection succeeded");
            stmt = connection.prepareStatement("select * from Users");
            results = stmt.executeQuery();
            while(results.next()){
                tmpUser = new User(results.getString(1), results.getString(2), results.getString(3), results.getString(4), 
                results.getString(5), results.getString(6), results.getString(7));
                testUsers.add(tmpUser);
              }
            results.close();
            stmt.close();
        }
        catch(SQLException err){
            System.out.println("\n" + err);
        // System.out.println("Connection failed");
        }
        assertTrue(userDaoObj.getAllUsers().equals(testUsers));
        return userDaoObj.getAllUsers();
    }
    @Test 
    public void testGetUser(){
        String validUsername = testUsers.get(0).getUsername();
        assertTrue(userDaoObj.getUser(validUsername).equals(testUsers.get(0)));
        if(testUsers.size() > 1)
            assertFalse(userDaoObj.getUser(validUsername).equals(testUsers.get(testUsers.size() - 1)));
        assertFalse(userDaoObj.getUser(validUsername).equals(null));
    }
    //@Test 
    public void testAddUser(){
        User newUser = new User("newUser", "newPassword", "newEmail", "newFirstName", 
        "newLastName", "m", "new_person_id");
        int oldTableSize = testGetAllUsers().size();
        userDaoObj.addUser(newUser);
        assertTrue(oldTableSize < userDaoObj.getAllUsers().size());
    }
    @Test 
    public void testRemoveUser(){
        testAddUser();
        int oldTableSize = userDaoObj.getAllUsers().size();
        userDaoObj.removeUser("newUser");
        assertTrue(userDaoObj.getAllUsers().size() < oldTableSize && testGetAllUsers().size() < oldTableSize);
    }
    
}