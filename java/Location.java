package GSON;

public class Location {
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
}