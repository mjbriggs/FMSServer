package manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager{
    /** SQL connection object */
    private Connection connection;

    /** Default constructor */
    public ConnectionManager(){
        connection = null;
    }
    /** will connnect to database, allows all daos to share one connection */
    public Connection connect(){
        if(connection == null){
            try{
                String driver = "org.sqlite.JDBC";
                Class.forName(driver);
                connection = DriverManager.getConnection("jdbc:sqlite:../fmsDataBase/fms.db");
                //System.out.println("Connection opened");
              }
              catch(SQLException err){
                System.out.println("\n" + err);
              }
              catch(ClassNotFoundException err){
                System.out.println("\n" + err);
              }
        }
        return connection;
    }
    public Connection closeConnection() throws SQLException{
        if(connection != null){
            connection.close();
            connection = null;
            //System.out.println("Connection closed");
        }
        return connection;

    }

}