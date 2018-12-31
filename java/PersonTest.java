package test;

import java.util.*;
import model.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PersonTest{
  private Person personObj;

  @Before
  public void setUp(){
    personObj = new Person("personID", "descendent",  "firstName",  "lastName",
     "gender",  "father",  "mother",  "spouse");
  }
  @Test
  public void testGetters(){
    assertEquals(personObj.getPersonID(), "personID");
    assertEquals(personObj.getDescendent(), "descendent");
    assertEquals(personObj.getFirstName(), "firstName");
    assertEquals(personObj.getLastName(), "lastName");
    assertEquals(personObj.getGender(), "gender");
    assertEquals(personObj.getFather(), "father");
    assertEquals(personObj.getMother(), "mother");
    assertEquals(personObj.getSpouse(), "spouse");
  }
  @Test
  public void testSetters(){
    personObj.setPersonID("newPersonID");
    personObj.setDescendent("newDescendent");
    personObj.setFirstName("newFirstName");
    personObj.setLastName("newLastName");
    personObj.setGender("newGender");
    personObj.setFather("newFather");
    personObj.setMother("newMother");
    personObj.setSpouse("newSpouse");
    assertEquals(personObj.getPersonID(), "newPersonID");
    assertEquals(personObj.getDescendent(), "newDescendent");
    assertEquals(personObj.getFirstName(), "newFirstName");
    assertEquals(personObj.getLastName(), "newLastName");
    assertEquals(personObj.getGender(), "newGender");
    assertEquals(personObj.getFather(), "newFather");
    assertEquals(personObj.getMother(), "newMother");
    assertEquals(personObj.getSpouse(), "newSpouse");
  }
  @Test
  public void testEquals(){
    Person equalPerson = new Person("personID", "descendent",  "firstName",  "lastName",
     "gender",  "father",  "mother",  "spouse");
    Person notEqualPerson = new Person("notpersonID", "notdescendent",  "notfirstName",  "notlastName",
     "notgender",  "notfather",  "notmother",  "notspouse");
    Person offByOnePerson = new Person("personID", "descendent",  "firstName",  "lastName",
     "gender",  "father",  "mother",  "spuse");
    Person nullPerson = null;
    assertTrue(personObj.equals(equalPerson));
    assertFalse(personObj.equals(offByOnePerson));
    assertFalse(personObj.equals(notEqualPerson));
    assertFalse(personObj.equals(nullPerson));
  }
}
