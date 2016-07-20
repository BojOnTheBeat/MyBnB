package actualApp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * @author 2943644
 *
 */
import java.util.ArrayList;
import java.util.Scanner;


@SuppressWarnings("unused")
public class CommandLine {
	
	// 'sqlMngr' is the object which interacts directly with MySQL
	private SQLController sqlMngr = null;
	// 'sc' is needed in order to scan the inputs provided by the user
	private Scanner sc = null;
	
	//Public functions - CommandLine State Functions
	
    /* Function used for initializing an instance of current
     * class
     */
	public boolean startSession() {
		boolean success = true;
		if (sc == null) {
			sc = new Scanner(System.in);
		}
		if (sqlMngr == null) {
			sqlMngr = new SQLController();
		}
		try {
			success = sqlMngr.connect(this.getCredentials());
		} catch (ClassNotFoundException e) {
			success = false;
			System.err.println("Establishing connection triggered an exception!");
			e.printStackTrace();
			sc = null;
			sqlMngr = null;
		}
		return success;
	}
	
    /* Function that acts as destructor of an instance of this class.
     * Performs some housekeeping setting instance's private field
     * to null
     */
	public void endSession() {
		if (sqlMngr != null)
			sqlMngr.disconnect();
		if (sc != null) {
			sc.close();
		}
		sqlMngr = null;
		sc = null;
	}
	
	/* Function that executes an infinite loop and activates the respective 
     * functionality according to user's choice. At each time it also outputs
     * the menu of core functionalities supported from our application.
     */
	public boolean execute() {
		if (sc != null && sqlMngr != null) {
			System.out.println("");
			System.out.println("***************************");
			System.out.println("******ACCESS GRANTED*******");
			System.out.println("***************************");
			System.out.println("");
			
			String input = "";
			int choice = -1;
			do {
				menu(); //Print Menu
				input = sc.nextLine();
				try {
					choice = Integer.parseInt(input);
					switch (choice) { //Activate the desired functionality
					case 1:
						this.createHostProfile();
						break;
//					case 2:
//						this.createRenterProfile();
//						break;
					case 3:
						this.signInAsHost();
						break;
//					case 4:
//						this.signInAsRenter();
//						break;
					default:
						break;
					}
				} catch (NumberFormatException e) {
					input = "-1";
				}
			} while (input.compareTo("0") != 0);
			
			return true;
		} else {
			System.out.println("");
			System.out.println("Connection could not been established! Bye!");
			System.out.println("");
			return false;
		}
	}

	
	//Private functions	
	
	// Called during the initialization of an instance of the current class
    // in order to retrieve from the user the credentials with which our program
    // is going to establish a connection with MySQL
	private String[] getCredentials() {
		String[] cred = new String[3];
		System.out.print("Username: ");
		cred[0] = sc.nextLine();
		System.out.print("Password: ");
		cred[1] = sc.nextLine();
		System.out.print("Database: ");
		cred[2] = sc.nextLine();
		return cred;
	}
	
	private static void menu(){
		System.out.println("**********MAIN MENU**********");
		System.out.println("1. Create a Host Profile");
		System.out.println("2. Create a Renter Profile");
		System.out.println("3. Sign in as Host");
		System.out.println("4. Sign in as Renter");
		System.out.println("0. Exit");
		
	}
	
	private static void host_menu(){
		System.out.println("**********HOST MENU**********");
		System.out.println("1. Delete My Host Profile");
		System.out.println("2. Add a Listing");
		System.out.println("3. Delete a Listing");
		System.out.println("4. View My Listings");
		System.out.println("5. View Bookings for Particular Listing");
		System.out.println("6. Cancel A Booking");
		System.out.println("7. Add available dates to a listing");
		System.out.println("8. Remove available dates for a listing");
		System.out.println("9. Comment and Rate a renter");
		System.out.println("0. Exit");
	}
	
	//Function to "3. sign in as a host"
	private void signInAsHost() {
		String sin = "";
		System.out.println("To sign in, enter your SIN (9 characters)");
		sin = sc.nextLine();
		Boolean signIn = sqlMngr.signInAsHost(sin); //Pass in SIN number
		if (signIn){
			String host_in = "";
			int host_choice = -1;
			do{
				host_menu();
				
				host_in = sc.nextLine();
				try {
					host_choice = Integer.parseInt(host_in);
					switch (host_choice) { //Activate the desired functionality
					case 1:
						this.deleteHostProfile(sin);
						host_in = "0";//force us back to main menu
						break;
					case 2:
						this.addListing(sin);
						break;
					case 3:
						this.deleteListing(sin);
						break;
					case 4:
						this.viewHostListings(sin);
						break;
					case 5:
						this.viewBookings();
						break;
					case 6:
						this.cancelBooking();
						break;
					case 7:
						this.addAvailableDates();
						//add prices for each date too
						break;
					case 8:
						this.removeAvailableDates();
						break;
					case 9:
						this.commentAndRateRenter();
						break;
					default:
						break;
					}
				} catch (NumberFormatException e) {
					host_in = "-1";
				}
			} while (host_in.compareTo("0") != 0);
		
	
			
		}else{
			System.out.println("Create A Profile first");
			
		}
		}

		// Function to create Host
	private void createHostProfile() {
		System.out.print("Enter your name: ");
		String name = sc.nextLine();
		System.out.print("Enter your SIN: ");
		String sin = sc.nextLine();
		System.out.print("Enter your address: ");
		String addr = sc.nextLine();
		System.out.print("Enter your date of birth in the following format YYYY-MM-DD: ");
		String dob = sc.nextLine();
		System.out.print("Enter your occupation: ");
		String occu = sc.nextLine();
		
		sqlMngr.createHost(name, sin, addr, dob, occu);
				
	}
	
		// Function to create Renter
	private void createRenterProfile() {
		System.out.print("Enter your name: ");
		String name = sc.nextLine();
		System.out.print("Enter your SIN: ");
		String sin = sc.nextLine();
		System.out.print("Enter your address: ");
		String addr = sc.nextLine();
		System.out.print("Enter your date of birth in the following format YYYY-MM-DD: ");
		String dob = sc.nextLine();
		System.out.print("Enter your occupation: ");
		String occu = sc.nextLine();
		System.out.print("Enter your credit card #: ");
		String cc = sc.nextLine();
		
		sqlMngr.createRenter(name, sin, addr, dob, occu, cc);
				
	}
	
	//Delete the host's profile...saving the sin he used to sign in
	private void deleteHostProfile(String sin){
		System.out.println("(Y/N) Are you sure you want to delete host profile with SIN:" + sin + "?");
		String choice = sc.nextLine();
		if(choice.compareToIgnoreCase("Y") == 0){
			sqlMngr.deleteHost(sin);
		}
	}
	
	//Delete the renter's profile...saving the sin he used to sign in
	private void deleteRenterProfile(String sin){
		System.out.println("(Y/N) Are you sure you want to delete host profile with SIN:" + sin + "?");
		String choice = sc.nextLine();
		if(choice.compareToIgnoreCase("Y") == 0){
			sqlMngr.deleteRenter(sin);
		}
	}
	
	
	private void listAmenities(){
		System.out.println("wifi");
		System.out.println("laundry");
		System.out.println("kitchen");
		System.out.println("air conditioning");
		System.out.println("wheelchair access");
		System.out.println("gym");
		System.out.println("heating");
		System.out.println("workspace");
		System.out.println("24-hour checkin");
		System.out.println("parking");
		System.out.println("pool");
		System.out.println("tv");
		System.out.println("smoke detector");
		
	}
	
	//add a listing..associated with the signed in sin
	private void addListing(String sin){
		System.out.print("Enter the listing type: ");
		String type = sc.nextLine();
		System.out.print("Enter the listing address: ");
		String laddr = sc.nextLine();
		System.out.print("Enter the postal code: ");
		String post_code = sc.nextLine();
		System.out.print("Enter the latitude(9 digits, 6 decimal places): ");
		String lat = sc.nextLine();
		System.out.print("Enter the longitude(9 digits, 6 decimal places): ");
		String lon = sc.nextLine();
		System.out.print("Enter the city: ");
		String city = sc.nextLine();
		System.out.print("Enter the country: ");
		String country = sc.nextLine();
		sqlMngr. createListing(sin, type, laddr, post_code, lat, lon, city, country); //add to Listing table
		
		
		//suggest price based on city...default is 50 but for cities in this list:
		// [Los Angeles, New York, Cancun, San Francisco, Paris, Boston, Dubai] suggest a base price of $300
		//For cities in [Madrid, Barcelona, Macau, Rome, Las Vegas] suggest a base price of $200
		// If not in either list, base price of $80...print suggestion later down
		//when selecting availability calendar
		
		//create a function add amenities that prompts for an amenity, and when 
		//the user selects an amenity, display expected revenue increase
		//when user is done, they should type 0, also, find the lid of the of the current listing(do a query)
		
		//System.out.print("Enter the city: ");
		//System.out.print("Enter the city: ");
		//System.out.print("Enter the city: ");
		//System.out.print("Enter the city: ");






		
		
	}
	
	private void deleteListing(String sin){
		
	}
	
	private void viewHostListings(String sin){
		
	}
	
	private void viewBookings(){
		
	}
	
	private void cancelBooking(){
		
	}
	
	private void addAvailableDates(){
		
	}
	
	private void removeAvailableDates(){
		
	}
	
	private void commentAndRateRenter(){
		
	}
	
	
	

}
