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
    //desired query from our application and returning the results of this
    //execution the same way that are received from the SQL backend.
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
		String hostCheck = "SELECT count(*) from Host WHERE sin = ?";
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

	private void createUser(String name, String sin, String addr, String dob, String occu) {
		try {
			String queryCheck = "Select * from User where sin = ?";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1,  sin);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			if (resultSet.next()) {
				 count = resultSet.getInt(1);
			}
			String query;
			// user exists if count > 0
			if (count == 0) {
				// add user if it does not exist
				query = "INSERT INTO User (sin, name, uaddr, dob, occupation) VALUES ('" +
				sin + "', '" + name + "', '" + addr + "', '" + dob + "', '" + occu + "');";
				Statement stmt = null;
		        try {
		            stmt = conn.createStatement();
		            stmt.executeUpdate(query);
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
		        System.out.print("User added\n");
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
			String queryCheck = "Select * from Host where sin = ?";
			PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1,  sin);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			if (resultSet.next()) {
				 count = resultSet.getInt(1);
			}
			String query;
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
	

	

}
