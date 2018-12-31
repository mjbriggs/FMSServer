package server;

import service.*;
import result.*;
import request.*;
import handler.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  

public class Server {

  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/css/main.css", new CSSHandler());
	  server.createContext("/", new RootHandler());
    server.createContext("/event", new EventHandler());
    server.createContext("/person", new PersonHandler());
    server.createContext("/user/register", new RegisterHandler());
    server.createContext("/user/login", new LoginHandler());
    server.createContext("/clear", new ClearHandler());
    server.createContext("/load", new LoadHandler());
    server.createContext("/fill/", new FillHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
    System.out.println("Server running on port 8080");
  }

  static class CSSHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        File file = new File ("../../web/css/main.css");
        byte [] bytearray  = new byte [(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(bytearray, 0, bytearray.length);
  
        // ok, we are ready to send the response.
        t.sendResponseHeaders(200, file.length());
        OutputStream os = t.getResponseBody();
        System.out.println("loading main.css");
        os.write(bytearray,0,bytearray.length);
        os.close();
    }
  }

  static class RootHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {

      // add the required response header for a PDF file
     /* Headers h = t.getResponseHeaders();
      h.add("Content-Type", "application/pdf");*/

      // a PDF (you provide your own!)
      File file = new File ("../../web/index.html~");
      byte [] bytearray  = new byte [(int)file.length()];
      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis);
      bis.read(bytearray, 0, bytearray.length);

      // ok, we are ready to send the response.
      t.sendResponseHeaders(200, file.length());
      OutputStream os = t.getResponseBody();
      System.out.println("loading index.html");
      os.write(bytearray,0,bytearray.length);
      os.close();
    }
  }
  /*static class EventHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
		System.out.println("Calling EventHandler");
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
		Gson gson = builder.create(); 
		String method = t.getRequestMethod();
	    String auth = t.getRequestHeaders().getFirst("Authorization");
		System.out.println("method: " + method);
		System.out.println("AuthToken: " + auth);
        t.sendResponseHeaders(HTTP_OK, 0);

	    Writer out = new OutputStreamWriter(t.getResponseBody());
	    gson.toJson("Event", out);
	    out.close();


	    System.out.println("response body: " + gson.toJson("Event"));
        System.out.println();
        
       /* OutputStream os = t.getResponseBody();
        os.write("Events".getBytes());
        os.close();*/
        /*File file = new File ("/Users/michaelbriggs/Desktop/FMS/web/css/main.css");
        byte [] bytearray  = new byte [(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(bytearray, 0, bytearray.length);
  
        // ok, we are ready to send the response.
        t.sendResponseHeaders(200, file.length());
        OutputStream os = t.getResponseBody();
        System.out.println("loading main.css");
        os.write(bytearray,0,bytearray.length);
        os.close();*/
  //} 
  //}
}
