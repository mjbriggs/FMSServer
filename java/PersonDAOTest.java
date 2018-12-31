package test;

import java.util.*;
import model.Person;
import dao.PersonDAO;

import org.junit.*;
import static org.junit.Assert.*;
import java.beans.Transient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manage.ConnectionManager;

public class PersonDAOTest {
        /** object that accesses the Events Table */
        PersonDAO personDaoObj;
        /** person arraylist to compare to DAO */
        private ArrayList<Person> testPersons;
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
            testPersons = new ArrayList<Person>();
            personDaoObj = new PersonDAO();
            Person tmpPerson = null;
            try{
                connection = connectionManager.connect();
                // System.out.println("Connection succeeded");
                stmt = connection.prepareStatement("select * from Persons");
                results = stmt.executeQuery();
                while(results.next()){
                    tmpPerson = new Person(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5)
                    , results.getString(6), results.getString(7), results.getString(8));
                    testPersons.add(tmpPerson);
                }
                results.close();
                stmt.close();
            }
            catch(SQLException err){
                System.out.println("\n" + err);
            // System.out.println("Connection failed");
            }
        }
        @Test 
        public void testGetAllPersons(){
            assertTrue(personDaoObj.getAllPersons().equals(testPersons));
        }
        @Test 
        public void testGetPersonByPersonID(){
            String validPersonID = testPersons.get(0).getPersonID();
            assertTrue(personDaoObj.getPerson(validPersonID).equals(testPersons.get(0)));
            assertFalse(personDaoObj.getPerson(validPersonID).equals(testPersons.get(testPersons.size()-1)));
            assertFalse(personDaoObj.getPerson(validPersonID).equals(null));    
        }
        @Test 
        public void testGetPersonByDescendent(){
            String validDescendent = testPersons.get(0).getDescendent();
            ArrayList<Person> validPersonList = new ArrayList<Person>();
            ArrayList<Person> invalidPersonList = new ArrayList<Person>();
            for(Person person : testPersons){
                if(person.getDescendent().equals(validDescendent))
                    validPersonList.add(person);
                else 
                    invalidPersonList.add(person);
            }
            assertTrue(personDaoObj.getPersons(validDescendent).equals(validPersonList));
            assertFalse(personDaoObj.getPersons(validDescendent).equals(invalidPersonList));
            assertFalse(personDaoObj.getPersons(validDescendent).equals(null));
        }
        //@Test 
        public void testAddPerson(){
            Person newPerson = new Person("new_personID", "Jim", "new", "person", "m", "newdad", "newmom", "newer_personID");
            Person newerPerson = new Person("newer_personID", "Jim", "newer", "person", "m", "newerdad", "newermom", "new_personID");
            int oldTableSize = personDaoObj.getAllPersons().size();
            personDaoObj.addPerson(newPerson);
            personDaoObj.addPerson(newerPerson);
            assertTrue(personDaoObj.getAllPersons().size() > oldTableSize);
        }
        @Test 
        public void testRemovePersons(){
            testAddPerson();
            int oldTableSize = personDaoObj.getAllPersons().size();
            personDaoObj.removePerson("new_personID");
            assertTrue(personDaoObj.getAllPersons().size() < oldTableSize);
            oldTableSize = personDaoObj.getAllPersons().size();
            personDaoObj.removePersons("Jim");
            // System.out.println("test remove event :: \n" + testGetAllEvents().toString() + "\n\n\n");
            //System.out.println(eventDaoObj.getAllEvents().toString());
            assertTrue(personDaoObj.getAllPersons().size() < oldTableSize);
        }

}