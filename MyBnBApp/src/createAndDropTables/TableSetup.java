package createAndDropTables;
/** 
 * 
 */
import java.sql.*;
/**
 * @author 2943644
 *
 */
public class TableSetup {
 
 @SuppressWarnings("unused")
 private String dbClassName; //= "com.mysql.jdbc.Driver";
 @SuppressWarnings("unused")
 private String CONNECTION; //= "jdbc:mysql://127.0.0.1/mydb";
 private Connection conn;
 
 public TableSetup(String dbClass, String CONNECTION, Connection conn){
    super();
    this.dbClassName = dbClass;
    this.CONNECTION = CONNECTION;
    this.conn = conn;
 }
 
 //Function to Create A User Table
 public void createUserTable() throws SQLException {
    
    String CreateUserString = 
        "create table User" + "(sin int(9) NOT NULL, "+
        "name varchar(32) NOT NULL, "+
        "uaddr varchar(50) NOT NULL, "+
        "dob date NOT NULL CHECK(YEAR(dob) <= 1998), "+
        "occupation varchar(32), "+
        "PRIMARY KEY (sin), "+
        "UNIQUE (uaddr)); ";
    
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateUserString);
         } catch (SQLException e) {
            System.err.println("Connection error occured!");
         } finally {
             if (stmt != null) { 
                stmt.close(); 
                }
         }
            
 }
 
 //Create an Update Trigger and an insert trigger to enforce the dob checks in the User table.
 
 public void createUserInsTrigger() throws SQLException {
	 
	 String userInsTrig = 
			 "CREATE TRIGGER UserIns BEFORE INSERT ON User "+
			 "FOR EACH ROW BEGIN " + "DECLARE msg varchar(255);" +"IF YEAR(NEW.dob) > 1998 THEN "+
			 "   SET msg = 'Constraints violated!';" + " SIGNAL sqlstate '45000'    set message_text = msg;"+
			 "END IF; END;";
	 
	 Statement stmt = null;
     try {
         stmt = conn.createStatement();
         stmt.execute(userInsTrig);
     } catch (SQLException e) {
        System.err.println("Connection error occured!");
        
        //Extra debug info
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
 
 public void createUserUpdTrigger() throws SQLException {
	 
	 String userUpdTrig = 
			 "CREATE TRIGGER UserUpd BEFORE UPDATE ON User "+
			 "FOR EACH ROW BEGIN " + "DECLARE msg varchar(255);" +"IF YEAR(NEW.dob) > 1998 THEN "+
			 "   SET msg = 'Constraints violated!';" + " SIGNAL sqlstate '45001'    set message_text = msg;"+
			 "END IF; END;";
	 
	 
	 Statement stmt = null;
     try {
         stmt = conn.createStatement();
         stmt.execute(userUpdTrig);
     } catch (SQLException e) {
        System.err.println("Connection error occured!");
        
        //Extra Debug info
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
 
 //Function to Create A Renter Table
 public void createRenterTable() throws SQLException {
    
    String CreateRenterString =    
        "create table Renter" + "(sin int(9) NOT NULL, " +
        "creditCard int(16) NOT NULL UNIQUE," +
        "FOREIGN KEY (sin) REFERENCES User(sin) ON UPDATE CASCADE ON DELETE CASCADE);";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateRenterString);
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
 
  //Function to Create A Host Table
 public void createHostTable() throws SQLException {
    
    String CreateHostString =    
        "create table Host" + "(sin int(9) NOT NULL, " +
        "lid int NOT NULL REFERENCES Listing(lid)," +
        "FOREIGN KEY (sin) REFERENCES User(sin) ON UPDATE CASCADE ON DELETE CASCADE);";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateHostString);
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
 
  //Function to Create A Listing Table
 public void createListingTable() throws SQLException {
    
    String CreateListingString =    
        "create table Listing" + " (host_sin int(9) NOT NULL REFERENCES User (sin)," +
        "lid int NOT NULL AUTO_INCREMENT," +
        "type varchar(32) NOT NULL," +
        "laddr varchar(50) NOT NULL," +
        "postal_code varchar(9) NOT NULL," +
        "city varchar(20) NOT NULL," +
        "country varchar(20) NOT NULL," +
        "UNIQUE (laddr)," +
        "PRIMARY KEY (lid));";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateListingString);
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
 
  //Function to Create A Amenities Table
 public void createAmenitiesTable() throws SQLException {
    
    String CreateAmenitiesString =    
        "create table Amenities" + "(aid int NOT NULL AUTO_INCREMENT," +
        "a_type varchar(50) NOT NULL," +
        "PRIMARY KEY (aid));";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateAmenitiesString);
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
 
  //Function to Create A ListingAmenities Table
 public void createListingAmenitiesTable() throws SQLException {
    
    String CreateListingAmenitiesString =    
        "create table ListingAmenities" + "(lid int NOT NULL REFERENCES Listing(lid) ON DELETE CASCADE," +
        "aid int(50) NOT NULL REFERENCES Amenities(aid)," +
        "PRIMARY KEY (lid, aid));";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateListingAmenitiesString);
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
 
  //Function to Create A ListingAvailability Table
 public void createListingAvailabilityTable() throws SQLException {
    
    String CreateListingAvailabilityString =    
        "create table ListingAvailability" + "(lid int NOT NULL REFERENCES Listing(lid) ON DELETE CASCADE," +
        "ldate date NOT NULL," +
        "price int NOT NULL," +
        "PRIMARY KEY(lid, ldate));";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateListingAvailabilityString);
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
 
  //Function to Create A Booking Table
 public void createBookingTable() throws SQLException {
    
    String CreateBookingString =    
        "create table Booking" + "(rsin int(9) NOT NULL REFERENCES Renter(sin)," +
        "lid int NOT NULL REFERENCES Listing(lid)," +
        "startDate date NOT NULL REFERENCES ListingAvailability(ldate)," +
        "endDate date NOT NULL REFERENCES ListingAvailability(ldate)," +
        "isCancelled boolean DEFAULT 0," +
        "PRIMARY KEY (rsin, startDate, endDate));";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateBookingString);
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
 
  //Function to Create A ExperienceComment Table
 public void createExperienceCommentTable() throws SQLException {
    
    String CreateExperienceCommentString =    
        "create table ExperienceComment" + "(rsin int(9) NOT NULL REFERENCES Renter(sin)," +
        "lid int NOT NULL REFERENCES Listing(lid)," +
        "comment varchar(256) NOT NULL," +
        "rating int CHECK(rating >= 1 AND rating <= 5)," +
        "PRIMARY KEY (rsin, lid));";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateExperienceCommentString);
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
 
 //Insert and update triggers to enforce checks in ExperienceComment table
 
 public void createExpCommInsTrigger() throws SQLException {
	 
	 String expCommInsTrig = 
			 "CREATE TRIGGER expCommIns BEFORE INSERT ON ExperienceComment "+
			 "FOR EACH ROW BEGIN " + "DECLARE msg varchar(255);" +"IF NEW.rating < 1 OR NEW.rating > 5 THEN "+
			 "   SET msg = 'Constraints violated!';" + " SIGNAL sqlstate '45000'    set message_text = msg;"+
			 "END IF; END ;";
	 
	 Statement stmt = null;
     try {
         stmt = conn.createStatement();
         stmt.execute(expCommInsTrig);
     } catch (SQLException e) {
        System.err.println("Connection error occured!");
        
        //Extra debug info
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
 
 public void createExpCommUpdTrigger() throws SQLException {
	 
	 String expCommUpdTrig = 
			 "CREATE TRIGGER expCommUpd BEFORE UPDATE ON ExperienceComment "+
			 "FOR EACH ROW BEGIN " + "DECLARE msg varchar(255);" +"IF NEW.rating < 1 OR NEW.rating > 5 THEN "+
			 "   SET msg = 'Constraints violated!';" + " SIGNAL sqlstate '45000'    set message_text = msg;"+
			 "END IF; END;";
	 
	 
	 Statement stmt = null;
     try {
         stmt = conn.createStatement();
         stmt.execute(expCommUpdTrig);
     } catch (SQLException e) {
        System.err.println("Connection error occured!");
        
        //Extra debug info
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
 
  //Function to Create A RenterComment Table
 public void createRenterCommentTable() throws SQLException {
    
    String CreateRenterCommentString =    
        "create table RenterComment" + "(host_sin int(9) NOT NULL REFERENCES Host(sin)," +
        "rsin int(9) NOT NULL REFERENCES Renter(sin)," +
        "comment varchar(256) NOT NULL," +
        "lid int NOT NULL REFERENCES Listing(lid)," +
        "PRIMARY KEY (host_sin, lid));";
    Statement stmt = null;
         try {
             stmt = conn.createStatement();
             stmt.executeUpdate(CreateRenterCommentString);
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
 //TODO: Functions for all other tables.

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
     
     
     TableSetup myTableSetup = new TableSetup(dbClassName, CONNECTION, myconn);
     
     
     //TODO: Create All Tables and triggers...in right order.
     //CreateUserTable
     myTableSetup.createUserTable();
     myTableSetup.createUserInsTrigger();
     myTableSetup.createUserUpdTrigger();
     myTableSetup.createRenterTable();
     myTableSetup.createHostTable();
     myTableSetup.createListingTable();
     myTableSetup.createAmenitiesTable();
     myTableSetup.createListingAmenitiesTable();
     myTableSetup.createListingAvailabilityTable();
     myTableSetup.createBookingTable();
     myTableSetup.createExperienceCommentTable();
     myTableSetup.createExpCommInsTrigger();
     myTableSetup.createExpCommUpdTrigger();
     myTableSetup.createRenterCommentTable();
     
     
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
