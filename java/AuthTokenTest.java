package test;

import java.util.*;
import model.*;
import org.junit.*;
import static org.junit.Assert.*;

public class AuthTokenTest{
  private AuthToken AuthTokenObj;


  @Before
  public void setUp(){
    AuthTokenObj = new AuthToken("user","token");
  }
  @Test
  public void testGetters(){
    assertEquals(AuthTokenObj.getUsername(), "user");
    assertEquals(AuthTokenObj.getToken(), "token");
   // assertEquals(AuthTokenObj.getPersonID(), "personID");
  }
  @Test
  public void testSetters(){
    AuthTokenObj.setUsername("newUser");
    AuthTokenObj.setToken("newToken");
    //AuthTokenObj.setPersonID("newPersonID");
    assertEquals(AuthTokenObj.getUsername(), "newUser");
    assertEquals(AuthTokenObj.getToken(), "newToken");
   // assertEquals(AuthTokenObj.getPersonID(), "newPersonID");
  }
  @Test
  public void testEquals(){
    AuthToken equalToken = new AuthToken("user","token");
    AuthToken notEqualToken = new AuthToken("newUser","newToken");
    AuthToken offByOneToken = new AuthToken("user","tokeen");
    AuthToken nullToken = null;
    assertTrue(AuthTokenObj.equals(equalToken));
    assertFalse(AuthTokenObj.equals(offByOneToken));
    assertFalse(AuthTokenObj.equals(notEqualToken));
    assertFalse(AuthTokenObj.equals(nullToken));
  }
}
