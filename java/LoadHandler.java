package handler;

import result.LoadResult;
import request.LoadRequest;
import service.LoadService;
import GSON.JSONDecoder;
import model.*;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

//import org.json.JSONArray;
//import org.json.JSONObject;


public class LoadHandler implements HttpHandler {
    /** will clear database and load given data */
    private LoadService loadService;
    /** contains load request information */
    private LoadRequest request;
    /** contains load result information */
    private LoadResult result;

    public void handle(HttpExchange t) throws IOException {
		System.out.println("Calling Load Handler");
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
        Gson gson = builder.create(); 
        JSONDecoder jsonDecoder = new JSONDecoder();
        //Map<String, String> bodyParameters = jsonDecoder.getParameters(t, "[","],");
        String reqBody = jsonDecoder.readString(t.getRequestBody());
        JsonObject obj = new JsonObject();
        JsonArray userArray = obj.getAsJsonArray("users");
        JsonArray personArray = obj.getAsJsonArray("persons");
        JsonArray eventArray = obj.getAsJsonArray("events");
		String method = t.getRequestMethod();
	    String auth = t.getRequestHeaders().getFirst("Authorization");
		System.out.println("method: " + method);
        System.out.println("AuthToken: " + auth);
        System.out.println("Request body {\n" + reqBody + "}\n");        
        t.sendResponseHeaders(HTTP_OK, 0);
        String [] parameters = t.getRequestURI().getPath().split("/");
        System.out.println("Parameter Array Length : " + parameters.length);

        loadService = new LoadService();
        request = gson.fromJson(reqBody, LoadRequest.class);
        result = loadService.load(request);
        


        System.out.println("sending response");
        Writer out = new OutputStreamWriter(t.getResponseBody());
        out.write(gson.toJson(result.resultMessage())); 
        out.flush();
        out.close();
        t.getResponseBody().close();


	    System.out.println("response body: " + gson.toJson(result.resultMessage()));
        System.out.println();
        
    }
  }
