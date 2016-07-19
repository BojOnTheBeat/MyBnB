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
			if(rs.next()) {
				System.out.println("SIN exists, thanks for signing in");
				return true;
			}
			else{
				System.out.println("SIN not found, Host doesn't exist");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Sign in as host failed");
			return false;
		}
		
		
	}

	

}
