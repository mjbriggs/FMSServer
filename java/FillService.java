package service;
import model.Person;
import model.User;
import model.Event;
import request.FillRequest;
import result.FillResult;
import dao.PersonDAO;
import dao.EventDAO;
import dao.UserDAO;
import GSON.JSONDecoder;
import GSON.Location;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

/** FillService will interact with DAO to perform server FillRequest
* @author Michael Briggs
* @version 1.0 Oct 12, 2018
*/
public class FillService {
  /**username that we will associate with generational data*/
  private String username;
  /**number of persons that have been added*/
  private int persons;
  /**number of events that have been added*/
  private int events;
  /**number of generatinos to be added*/
  private int generations;
  /** states the success of the fill request*/
  private boolean success;
  /** message describing error that occured */
  private String error;

  /** Default Constructor
  */
  public FillService(){
    username = "";
    persons = -1;
    events = -1;
    generations = 4;
    success = false;
    error = "";
  }
  /**will populate the database for username with number of generations
  * @param r Request object that specifies the username and generations to be added
  * @return Fill result object containing operation information
  */
  public FillResult fill(FillRequest r){
    FillResult result = null;
    int maxYear = 2018;
    username = r.getUsername();
    generations = r.getGenerations();
    UserDAO userDAO = new UserDAO();
    Random random = new Random();
    ArrayList<String> differentEventTypes = arrayEventTypes();
    int eventTypesSize = differentEventTypes.size();
    int descendentCount = 0;
    int eventCount = 0;
    if(userDAO.getUser(username) == null || generations < 0){
      error = "Invalid username or generations parameter";
    }
    else{
      error = "";
      PersonDAO personDAO = new PersonDAO();
      EventDAO eventDAO = new EventDAO();
      JSONDecoder jsonDecoder = new JSONDecoder();
      jsonDecoder.loadLocalFiles();
      personDAO.removePersons(username);
      //System.out.println(eventDAO.getEvents(username));
      eventDAO.removeEvents(username);
     // System.out.println(eventDAO.getEvents(username));
      ArrayList<String> femaleNames = jsonDecoder.getFNames();
      ArrayList<String> maleNames = jsonDecoder.getMNames();
      ArrayList<String> surnames = jsonDecoder.getSNames();
      ArrayList<Location> locations = jsonDecoder.getLocations();
      int femaleNamesSize = femaleNames.size();
      int maleNamesSize = maleNames.size();
      int surnamesSize = surnames.size();
      int locationsSize = locations.size();
      int count = 0;
      descendentCount = descendentCount();
      String firstName = "";
      String lastName = "";
      String gender = "";
      String spouse = "";
      Location location = null;
      ArrayList<Person> generationToAdd = new ArrayList<Person>();
      ArrayList<Person> nextGeneration = new ArrayList<Person>();
      ArrayList<Event> marriagesToAdd = new ArrayList<Event>();
      String [] fatherName;
      String [] motherName;
      String personID1 = "";
      String personID2 = "";
      User userNameUser = userDAO.getUser(username);
      Person person = new Person(userNameUser.getPersonID(), username, userNameUser.getFirstName(), userNameUser.getLastName(),
      userNameUser.getGender(), maleNames.get(random.nextInt(maleNamesSize)) + "_" + userNameUser.getLastName(),
      femaleNames.get(random.nextInt(femaleNamesSize)) + "_" + userNameUser.getLastName(), "");
      Event event = new Event();
      generationToAdd.add(person);
      int baseYear = 1950;
      int preGenYear = baseYear - 80;
      while (count <= generations){ //add a person and 2 events for each person
        //System.out.println(count + " iteration, generation without parents " + (generations - 1));
       // System.out.println(generationToAdd.toString());
        if(count == generations - 1 || generations == 0){
          //System.out.println("Not adding parents");
          for(Person personToAdd : generationToAdd){
              personDAO.addPerson(personToAdd);
              int ECount = 0;
              for(Event eventToAdd : addTwoEvents(personToAdd.getPersonID(), username, baseYear)) {
                if(ECount == 0)
                   event = eventToAdd;
                eventDAO.addEvent(eventToAdd);
                ECount++;
              }
              fatherName = personToAdd.getFather().split("_");
              motherName = personToAdd.getMother().split("_");

              person = new Person("", username, fatherName[0],fatherName[1], "m", "", "", motherName[0] + "_" + motherName[1]);
              personID1 = person.generatePersonID();
              person.setPersonID(personID1);
              nextGeneration.add(person);

              person = new Person("", username, motherName[0],motherName[1], "f", "","", fatherName[0] + "_" + fatherName[1]);
              personID2 = person.generatePersonID();
              person.setPersonID(personID2);
              nextGeneration.add(person);

              for(Event marriageEvent : addTwoMarriages(personID1, personID2, username, preGenYear)){
              //  System.out.println("Marriage to add " + marriageEvent.toString());
                eventDAO.addEvent(marriageEvent);
              }
          }
        }
       else if(count == generations){
        // int personAddCount = 0;
        for(Person personToAdd : generationToAdd){
          personDAO.addPerson(personToAdd);
          for(Event eventToAdd : addTwoEvents(personToAdd.getPersonID(), username, baseYear)) {
            eventDAO.addEvent(eventToAdd);
          }
        /*  if(personAddCount % 2 == 1){
            for(Event marriageEvent : addTwoMarriages(personToAdd.getPersonID(), generationToAdd.get(personAddCount - 1).getPersonID(), username, preGenYear)){
              System.out.println("Marriage to add " + marriageEvent.toString());
              eventDAO.addEvent(marriageEvent);
            }
          }

          personAddCount++;*/
        }
       }
       else{
          //System.out.println("Adding parents");

          for(Person personToAdd : generationToAdd){
              personDAO.addPerson(personToAdd);
              int ECount = 0;

             for(Event eventToAdd : addTwoEvents(personToAdd.getPersonID(), username, baseYear)) {
               if(ECount == 0)
                  event = eventToAdd;
               eventDAO.addEvent(eventToAdd);
               ECount++;
             }
              fatherName = personToAdd.getFather().split("_");
              motherName = personToAdd.getMother().split("_");

              person = new Person("", username, fatherName[0],fatherName[1], "m", maleNames.get(random.nextInt(maleNamesSize)) + "_" + fatherName[1],
              femaleNames.get(random.nextInt(femaleNamesSize)) + "_" + fatherName[1], motherName[0] + "_" + motherName[1]);
              personID1 = person.generatePersonID();
              person.setPersonID(personID1);
              nextGeneration.add(person);


              person = new Person("", username, motherName[0],motherName[1], "f", maleNames.get(random.nextInt(maleNamesSize)) + "_" + motherName[1],
              femaleNames.get(random.nextInt(femaleNamesSize)) + "_" + motherName[1], fatherName[0] + "_" + fatherName[1]);
              personID2 = person.generatePersonID();
              person.setPersonID(personID2);
              nextGeneration.add(person);

              for(Event marriageEvent : addTwoMarriages(personID1, personID2, username, preGenYear)){
              //  System.out.println("Marriage to add " + marriageEvent.toString());
                eventDAO.addEvent(marriageEvent);
              }

            //  event = new Event(event.generateEventID(), )

          }
        }
        count ++;
        baseYear -= 80;
        preGenYear -= 80;
        generationToAdd.clear();
        for(Person nextPerson : nextGeneration){
          Person newPerson = nextPerson;
          generationToAdd.add(newPerson);
        }
       // System.out.println(generationToAdd.toString());
        nextGeneration.clear();





        /*




        lastName = surnames.get(random.nextInt(surnamesSize));
        if(count % 2 == 0){
          firstName = maleNames.get(random.nextInt(maleNamesSize));
          spouse = femaleNames.get(random.nextInt(femaleNamesSize)) + " " + lastName;
          gender = "m";
        }
        else {
          firstName = femaleNames.get(random.nextInt(femaleNamesSize));
          spouse = maleNames.get(random.nextInt(maleNamesSize)) + " " + lastName;
          gender = "f";
        }
        person = new Person("", username, firstName, lastName, gender, maleNames.get(random.nextInt(maleNamesSize)) + " " + surnames.get(random.nextInt(surnamesSize)),
        femaleNames.get(random.nextInt(femaleNamesSize)) + " " + surnames.get(random.nextInt(surnamesSize)), spouse);
        person.setPersonID(person.generatePersonID());
        //person = new Person(personIDIn, descendentIn, firstNameIn, lastNameIn,
        //genderIn, fatherIn, motherIn, spouseIn);
        count++;
        eventCount += 2;
        */
      }
      //eventDAO = new EventDAO();
      persons = personDAO.getPersons(username).size();
      //System.out.println("all events : \n" + eventDAO.getAllEvents().toString());
      //System.out.println("events by user : \n" + eventDAO.getEvents(username).toString());
      events = eventDAO.getEvents(username).size();
    }
    if((events < 1 || persons < 1) && (error.equals("")))
      error = "Internal Server Error";

    result = new FillResult(persons, events, error);
    return result;
  }
  /** If there is a failure, error message will be set and will describe the error
  * @return String that contains error description
  */
  public String getError(){ return error;  }
  private int descendentCount(){
    int sum = 0;
    for(int i = 1; i <= generations; i++){
      sum += Math.pow(2,i);
    }
    sum++;
    System.out.println("descendent count " + sum);
    return sum;
  }
  private ArrayList<Event> addTwoEvents(String personID, String username, int birthYear){
    Random random = new Random();
    ArrayList<String> differentEventTypes = arrayEventTypes();
    int eventTypesSize = differentEventTypes.size();
    JSONDecoder jsonDecoder = new JSONDecoder();
    jsonDecoder.loadLocalFiles();
    ArrayList<Location> locations = jsonDecoder.getLocations();
    Location location = null;
    int locationsSize = locations.size();
    Event event = new Event();
    ArrayList<Event> eventsToAdd = new ArrayList<>();
    int maxYear = 2017-80-35;
    //int birthYear = Math.abs(random.nextInt() % maxYear);

    location = locations.get(random.nextInt(locationsSize));

    event = new Event(event.generateEventID(), username,  personID,  location.getLatitude(),
    location.getLongitude(),  location.getCountry(),  location.getCity(),  "birth", birthYear);
    eventsToAdd.add(event);
    //eventDAO.addEvent(event);
    location = locations.get(random.nextInt(locationsSize));
    
    event = new Event(event.generateEventID(), username,  personID,  location.getLatitude(),
    location.getLongitude(),  location.getCountry(),  location.getCity(),  "death", birthYear + 65);
    eventsToAdd.add(event);
    //eventDAO.addEvent(event);
    return eventsToAdd;
  }
  private ArrayList<Event> addTwoMarriages(String personID1, String personID2, String username, int year){
    Random random = new Random();
    ArrayList<String> differentEventTypes = arrayEventTypes();
    int eventTypesSize = differentEventTypes.size();
    JSONDecoder jsonDecoder = new JSONDecoder();
    jsonDecoder.loadLocalFiles();
    ArrayList<Location> locations = jsonDecoder.getLocations();
    Location location = null;
    int locationsSize = locations.size();
    Event event = new Event();
    ArrayList<Event> eventsToAdd = new ArrayList<>();
    int maxYear = 2018-80;
    year += 35; //adding 35 years to persons birthyear
    location = locations.get(random.nextInt(locationsSize));

    event = new Event(event.generateEventID(), username,  personID1,  location.getLatitude(),
    location.getLongitude(),  location.getCountry(),  location.getCity(),  "marriage", year);
    eventsToAdd.add(event);
    //eventDAO.addEvent(event);

    event = new Event(event.generateEventID(), username,  personID2,  location.getLatitude(),
    location.getLongitude(),  location.getCountry(),  location.getCity(),  "marriage", year);
    eventsToAdd.add(event);
    //eventDAO.addEvent(event);
    return eventsToAdd;
  }
  private ArrayList<String> arrayEventTypes(){
    ArrayList<String> eventTypes = new ArrayList<>();
    eventTypes.add("birth");
    eventTypes.add("marriage");
    eventTypes.add("death");
    eventTypes.add("baptism");
    eventTypes.add("christening");
    eventTypes.add("arrested");
    eventTypes.add("graduation");
    eventTypes.add("had a child");
    eventTypes.add("divorce");
    return eventTypes;
  }
}
