package handler;

import result.FillResult;
import request.FillRequest;
import service.FillService;


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


public class FillHandler implements HttpHandler {
    /** Interacts with the fill DAO to grab fill or all fills */
    private FillService fillService;
    /** contains fill request information */
    private FillRequest request;
    /** contains fill result information */
    private FillResult result;

    public void handle(HttpExchange t) throws IOException {
		System.out.println("Calling Fill Handler");
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
        fillService = new FillService();
        if(parameters.length == 3){
            System.out.println("Fill User : " + parameters[2]);
            request = new FillRequest(parameters[2], 4);
            result = fillService.fill(request);
            //result = new FillResult();
            //Will call fill service with fillID
            //request = new FillRequest(parameters[2]);
            //result = fillService.getFillOrFills(request, auth, username)
        }
        else if(parameters.length == 4){
            System.out.println("Fill User : " + parameters[2]);
            System.out.println("Fill generations : " + parameters[3]);
            request = new FillRequest(parameters[2], Integer.parseInt(parameters[3]));
            result = fillService.fill(request);
        }
        else {
            result = new FillResult(0,0, "Invalid request parameters");
        }
        System.out.println("sending response");
        Writer out = new OutputStreamWriter(t.getResponseBody());
        out.write(gson.toJson(result.resultMessage()));
        out.flush();
        out.close();
        t.getResponseBody().close();


	    System.out.println("response body: " + gson.toJson(result));
        System.out.println();
        
    }
  }