package handler;

import result.EventResult;
import request.EventRequest;
import service.EventService;


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


public class EventHandler implements HttpHandler {
    /** Interacts with the event DAO to grab event or all events */
    private EventService eventService;
    /** contains event request information */
    private EventRequest request;
    /** contains event result information */
    private EventResult result;

    public void handle(HttpExchange t) throws IOException {
		System.out.println("Calling EventID Handler");
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
        eventService = new EventService();
        if(parameters.length == 3){
            System.out.println("Event ID : " + parameters[2]);
            request = new EventRequest(parameters[2]);
            result = eventService.getEventOrEvents(request, auth, "");
            //result = new EventResult();
            //Will call event service with eventID
            //request = new EventRequest(parameters[2]);
            //result = eventService.getEventOrEvents(request, auth, username)
        }
        else if(parameters.length == 2){
            request = new EventRequest();
            System.out.println("calling get all events, request :: " + request.returningOneEvent());
            result = eventService.getEventOrEvents(request, auth, "");
            System.out.println("finished running get all events");
        }
        else {
            result = new EventResult();
            result.setMessage("Invalid request parameters");
        }
        System.out.println("sending response");
        Writer out = new OutputStreamWriter(t.getResponseBody());
        if(result.errorMessage().equals("") && parameters.length == 2)
            out.write(gson.toJson(result.getAllEvents()));
        else if(result.errorMessage().equals("") && parameters.length == 3)
            out.write(gson.toJson(result.getOneEvent()));
        else    
            out.write(gson.toJson(result.errorMessage())); 
        out.flush();
        out.close();
        t.getResponseBody().close();


	    System.out.println("response body: " + gson.toJson(result));
        System.out.println();
        
    }
  }