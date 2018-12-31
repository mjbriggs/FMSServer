package handler;

import result.PersonResult;
import request.PersonRequest;
import service.PersonService;


import java.io.Writer;
import java.net.InetSocketAddress;
import java.io.OutputStreamWriter;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  


public class PersonHandler implements HttpHandler {
    /** Interacts with the person DAO to grab person or all persons */
    private PersonService personService;
    /** contains person request information */
    private PersonRequest request;
    /** contains person result information */
    private PersonResult result;

    public void handle(HttpExchange t) throws IOException {
		System.out.println("Calling PersonID Handler");
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
		Gson gson = builder.create(); 
		String method = t.getRequestMethod();
	    String auth = t.getRequestHeaders().getFirst("Authorization");
		System.out.println("method: " + method);
		System.out.println("AuthToken: " + auth);
        t.sendResponseHeaders(HTTP_OK, 0);
        String [] parameters = t.getRequestURI().getPath().split("/");
        System.out.println("Parameter Array Length : " + parameters.length);
        personService = new PersonService();
        if(parameters.length == 3){
            System.out.println("Person ID : " + parameters[2]);
            request = new PersonRequest(parameters[2]);
            result = personService.getPersonOrPersons(request, auth, "");
            //result = new PersonResult();
            //Will call person service with personID
            //request = new PersonRequest(parameters[2]);
            //result = personService.getPersonOrPersons(request, auth, username)
        }
        else if(parameters.length == 2){
            request = new PersonRequest();
            System.out.println("calling get all persons");
            result = personService.getPersonOrPersons(request, auth, "");
            System.out.println("finished running get all persons");
        }
        else {
            result = new PersonResult();
            result.setMessage("Invalid request parameters");
        }
        System.out.println("sending response");
        Writer out = new OutputStreamWriter(t.getResponseBody());
        if(result.errorMessage().equals("") && parameters.length == 2)
            out.write(gson.toJson(result.getAllPersons()));
        else if(result.errorMessage().equals("") && parameters.length == 3)
            out.write(gson.toJson(result.getOnePerson()));
        else    
            out.write(gson.toJson(result.errorMessage())); 
        out.flush();
        out.close();
        t.getResponseBody().close();


	    System.out.println("response body: " + gson.toJson(result));
        System.out.println();
        
    }
  }