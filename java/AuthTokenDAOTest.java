package test;

import java.util.*;
import model.AuthToken;
import model.User;
import dao.AuthTokenDAO;
import org.junit.*;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenDAOTest{
  /** object that accesses AuthToken table */
  private AuthTokenDAO AuthTokenDaoObj;
  /** name of databse we connect to */
  private String dbName;
  /** AuthToken array to compare to DAO */
  private ArrayList<AuthToken> testAuthTokens;
  /** Object that creates a sql connection */
  private Connection connection;
  /**object that contains sql commands */
  PreparedStatement stmt = null;
  /** object that contains sql results */
	ResultSet results = null;

  @Before
  public void setUp(){
    testAuthTokens = new ArrayList<AuthToken>();
    AuthTokenDaoObj = new AuthTokenDAO();
    AuthToken tmpToken = null;
    try{
      String driver = "org.sqlite.JDBC";
      Class.forName(driver);
      dbName = "jdbc:sqlite:../fmsDataBase/fms.db";
      connection = DriverManager.getConnection(dbName);
     // System.out.println("Connection succeeded");
      stmt = connection.prepareStatement("select * from AuthTokens");
      results = stmt.executeQuery();
      while(results.next()){
        tmpToken = new AuthToken(results.getString(1), results.getString(2));
        testAuthTokens.add(tmpToken);
      }
      results.close();
      stmt.close();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
     // System.out.println("Connection failed");
    }
    catch(ClassNotFoundException err){
      System.out.println("\n" + err);
      //System.out.println("Connection failed");
    }
  }
  @Test
  public void testConnection(){
    assertEquals(AuthTokenDaoObj.getConnection(), "Connection succeeded");
  }
  //@Test 
  public ArrayList<AuthToken> getAllTokens(){
    ArrayList<AuthToken> testUserTokens = new ArrayList<AuthToken>();
    AuthToken tmpToken = null;
    try{
      stmt = connection.prepareStatement("select * from AuthTokens");
      results = stmt.executeQuery();
      while(results.next()){
        tmpToken = new AuthToken(results.getString(1), results.getString(2));
        testUserTokens.add(tmpToken);
      }
      results.close();
      stmt.close();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    //System.out.println(testUserTokens);
    assertEquals(AuthTokenDaoObj.getAllAuthTokens(), testUserTokens);
    return testUserTokens;
  }
  @Test 
  public void testGetTokensByUsername(){
    String testUsername = testAuthTokens.get(0).getUsername();
    String falseTestUsername = testAuthTokens.get(1).getUsername();
    //System.out.println(falseTestUsername);
    ArrayList<AuthToken> testUserTokens = new ArrayList<AuthToken>();
    AuthToken tmpToken = null;
    try{
      stmt = connection.prepareStatement("select * from AuthTokens where username = ?");
      stmt.setString(1, testUsername);
      results = stmt.executeQuery();
      while(results.next()){
        tmpToken = new AuthToken(results.getString(1), results.getString(2));
        testUserTokens.add(tmpToken);
      }
      results.close();
      stmt.close();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
    assertTrue(AuthTokenDaoObj.getAuthTokens(testUsername).equals(testUserTokens));
    assertFalse(AuthTokenDaoObj.getAuthTokens(testUsername).equals(null));
    try{
      testUserTokens.clear();
      stmt = connection.prepareStatement("select * from AuthTokens where username = ?");
      stmt.setString(1, falseTestUsername);
      results = stmt.executeQuery();
      while(results.next()){
        tmpToken = new AuthToken(results.getString(1), results.getString(2));
        testUserTokens.add(tmpToken);
      }
      results.close();
      stmt.close();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
   // System.out.println(testUserTokens.toString());
    //System.out.println(AuthTokenDaoObj.getAuthTokens(testUsername).toString());
    if(!testUsername.equals(falseTestUsername))
      assertFalse(AuthTokenDaoObj.getAuthTokens(testUsername).equals(testUserTokens));
  }
  public void testGetTokenByToken(String token, String user){
    assertEquals(AuthTokenDaoObj.getByToken(token).getUsername(), user);
  }
  @Test 
  public void addToken(){ 
    String newUserName = "SuperNEWname";
    int oldTableSize = AuthTokenDaoObj.getAllAuthTokens().size();
    AuthTokenDaoObj.addAuthToken(newUserName);
    AuthTokenDaoObj.addAuthToken(newUserName);
    assertTrue(getAllTokens().size() > oldTableSize && AuthTokenDaoObj.getAllAuthTokens().size() > oldTableSize);  

  }
  @Test 
  public void testValidation(){
    String user = "SuperNEWname";
    String token = AuthTokenDaoObj.getAuthTokens(user).get(0).getToken();
    assertTrue(AuthTokenDaoObj.validate(token, user));
    assertFalse(AuthTokenDaoObj.validate(token, "sheila"));
    assertFalse(AuthTokenDaoObj.validate("fakeToken", user));
    testGetTokenByToken(token, user);
  }
  @Test 
  public void removeToken() throws SQLException{
    String newUserName = "SuperNEWname";
    int oldTableSize = AuthTokenDaoObj.getAllAuthTokens().size();
    AuthTokenDaoObj.removeToken(newUserName);
    assertTrue(getAllTokens().size() < oldTableSize && AuthTokenDaoObj.getAllAuthTokens().size() < oldTableSize);    
  }
  @After
  public void closeConnection(){
    try{
      connection.close();
      AuthTokenDaoObj.closeConnection();
    }
    catch(SQLException err){
      System.out.println("\n" + err);
    }
  }
}
