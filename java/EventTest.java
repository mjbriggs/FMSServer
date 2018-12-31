package test;

import java.util.*;
import model.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EventTest{
  private Event eventObj;

  @Before
  public void setUp(){
    eventObj = new Event("eventID",  "descendent",  "personID",  1.0,
     2.0,  "country",  "city",  "eventType",  3);
  }
  @Test
  public void testGetters(){
    assertEquals(eventObj.getEventID(), "eventID");
    assertEquals(eventObj.getDescendent(), "descendent");
    assertEquals(eventObj.getPersonID(), "personID");
    assertTrue(eventObj.getLatitude() ==  1.0);
    assertTrue(eventObj.getLongitude() ==  2.0);
    assertEquals(eventObj.getCountry(), "country");
    assertEquals(eventObj.getCity(), "city");
    assertEquals(eventObj.getEventType(), "eventType");
    assertEquals(eventObj.getYear(), 3);
  }
  @Test
  public void testSetters(){
    eventObj.setEventID("newEventID");
    eventObj.setDescendent("newDescendent");
    eventObj.setPersonID("newPersonID");
    eventObj.setLatitude(11.0);
    eventObj.setLongitude(22.0);
    eventObj.setCountry("newCountry");
    eventObj.setCity("newCity");
    eventObj.setEventType("newEventType");
    eventObj.setYear(33);
    assertEquals(eventObj.getEventID(), "newEventID");
    assertEquals(eventObj.getDescendent(), "newDescendent");
    assertEquals(eventObj.getPersonID(), "newPersonID");
    assertTrue(eventObj.getLatitude() ==  11.0);
    assertTrue(eventObj.getLongitude() ==  22.0);
    assertEquals(eventObj.getCountry(), "newCountry");
    assertEquals(eventObj.getCity(), "newCity");
    assertEquals(eventObj.getEventType(), "newEventType");
    assertEquals(eventObj.getYear(), 33);
  }
  @Test
  public void testEquals(){
    Event equalEvent = new Event("eventID",  "descendent",  "personID",  1.0,
     2.0,  "country",  "city",  "eventType",  3);
    Event notEqualEvent = new Event("neventID",  "ndescendent",  "npersonID",  11.0,
     22.0,  "ncountry",  "ncity",  "neventType",  33);
    Event offByOneEvent = new Event("eventID",  "descendent",  "personID",  1.0,
     2.0,  "country",  "city",  "eventType",  4);
    Event nullEvent = null;
    assertTrue(eventObj.equals(equalEvent));
    assertFalse(eventObj.equals(offByOneEvent));
    assertFalse(eventObj.equals(notEqualEvent));
    assertFalse(eventObj.equals(nullEvent));
  }
}
