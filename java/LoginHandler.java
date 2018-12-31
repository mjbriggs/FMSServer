package handler;

import result.LoginResult;
import request.LoginRequest;
import service.LoginService;
import model.User;
import model.Person;
import GSON.JSONDecoder;

import java.io.Writer;
import java.net.InetSocketAddress;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  


public class LoginHandler implements HttpHandler {
    /** intereacts with databse to register user and create user info*/
    private LoginService loginService;
    /** contains event request information */
    private LoginRequest request;
    /** contains event result information */
    private LoginResult result;

    public void handle(HttpExchange t) throws IOException {
		System.out.println("Calling Login Handler");
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
        Gson gson = builder.create(); 

		String method = t.getRequestMethod();
        String auth = t.getRequestHeaders().getFirst("Authorization");
        JSONDecoder jsonDecoder = new JSONDecoder();
        Map<String, String> bodyParameters = jsonDecoder.getParameters(t,":",",");
		System.out.println("method: " + method);
        System.out.println("AuthToken: " + auth);
        System.out.println("Request body [\n" + bodyParameters.toString() + "\n]");
        t.sendResponseHeaders(HTTP_OK, 0);
        String [] parameters = t.getRequestURI().getPath().split("/");
        System.out.println("Parameter Array Length : " + parameters.length);
        loginService = new LoginService();

    
        request = new LoginRequest(bodyParameters.get("userName"), bodyParameters.get("password"));
        result = loginService.login(request);



        System.out.println("sending response");
        Writer out = new OutputStreamWriter(t.getResponseBody());
        if(!loginService.getError().equals(""))
            out.write(gson.toJson(loginService.getError()));
        else    
            out.write(gson.toJson(result));
        out.flush();
        out.close();
        t.getResponseBody().close();


	    System.out.println("response body: " + gson.toJson(result));
        System.out.println();
        
    }
  }