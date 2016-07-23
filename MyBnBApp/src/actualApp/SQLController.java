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
			System.out.println("User Deleted");
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
			System.out.println("User Deleted");
		}
	    
	    
	}	
	
	private void deleteUser(String sin){
		
		String sql = "DELETE FROM User " +
                "WHERE sin = " + sin;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
		}catch (SQLException e) {
			System.err.println("Connection error occured!");
		}
	}

	public void bookListing(String sin, String lid, String date) {
		try {
		String queryCheck = "SELECT * FROM ListingAvailability WHERE lid = ? AND ldate = ?";
		PreparedStatement ps = conn.prepareStatement(queryCheck);
		ps.setString(1,  sin);
		ps.setString(2, date);
		ResultSet resultSet = ps.executeQuery();
		int count = 0;
		if (resultSet.next()) {
			 count = resultSet.getInt(1);
		}
		if (count <0) {
			System.out.println("No listing with lid and/or date given");
			return;
		}
		} catch (SQLException e) {
			System.err.println("Connection error occured!");
		}
		
		String book = "INSERT INTO Booking (sin, lid, bdate) VALUES ('" +
				sin + "', '" + lid + "', '" + date + "');";
		try {
		Statement stmt = null;
        	removeDate(lid, date);
            stmt = conn.createStatement();
            stmt.executeUpdate(book);
        } catch (SQLException e) {
           System.err.println("Connection error occured!");
        }
	}
	
	private void removeDate(String lid, String date) {
		String remove = "UPDATE ListingAvailability SET isBooked='0'" + 
				"WHERE lid = " + lid + "AND date = " + date;
		Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(remove);
        } catch (SQLException e) {
           System.err.println("Connection error occured!");
        }
	}

	public void cancelBookedListing(String sin, String lid, String date) {
		String cancel = "UPDATE Booking SET isCancelled='1' WHERE sin='" +
				sin + "' AND lid='" + lid + "' AND date='" + date + "';";
		Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(cancel);
        } catch (SQLException e) {
           System.err.println("Connection error occured!");
        }
	}

	public void viewBookedListings(String sin) {
		String query = "SELECT * FROM Booking WHERE sin='" + sin + "';";
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
			System.err.println("Exception triggered during SELECT execution!");
			e.printStackTrace();
		}
	}
}
