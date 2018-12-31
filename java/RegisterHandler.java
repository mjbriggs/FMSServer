package handler;

import result.RegisterResult;
import request.RegisterRequest;
import service.RegisterService;
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


public class RegisterHandler implements HttpHandler {
    /** intereacts with databse to register user and create user info*/
    private RegisterService registerService;
    /** contains event request information */
    private RegisterRequest request;
    /** contains event result information */
    private RegisterResult result;

    public void handle(HttpExchange t) throws IOException {
		System.out.println("Calling Register Handler");
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
        registerService = new RegisterService();

        User newUser = new User();
        newUser.setUsername(bodyParameters.get("userName"));
        newUser.setPassword(bodyParameters.get("password"));
        newUser.setEmail(bodyParameters.get("email"));
        System.out.println(bodyParameters.get("firstName"));
        newUser.setFirstName(bodyParameters.get("firstName"));
        newUser.setLastName(bodyParameters.get("lastName"));
        newUser.setGender(bodyParameters.get("gender"));
        Person idGenerator = new Person();
        idGenerator.setFirstName(newUser.getFirstName());
        idGenerator.setLastName(newUser.getLastName());
        newUser.setPersonID(idGenerator.generatePersonID());

        request = new RegisterRequest(newUser);
        result = registerService.register(request);



        System.out.println("sending response");
        Writer out = new OutputStreamWriter(t.getResponseBody());
        
        if(registerService.getError().equals("")){
            out.write(gson.toJson(result));
        }
        else{
            out.write(gson.toJson(registerService.getError()));
        }
        out.flush();
        out.close();
        t.getResponseBody().close();


	    System.out.println("response body: " + gson.toJson(result));
        System.out.println();
        
    }
  }