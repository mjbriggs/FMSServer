package test;

import java.util.*;
import model.*;
import org.junit.*;
import static org.junit.Assert.*;

public class UserTest{
  private User userObj;

  @Before
  public void setUp(){
    userObj = new User("username",  "password",   "email",  "firstName",  "lastName",
     "gender",  "personID");
  }
  @Test
  public void testGetters(){
    assertEquals(userObj.getUsername(), "username");
    assertEquals(userObj.getPassword(), "password");
    assertEquals(userObj.getFirstName(), "firstName");
    assertEquals(userObj.getLastName(), "lastName");
    assertEquals(userObj.getGender(), "gender");
    assertEquals(userObj.getEmail(), "email");
    assertEquals(userObj.getPersonID(), "personID");
  }
  @Test
  public void testSetters(){
    userObj.setUsername("newUsername");
    userObj.setPassword("newPassword");
    userObj.setFirstName("newFirstName");
    userObj.setLastName("newLastName");
    userObj.setGender("newGender");
    userObj.setEmail("newEmail");
    userObj.setPersonID("newPersonID");
    assertEquals(userObj.getUsername(), "newUsername");
    assertEquals(userObj.getPassword(), "newPassword");
    assertEquals(userObj.getFirstName(), "newFirstName");
    assertEquals(userObj.getLastName(), "newLastName");
    assertEquals(userObj.getGender(), "newGender");
    assertEquals(userObj.getEmail(), "newEmail");
    assertEquals(userObj.getPersonID(), "newPersonID");
  }
  @Test
  public void testEquals(){
    User equalUser = new User("username",  "password",  "email",  "firstName",  "lastName",
     "gender",  "personID");
    User notEqualUser = new User("nusername",  "pnassword", "emnail", "nfirstName",  "lasntName",
     "gender",   "personInD");
    User offByOneUser = new User("username",  "password",  "email", "firstName",  "lastName",
     "gender",   "perssonID");
    User nullUser = null;
    assertTrue(userObj.equals(equalUser));
    assertFalse(userObj.equals(offByOneUser));
    assertFalse(userObj.equals(notEqualUser));
    assertFalse(userObj.equals(nullUser));
  }
  @Test 
  public void testValidUser(){
    User blankFields = new User("",  "",  "",  null,  "", "m",  "");
    User invalidGender = new User("username",  "password",  "email",  "firstName",  "lastName",
    "falseGender",  "personID");
    User vaildMale = new User("username",  "password",  "email",  "firstName",  "lastName",
    "m",  "personID");
    User validFemale = new User("nusername",  "pnassword", "emnail", "nfirstName",  "lasntName",
    "f",   "personInD");
    assertFalse(blankFields.validUser());
    assertFalse(invalidGender.validUser());
    assertTrue(vaildMale.validUser());
    assertTrue(validFemale.validUser());

  }
}
