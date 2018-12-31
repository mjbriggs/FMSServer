package handler;

import result.ClearResult;
import service.ClearService;
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


public class ClearHandler implements HttpHandler {
    /** clears entire database*/
    private ClearService clearService;
    /** contains event result information */
    private ClearResult result;

    public void handle(HttpExchange t) throws IOException {
		System.out.println("Calling Clear Handler");
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
        Gson gson = builder.create(); 

		String method = t.getRequestMethod();
        String auth = t.getRequestHeaders().getFirst("Authorization");
        JSONDecoder jsonDecoder = new JSONDecoder();
       // Map<String, String> bodyParameters = jsonDecoder.getParameters(t);
		System.out.println("method: " + method);
        System.out.println("AuthToken: " + auth);
        t.sendResponseHeaders(HTTP_OK, 0);
        String [] parameters = t.getRequestURI().getPath().split("/");
        clearService = new ClearService();

        try{
            result = clearService.clear();
        }
        catch(Exception ex){
            System.out.println("\n" + ex);
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