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
	
	//TODO: Functions for all other tables.

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		String dbClassName = "com.mysql.jdbc.Driver";
		String CONNECTION = "jdbc:mysql://127.0.0.1/mydb";
		
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
			
			
			//TODO: Create All Tables
			//CreateUserTable
			myTableSetup.createUserTable();
			
			
			System.out.println("Closing connection...");
			myconn.close();
			System.out.println("Success!");
		} catch (SQLException e) {
			// Can't get a connection
			System.err.println("Connection error occured!");
		}
		
		
		

		
		
		
		
		
	}
}
