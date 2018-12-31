package GSON;

import java.util.Scanner;
import GSON.Location;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  
import java.util.Scanner;
import java.io.File;
import java.lang.StringBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.io.*;
import com.sun.net.httpserver.HttpExchange;




public class JSONDecoder { 
    private Names fNames;
    private Names sNames;
    private Names mNames;
    private Locations locations;

    public JSONDecoder(){}
    public ArrayList<String> getFNames(){
        return fNames.get();
    }
    public ArrayList<String> getSNames(){
        return sNames.get();
    }
    public ArrayList<String> getMNames(){
        return mNames.get();
    }
    public ArrayList<Location> getLocations(){
        return locations.get();
    }
    public void loadLocalFiles() { 
     // String jsonString = "{\"name\":\"Mahesh\", \"grade\":\"freshman\", \"age\":21}"; 
        String jsonFNamesFile = "fnames.json";
        String jsonSNamesFile = "snames.json";
        String jsonMNamesFile = "mnames.json";
        String jsonLocationsFile = "locations.json";
        String fNamesString = "";
        String sNamesString = "";
        String mNamesString = "";
        String locationsString = "";
        GsonBuilder builder = new GsonBuilder(); 
        builder.setPrettyPrinting(); 
        fNamesString = readFile(jsonFNamesFile);
        sNamesString = readFile(jsonSNamesFile);
        mNamesString = readFile(jsonMNamesFile);
        locationsString = readFile(jsonLocationsFile);
        //System.out.println(fNamesString);

        Gson gson = builder.create(); 
        //Student student = gson.fromJson(jsonString, Student.class); 
        fNames =  gson.fromJson(fNamesString, Names.class); 
        sNames =  gson.fromJson(sNamesString, Names.class); 
        mNames =  gson.fromJson(mNamesString, Names.class); 
        locations = gson.fromJson(locationsString, Locations.class); 
       /*
        System.out.println("fNames : \n" + fNames.toString());
        System.out.println("sNames : \n" + sNames.toString());
        System.out.println("mNames : \n" + mNames.toString());
        System.out.println("locations : \n" + locations.toString());
        */
        //System.out.println(Arrays.toString(fNames));    
      
        //jsonString = gson.toJson(student); 
        //System.out.println(jsonString);  
    }     
   public static String readFile(String filename) {
    String result = "";
    String fileToRead = "../../json/" + filename;
    try {
        Scanner scanner = new Scanner(new File(fileToRead));
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.next());
        }
        result = sb.toString();
    } catch(Exception e) {
        e.printStackTrace();
    }
    return result;
    }
    public Map<String, String> getParameters(HttpExchange httpExchange, String filter, String splitValue) {
        Map<String, String> parameters = new HashMap<>();
        InputStream inputStream = httpExchange.getRequestBody();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int read = 0;
        try{
            while ((read = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, read);
              }
        }
        catch(IOException ex){
            System.out.println("\n" + ex);
        }
        
        String[] keyValuePairs = byteArrayOutputStream.toString().split(splitValue);
        String pair = "";
        for(int i  = 0; i < keyValuePairs.length; i++){
            pair = keyValuePairs[i];
            pair = pair.replaceAll("\\s+|\"|\\}","");
            pair = pair.replaceAll("\\{","");
            /*pair = pair.replaceAll("{","");
            pair = pair.replaceAll("}","");*/
            System.out.println("Pair :: " + pair);
            keyValuePairs[i] = pair;
            
        }
        for (String keyValuePair : keyValuePairs) {
          String[] keyValue = keyValuePair.split(filter);
          System.out.println(keyValue.toString());
          if (keyValue.length != 2) {
            continue;
          }
          parameters.put(keyValue[0], keyValue[1]);
        }
        return parameters;
      }
      /*public Map<String, String> getLoadArrays(HttpExchange httpExchange) {
        Map<String, String> parameters = new HashMap<>();
        InputStream inputStream = httpExchange.getRequestBody();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int read = 0;
        try{
            while ((read = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, read);
              }
        }
        catch(IOException ex){
            System.out.println("\n" + ex);
        }
        
        String[] keyValuePairs = byteArrayOutputStream.toString().split(",");
        String pair = "";
        for(int i  = 0; i < keyValuePairs.length; i++){
            pair = keyValuePairs[i];
            pair = pair.replaceAll("\\s+|\"|\\}","");
            pair = pair.replaceAll("\\{","");
            /*pair = pair.replaceAll("{","");
            pair = pair.replaceAll("}","");
            System.out.println("Pair :: " + pair);
            keyValuePairs[i] = pair;
            
        }
        for (String keyValuePair : keyValuePairs) {
          String[] keyValue = keyValuePair.split(": [");
          if (keyValue.length != 2) {
            continue;
          }
          parameters.put(keyValue[0], keyValue[1]);
        }
        return parameters;
      }*/
      /*
		The readString method shows how to read a String from an InputStream.
	*/
	public String readString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader sr = new InputStreamReader(is);
		char[] buf = new char[1024];
		int len;
		while ((len = sr.read(buf)) > 0) {
			sb.append(buf, 0, len);
		}
		return sb.toString();
	}
}  

class Names {
    private ArrayList<String> data;
    public Names(){
        data = new ArrayList<>();
    }
    public void add(String name){
        data.add(name);
    }
    public ArrayList<String> get(){
        return data;
    }
     public String toString() { 
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(String name : data){
            sb.append(name + ", ");
        }
        sb.append("]");
        return sb.toString(); 
     } 
}
class Locations{
    private ArrayList<Location> data;
    public Locations(){
        data = new ArrayList<>();
    }
    public void add(Location location){
        data.add(location);
    }
    public ArrayList<Location> get(){
        return data;
    }
     public String toString() { 
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Location location : data){
            sb.append(location + ",\n");
        }
        sb.append("]");
        return sb.toString(); 
     } 
}
/*class Location {
    private String country;
    private String city;
    private double latitude;
    private double longitude;

    public Location(String country, String city, double latitude, double longitude ){
        setCountry(country);
        setCity(city);
        setLatitude(latitude);
        setLongitude(longitude);
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return country;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return city;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public double getLongitude(){
        return longitude;
    }
     public String toString() { 
        StringBuilder sb = new StringBuilder();
        sb.append("  {\n");
        sb.append("    country :" + country + ",\n");
        sb.append("    city :" + city + ",\n");
        sb.append("    latitude :" + latitude + ",\n");
        sb.append("    longitude :" + longitude + ",\n");
        sb.append("  }");
        return sb.toString(); 
     } 
}*/