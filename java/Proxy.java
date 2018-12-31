package client;

import java.io.*;
import java.net.*;
import java.util.*;

// send request to server and read response
public class Proxy {

    public static void main(String[] args) throws Exception {

	String host = "localhost";
	int port = 8888;

	System.out.printf("client connecting to: %s (port: %d)\n", host, port);
	System.out.println();

	Socket socket = new Socket(host, port);

	Scanner in = new Scanner(socket.getInputStream());
	PrintWriter out = new PrintWriter(socket.getOutputStream());

	String request = "[Client Request]";
	out.println(request);
	out.flush();

	System.out.println("client sent: " + request);
	System.out.println();

	String response = in.nextLine();

	System.out.println("client read: " + response);
	System.out.println();

    }

}