/**
 * 
 */
package actualApp;

import java.security.interfaces.RSAKey;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author 2943644
 *
 */
@SuppressWarnings("unused")
public class SQLController {
	
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/";
    //Object that establishes and keeps the state of our application's
    //connection with the MySQL backend.
	private Connection conn = null;
    //Object which communicates with the SQL backend delivering to it the
    //desired query FROM our application and returning the results of this
    //execution the same way that are received FROM the SQL backend.
	private Statement st = null;
	
    // Initialize current instance of this class.
	public boolean connect(String[] cred) throws ClassNotFoundException {
		Class.forName(dbClassName);
		boolean success = true;
		String user = cred[0];
		String pass = cred[1];
		String connection = CONNECTION + cred[2];
		try {
			conn = DriverManager.getConnection(connection, user, pass);
			st = conn.createStatement();
		} catch (SQLException e) {
			success = false;
			System.err.println("Connection could not be established!");
			e.printStackTrace();
		}
		return success;
	}

    // Destroy the private objects/fields of current instance of this class.
    // Acts like a destructor.
	public void disconnect() {
		try {
			st.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Exception occured while disconnecting!");
			e.printStackTrace();
		} finally {
			st = null;
			conn = null;
		}
	}
	

	//Controls the signInAsHost functionality
	public Boolean signInAsHost(String sin){
		String hostCheck = "SELECT count(*) FROM Host WHERE sin = ?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(hostCheck);
			ps.setString(1, sin);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next()) {
				count = rs.getInt(1);
				
			}
			if (count > 0){
				System.out.println("SIN exists, thanks for signing in\n");
				return true;
				
			}
			else{
				System.out.println("Host with that SIN doesn't exist\n");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Sign in as host failed");
			return false;
		}
	}
	
	//Controls the signInAsRenter functionality
	public Boolean signInAsRenter(String sin){
		String hostCheck = "SELECT count(*) FROM Renter WHERE sin = ?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(hostCheck);
			ps.setString(1, sin);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next()) {
				count = rs.getInt(1);
				
			}
			if (count > 0){
				System.out.println("SIN exists, thanks for signing in\n");
				return true;
				
			}
			else{
				System.out.println("Renter with that SIN doesn't exist\n");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Sign in as renter failed");
			return false;
		}
	}
	
	//Function to just add a new tuple to the listing table
	public void createListing(String sin, String type, String laddr, String postal_code, String lat, String lon, String city, String country ){
		String query;
		
		query = "INSERT INTO Listing(host_sin, type, laddr, postal_code, lat, lon, city, country) VALUES ('"+
		sin + "', '" + type + "', '" + laddr + "', '" + postal_code + "', '" + lat + "', '" + lon + "', '" + city + "', '" + country + "');" ;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException e){
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
			
			
			
		}
		
	}

	private void createUser(String name, String sin, String addr, String dob, String occu) {
		try {
			String queryCheck = "SELECT * FROM User WHERE sin = ?";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1,  sin);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			if (resultSet.next()) {
				 count = resultSet.getInt(1);
			}
			// user exists if count > 0
			if (count == 0) {
				// add user if it does not exist
				String userQuery = "INSERT INTO User (sin, name, uaddr, dob, occupation) VALUES ('" +
				sin + "', '" + name + "', '" + addr + "', '" + dob + "', '" + occu + "');";
				Statement stmt = null;
		        try {
		            stmt = conn.createStatement();
		            stmt.executeUpdate(userQuery);
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
		        System.out.print("User created\n");
			} else {
				System.out.print("User already exists\n");
			}
		} catch (SQLException e) {
		System.err.println("Exception triggered during create user exectuion!");
		e.printStackTrace();
	}
}
	
	public void createHost(String name, String sin, String addr, String dob, String occu) {
		try {
			// creates user
			createUser(name, sin, addr, dob, occu);
			// creates host
			String queryCheck = "SELECT * FROM Host WHERE sin = ?";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1,  sin);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			if (resultSet.next()) {
				 count = resultSet.getInt(1);
			}
			// user exists if count > 0
			if (count == 0) {
				// add user if it does not exist
				String hostQuery = "INSERT INTO Host (sin) VALUES ('" + sin + "');";
				Statement stmt = null;
		        try {
		            stmt = conn.createStatement();
		            stmt.executeUpdate(hostQuery);
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
		        System.out.print("Host created\n");
			} else {
				System.out.print("Host already exists\n");
			}
		} catch (SQLException e) {
			System.err.println("Exception triggered during create Host execution!");
		}

	}
	
	public void createRenter(String name, String sin, String addr, String dob, String occu, String cc) {
		try {
			// creates user
			createUser(name, sin, addr, dob, occu);
			// creates renter
			String queryCheck = "SELECT * FROM Renter WHERE sin=?";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1,  sin);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			if (resultSet.next()) {
				 count = resultSet.getInt(1);
			}
			// user exists if count > 0
			if (count == 0) {
				// add user if it does not exist
				String renterQuery = "INSERT INTO Renter (sin, creditCard) VALUES ('" + sin + "', '" + cc + "');";
				Statement stmt = null;
		        try {
		            stmt = conn.createStatement();
		            stmt.executeUpdate(renterQuery);
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
		        System.out.print("Renter created\n");
			} else {
				System.out.print("Renter already exists\n");
			}
		} catch (SQLException e) {
			System.err.println("Exception triggered during create Host execution!");
		}

	}
	
	public void deleteHost(String sin){
		String sql = "DELETE FROM Host " +
                "WHERE sin = " + sin;
		Statement stmt = null;
		int count = 0;
		try {
			// checks if there is a renter
			String queryCheck = "SELECT * FROM Renter WHERE sin = ?";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1,  sin);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				 count = resultSet.getInt(1);
			}
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Host Deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error with deletion");
		}
		// if there is no renter with same sin delete the user as well
		if (count == 0 ) {
			deleteUser(sin);
		}
	    
	    
	}
	
	public void deleteRenter(String sin){
		String sql = "DELETE FROM Renter " +
                "WHERE sin = " + sin;
		Statement stmt = null;
		int count = 0;
		try {
			// checks if there is a renter
			String queryCheck = "SELECT * FROM Host WHERE sin = ?";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1,  sin);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				 count = resultSet.getInt(1);
			}
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Renter Deleted");
		} catch (SQLException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error with deletion");
		}
		// if there is no renter with same sin delete the user as well
		if (count == 0 ) {
			deleteUser(sin);
		}
	    
	    
	}	
	
	private void deleteUser(String sin){
		
		String sql = "DELETE FROM User " +
                "WHERE sin = " + sin;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("User Deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getLidFromAddr(String laddr){
		String sql = "SELECT lid FROM Listing WHERE Listing.laddr = '" + laddr +"'";
		Statement stmt = null;
		String ret = "";
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int lid = rs.getInt("lid");
				ret = Integer.toString(lid);
				
			}
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
			
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
		}
		return ret;
	}
	
	//Adds a new tuple to the ListingAmenity table 
	public void addListingAmenity(String lid, String aid){
		String add = "INSERT INTO ListingAmenities (lid, aid) VALUES ('" +
				lid + "', '" + aid + "');";
				Statement stmt = null;
		        try {
		            stmt = conn.createStatement();
		            stmt.executeUpdate(add);
		            System.out.println("Amenity has been added");
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
		        }
	}

	
	//adds a new tuple to the ListingAvailability table
	public void addListingAvailability(String lid, String ldate, String price){
		String add = "INSERT INTO ListingAvailability  (lid, ldate, price) VALUES ('" +
				lid + "', '" + ldate + "', '"+ price + "');";
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(add);
			System.out.println("Listing is now added at given date and price");
		}catch (SQLException e) {
			System.err.println("Connection error occured!");
		}
	}
	
	//delete a Listing by host
	public void deleteHostListing(String host_sin){
		String sql = "DELETE FROM Listing WHERE sin = '"+ host_sin + "' ;";
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Listind Deleted");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void bookListing(String sin, String lid, String date) {
		try {
		// checks to see if listing is available at given date
		String queryCheck = "SELECT * FROM ListingAvailability WHERE lid = ? AND ldate = ? AND isAvailable='1'";
		PreparedStatement ps = conn.prepareStatement(queryCheck);
		ps.setString(1,  lid);
		ps.setString(2, date);
		ResultSet resultSet = ps.executeQuery();
		int count = 0;
		if (resultSet.next()) {
			 count = resultSet.getInt(1);
		}

		if (count == 0) {
			System.out.println("No listing with lid and/or date given");
			return;
		}
		
		// checks to see if listing has previously booked and canceled
		String queryCheck2 = "SELECT * FROM Booking WHERE lid = ? AND bdate = ? AND isCancelled='1'";
		
		PreparedStatement ps2 = conn.prepareStatement(queryCheck2);
		ps2.setString(1,  lid);
		ps2.setString(2, date);
		ResultSet resultSet2 = ps2.executeQuery();
		int count2 = 0;
		if (resultSet2.next()) {
			 count2 = resultSet2.getInt(1);
		}
		if (count2 > 0) {
			String readd = "UPDATE Booking SET isCancelled='0'" + 
					"WHERE lid = '" + lid + "' AND bdate = '" + date + "';";
			Statement stmt = null;
	        stmt = conn.createStatement();
	        stmt.executeUpdate(readd);
	        System.out.println("Listing is now re-booked");
			return;
		}
		
		} catch (SQLException e) {
			System.err.println("Connection error occured!");
			
			e.printStackTrace();
			
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
		}
		
		String book = "INSERT INTO Booking (rsin, lid, bdate) VALUES ('" +
				sin + "', '" + lid + "', '" + date + "');";
		try {
		Statement stmt = null;
        	removeDate(lid, date); //set date to unavailable in ListingAvailability
            stmt = conn.createStatement();
            stmt.executeUpdate(book);
            System.out.println("Listing is now booked");
        } catch (SQLException e) {
           System.err.println("Connection error occured!");
           e.printStackTrace();
           
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
           
        }
	}
	
	private void removeDate(String lid, String date) {
		String remove = "UPDATE ListingAvailability SET isAvailable='0'" + 
				"WHERE lid = '" + lid + "' AND ldate = '" + date + "';";

		Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(remove);
            System.out.println("Listing is no longer available");
        } catch (SQLException e) {
           System.err.println("Connection error occured!");
        }
	}
	
	
	private void addDate(String lid, String date) {
		String remove = "UPDATE ListingAvailability SET isAvailable='1'" + 
				"WHERE lid = '" + lid + "' AND ldate = '" + date + "';";
		Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(remove);
            System.out.println("Listing is now available");
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
        }
	}

	public void cancelBookedListing(String sin, String lid, String date) {
		String cancel = "UPDATE Booking SET isCancelled='1' WHERE rsin='" +
				sin + "' AND lid='" + lid + "' AND bdate='" + date + "';";
		Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(cancel);
            System.out.println("This booked listing has been canceled");
            addDate(lid, date);
        } catch (SQLException e) {
           System.err.println("Connection error occured!");
        }
	}
	
	public void viewHostListings(String sin){
		String query = "SELECT * FROM Listing WHERE host_sin='" + sin + "';";
		try {
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t \t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during SELECT execution!");
			e.printStackTrace();
		}
	}
	
	//Make the specified listing unavailable on the specified date(i.e delete it from ListingAvail.
	public void makeDateUnavailable(String lid, String date){
		//First check if it's available at the given date.
		try{
			//Check to see if listing is available at given date.
			String queryCheck = "SELECT * FROM ListingAvailability WHERE lid = ? AND ldate = ? AND isAvailable='1'";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1, lid);
			ps.setString(2, date);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			if(resultSet.next()){
				count = resultSet.getInt(1);
			}
			if (count == 0){
				//listing has been booked, host can't change price
				System.out.println("Listing has been booked for that day. You can't change the price");
				return;
			}
			
			String del = "DELETE FROM ListingAvailability WHERE lid = '" + lid + "' AND ldate = '" + date + "';"; 
			Statement stmt = null;
			stmt = conn.createStatement();
			stmt.executeUpdate(del);
			System.out.println("The listing is no longer available on " + date);
		}catch (SQLException e){
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
		}
	}
	
	//modify the price for a particular date 
	public void changePriceForDate(String lid, String date, String new_price){
		try{
			//Check to see if listing is available at given date.
			String queryCheck = "SELECT * FROM ListingAvailability WHERE lid = ? AND ldate = ? AND isAvailable='1'";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1, lid);
			ps.setString(2, date);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			if(resultSet.next()){
				count = resultSet.getInt(1);
			}
			
			if (count == 0){
				//listing has been booked, host can't change price
				System.out.println("Listing has been booked for that day. You can't change the price");
				return;
			}
			String upd = "UPDATE ListingAvailability SET price ='" + new_price + "' WHERE ldate = '" + date + "' AND lid = '" + lid + "' ;";
			
			Statement stmt = null;
			stmt = conn.createStatement();
			stmt.executeUpdate(upd);
			System.out.println("The price for " + date + " has now been changed");
			
		}catch (SQLException e){
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
		}
	}
	
	public void viewBookingsByHost(String sin){
		String query = "SELECT host_sin,Listing.lid,type,laddr,city,country FROM Listing,Booking WHERE Listing.lid=Booking.lid AND host_sin='" + 
						sin + "' AND Booking.isCancelled='0';";
		
		try {
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t \t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during SELECT execution!");
			e.printStackTrace();
		}
	}

	public void viewBookedListings(String sin) {
		String query = "SELECT rsin, lid, bdate FROM Booking WHERE rsin='" + sin + "' AND isCancelled='0';";
		try {
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t \t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during SELECT execution!");
			e.printStackTrace();
		}
	}

	public void commentAndRateListing(String sin, String lid, String comment, String rating) {

		String queryCheck = "SELECT * FROM Booking WHERE rsin = ? AND lid = ? AND isCancelled='0'";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(queryCheck);
			ps.setString(1, sin);
			ps.setString(2, lid);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next()) {
				count = rs.getInt(1);
				
			}
			// count > 0 means renter has booked this lid
			if (count > 0) {
				String experience = "INSERT INTO ExperienceComment (rsin, lid, comment, rating) VALUES ('" +
						sin + "', '" + lid + "', '" + comment + "', '" + rating + "');";
				Statement stmt = null;
		        try {
		            stmt = conn.createStatement();
		            stmt.executeUpdate(experience);
		            System.out.println("Comment and Rating added");
		        } catch (SQLException e) {
		           System.err.println("Connection error occured!");
		        }
				
			} else { 
				System.out.println("You cannot comment on a listing you have not booked");
			}
		} catch (SQLException e) {
			System.err.println("Connection error occured!");
		}
	}

	public void commentAndRateRenter(String sin, String rsin, String comment, String rating) {
		String queryCheck = "SELECT rsin FROM Listing,Booking WHERE host_sin=? AND Listing.lid=Booking.lid AND rsin=? AND isCancelled='0'";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(queryCheck);
			ps.setString(1, sin);
			ps.setString(2, rsin);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next()) {
				count = rs.getInt(1);
				
			}
			// count > 0 means renter has booked this lid
			if (count > 0) {
				String renter = "INSERT INTO RenterComment (host_sin, rsin, comment, rating) VALUES ('" +
						sin + "', '" + rsin + "', '" + comment + "', '" + rating + ");";
				Statement stmt = null;
		        try {
		            stmt = conn.createStatement();
		            stmt.executeUpdate(renter);
		            System.out.println("Comment and Rating added");
		        } catch (SQLException e) {
		           System.err.println("Connection error occured!");
		        }
				
			} else { 
				System.out.println("You cannot comment on a renter that has not booked your listing");
			}
		} catch (SQLException e) {
			System.err.println("Connection error occured!");
		}
		
	}

	public void search(String lati, String longi, String post_code, String addr, String country, String city, String sort_by,
			String price_min, String price_max, String after_date, String before_date, ArrayList<String> amenities) {
		String query = "SELECT Listing.lid, type, laddr, postal_code, city, country, ldate, price FROM Listing, ListingAvailability";
		
		// for amenity filter
		for (String amenity: amenities) {
			query = query + ", ListingAmenities LA" + amenity;
		}
		
		query = query + " WHERE Listing.lid=ListingAvailability.lid";

		for (String amenity: amenities) {
			query = query + "=LA" + amenity + ".lid";
		}

		
		// location based
		if (lati.compareTo("-1") != 0) {
			
		} else if (post_code.compareTo("-1") != 0) {
			
		} else if (addr.compareTo("-1") != 0) {
			query = query + " AND laddr = '" + addr + "'";
		} else {
			query = query + " AND country = '" + country + "' AND city = '" + city + "'";
		}
		
		// price filter
		if (price_min.compareTo("-1") != 0) {
			query = query + " AND price >= '" + price_min + "'";
		}
		
		if (price_max.compareTo("-1") != 0) {
			query = query + " AND price <= '" + price_max + "'";
		}
		
		// date filter
		if (after_date.compareTo("-1") != 0) {
			query = query + " AND ldate >= '" + after_date + "'";
		}

		if (before_date.compareTo("-1") != 0) {
			query = query + " AND ldate <= '" + before_date + "'";
		}
		

		// Amenity filter
		if (!amenities.isEmpty()) {
			query = query + " AND (";
			for (String amenity: amenities) {
				query = query + "LA" + amenity + ".aid = '" + amenity + "' AND ";
			}
			// gets rid of last "' AND "
			query = query.substring(0, query.length()-5);
			query = query + ")";
		}
		
		// sort by
		if (sort_by.compareTo("1") == 0) {
			// price ascending
			query = query + " ORDER BY price";
			
		} else if (sort_by.compareTo("2") == 0) {
			// price descending
			query = query + "ORDER BY price DESC";
		} else {
			// distance
		}
		
		try {
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during search execution!");
			e.printStackTrace();
		}
	}

	public void viewListingDates(String lid) {
		String dates = "SELECT Listing.lid, type, laddr, postal_code, city, country, ldate, price FROM Listing, ListingAvailability " +
				"WHERE Listing.lid='" + lid + "'";

		try {
			ResultSet rs = st.executeQuery(dates);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during viewListingDates execution!");
			e.printStackTrace();
		}
		
	}

	public void viewListingAmenities(String lid) {
		String amenities = "SELECT Listing.lid, type, laddr, postal_code, city, country, a_type FROM Listing, ListingAmenities, Amenities " +
				"WHERE Listing.lid='" + lid + "'";


		try {
			ResultSet rs = st.executeQuery(amenities);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during viewListingAmenities execution!");
			e.printStackTrace();
		}
	}

	public void viewListingComments(String lid) {
		String amenities = "SELECT rsin, lid, comment, rating FROM ExperienceComment " +
				"WHERE lid='" + lid + "'";


		try {
			ResultSet rs = st.executeQuery(amenities);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during viewListingComments execution!");
			e.printStackTrace();
		}
		
	}

	public void viewRenterComments(String sin) {
		String amenities = "SELECT rsin, comment, rating FROM RenterComment " +
				"WHERE rsin='" + sin + "'";


		try {
			ResultSet rs = st.executeQuery(amenities);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			System.out.println("");
			for (int i = 0; i < colNum; i++) {
				System.out.print(rsmd.getColumnLabel(i+1) + "\t");
			}
			System.out.println("");
			while(rs.next()) {
				for (int i = 0; i < colNum; i++) {
					System.out.print(rs.getString(i+1) + "\t");
				}
				System.out.println("");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Exception triggered during viewRenterComments execution!");
			e.printStackTrace();
		}
	}
}
