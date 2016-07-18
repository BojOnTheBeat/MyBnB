package createAndDropTables;
/** 
 * 
 */
import java.sql.*;
/**
 * @author 2943644
 *
 */
public class TableDrop {
 
 @SuppressWarnings("unused")
 private String dbClassName; //= "com.mysql.jdbc.Driver";
 @SuppressWarnings("unused")
 private String CONNECTION; //= "jdbc:mysql://127.0.0.1/mydb";
 private Connection conn;
 
 public TableDrop(String dbClass, String CONNECTION, Connection conn){
    super();
    this.dbClassName = dbClass;
    this.CONNECTION = CONNECTION;
    this.conn = conn;
 }
 
 //Function to Drop A User Table
 public void dropUserTable() throws SQLException {
    
    String DropUserString = 
        "DROP TABLE IF EXISTS User;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropUserString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
            
            
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
 //Function to Drop A Renter Table
 public void dropRenterTable() throws SQLException {
    
    String DropRenterString =    
        "DROP TABLE IF EXISTS Renter;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropRenterString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            e.printStackTrace(System.err);
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
  //Function to Drop A Host Table
 public void dropHostTable() throws SQLException {
    
    String DropHostString =    
        "DROP TABLE IF EXISTS Host;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropHostString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            e.printStackTrace(System.err);
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
  //Function to Drop A Listing Table
 public void dropListingTable() throws SQLException {
    
    String DropListingString =    
        "DROP TABLE IF EXISTS Listing;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropListingString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            e.printStackTrace(System.err);
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
  //Function to Drop A Amenities Table
 public void dropAmenitiesTable() throws SQLException {
    
    String DropAmenitiesString =    
        "DROP TABLE IF EXISTS Amenities;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropAmenitiesString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            e.printStackTrace(System.err);
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
  //Function to Drop A ListingAmenities Table
 public void dropListingAmenitiesTable() throws SQLException {
    
    String DropListingAmenitiesString =    
        "DROP TABLE IF EXISTS ListingAmenities;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropListingAmenitiesString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            e.printStackTrace(System.err);
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
  //Function to Drop A ListingAvailability Table
 public void dropListingAvailabilityTable() throws SQLException {
    
    String DropListingAvailabilityString =    
        "DROP TABLES IF EXISTS ListingAvailability;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropListingAvailabilityString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            e.printStackTrace(System.err);
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
  //Function to Drop A Booking Table
 public void dropBookingTable() throws SQLException {
    
    String DropBookingString =    
        "DROP TABLE IF EXISTS Booking;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropBookingString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            e.printStackTrace(System.err);
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
  //Function to Drop A ExperienceComment Table
 public void dropExperienceCommentTable() throws SQLException {
    
    String DropExperienceCommentString =    
        "DROP TABLE IF EXISTS ExperienceComment;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropExperienceCommentString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
  //Function to Drop A RenterComment Table
 public void dropRenterCommentTable() throws SQLException {
    
    String DropRenterCommentString =    
        "DROP TABLE IF EXISTS RenterComment;";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(DropRenterCommentString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
            
            //**EXTRA ERROR INFO FOR DEBUGGING**
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                ((SQLException)e).getSQLState());

            System.err.println("Error Code: " +
                ((SQLException)e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while(t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 //Functions for all other tables.

 /**
    * @param args
    */
 public static void main(String[] args) throws ClassNotFoundException {
    String dbClassName = "com.mysql.jdbc.Driver";
    String CONNECTION = "jdbc:mysql://127.0.0.1/mydb?autoReconnect=true&useSSL=false";
    
    //Register JDBC driver
    Class.forName(dbClassName);
    
    //Database credentials
    final String USER = "root";
    final String PASS = ""; //Insert Password Here
    System.out.println("Connecting to database...");
    
    //Establish connection
    try {
     Connection myconn = DriverManager.getConnection(CONNECTION,USER,PASS);
     System.out.println("Successfully connected to MySQL!");
     
     
     TableDrop myTableDrop = new TableDrop(dbClassName, CONNECTION, myconn);
     
     
     //Drop All Tables and triggers...in right order.
     myTableDrop.dropRenterCommentTable();
     myTableDrop.dropExperienceCommentTable();
     myTableDrop.dropBookingTable();
     myTableDrop.dropListingAvailabilityTable();
     myTableDrop.dropListingAmenitiesTable();
     myTableDrop.dropAmenitiesTable();
     myTableDrop.dropListingTable();
     myTableDrop.dropHostTable();
     myTableDrop.dropRenterTable();
     myTableDrop.dropUserTable();
     
     
     System.out.println("Closing connection...");
     myconn.close();
     System.out.println("Success!");
    } catch (SQLException e) {
    	// Can't get a connection
    	System.err.println("Connection error occured!");
    	
    	//**EXTRA ERROR INFO FOR DEBUGGING**
    	e.printStackTrace(System.err);
        System.err.println("SQLState: " +
            ((SQLException)e).getSQLState());

        System.err.println("Error Code: " +
            ((SQLException)e).getErrorCode());

        System.err.println("Message: " + e.getMessage());

        Throwable t = e.getCause();
        while(t != null) {
            System.out.println("Cause: " + t);
            t = t.getCause();
        }

     
    }
    
    
    

    
    
    
    
    
 }
}
