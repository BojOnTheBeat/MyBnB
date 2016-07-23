package actualApp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * @author 2943644
 *
 */
import java.util.ArrayList;
import java.util.List;
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
					case 2:
						this.createRenterProfile();
						break;
					case 3:
						this.signInAsHost();
						break;
					case 4:
						this.signInAsRenter();
						break;
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
		//System.out.print("Database: ");
		cred[2] = "mydb?autoReconnect=true&useSSL=false";
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
	
	private static void renter_menu(){
		System.out.println("**********RENTER MENU**********");
		System.out.println("1. Delete My Renter Profile");
		System.out.println("2. View Available Listings");
		System.out.println("3. Book Listing");
		System.out.println("4. View Booked Listing");
		System.out.println("5. Cancel a Booked Listing");
		System.out.println("6. Comment and Rate a Listing");
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
						this.viewBookings(sin);
						break;
					case 6:
						this.cancelBooking(sin);
						break;
					case 7:
						this.addAvailableDates(sin);
						//add prices for each date too
						break;
					case 8:
						this.removeAvailableDates(sin);
						break;
					case 9:
						this.commentAndRateRenter(sin);
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
	
	//Function to "4. sign in as a renter"
	private void signInAsRenter() {
		String sin = "";
		System.out.println("To sign in, enter your SIN (9 characters)");
		sin = sc.nextLine();
		Boolean signIn = sqlMngr.signInAsRenter(sin); //Pass in SIN number
		if (signIn){
			String renter_in = "";
			int renter_choice = -1;
			do{
				renter_menu();
				
				renter_in = sc.nextLine();
				try {
					renter_choice = Integer.parseInt(renter_in);
					switch (renter_choice) { //Activate the desired functionality
					case 1:
						this.deleteRenterProfile(sin);
						renter_in = "0";//force us back to main menu
						break;
					case 2:
						this.viewListings(sin);
						break;
					case 3:
						this.bookListing(sin);
						break;
					case 4:
						this.viewBookedListings(sin);
						break;
					case 5:
						this.cancelBookedListing(sin);
						break;
					case 6:
						this.commentAndRateListing(sin);
						break;
					default:
						break;
					}
				} catch (NumberFormatException e) {
					renter_in = "-1";
				}
			} while (renter_in.compareTo("0") != 0);
		
	
			
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
		System.out.println("(Y/N) Are you sure you want to delete renter profile with SIN:" + sin + "?");
		String choice = sc.nextLine();
		if(choice.compareToIgnoreCase("Y") == 0){
			sqlMngr.deleteRenter(sin);
		}
	}
	
	
	private void listAmenities(){
		System.out.println("1. wifi");
		System.out.println("2. laundry");
		System.out.println("3. kitchen");
		System.out.println("4. air conditioning");
		System.out.println("5. wheelchair access");
		System.out.println("6. gym");
		System.out.println("7. heating");
		System.out.println("8. workspace");
		System.out.println("9. 24-hour checkin");
		System.out.println("10. parking");
		System.out.println("11. pool");
		System.out.println("12. tv");
		System.out.println("13. smoke detector");
		
	}
	
	//add a listing..associated with the signed in sin
	private void addListing(String sin){
		
		System.out.print("Enter the listing type(apartment,room or full house): ");
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
		sqlMngr.createListing(sin, type, laddr, post_code, lat, lon, city, country); //add to Listing table
		this.suggestBasePrice(city, type); // Function that suggests a base price based on city and listing type
		this.addAmenities(laddr); //add as many amenities as user wants
		this.addAvailability(laddr); //add dates and prices for each date.
	}
	
	//Helper function to be used in addListing to add dates and prices.
	private void addAvailability(String laddr){
		String lid = sqlMngr.getLidFromAddr(laddr);
		//infinite loop so that the host can add as many dates 
		//as they want, and a corresponding price per date.
		// Add a date and a price for that date: example: 25-06-06,80, type 0 when you're done		
		//System.out.println("Add one date(YYYY-MM-DD) and price, comma separated. Type 0 when done. Example: 2016-10-10,80\n");
		String input = "";
		while(input.compareTo("0") != 0){
			System.out.println("Add one date(YYYY-MM-DD) and price, comma separated. Type 0 when done. Example: 2016-10-10,80\n");
			input = sc.nextLine();
			if(input.compareTo("0")!= 0){
				
				String[] input_vals = input.split(",");
				sqlMngr.addListingAvailability(lid, input_vals[0], input_vals[1]);//pass lid, date and price 
			}
			
		}
		
	}
	
	//Helper function to suggest a Base Price to users
	private void suggestBasePrice(String city, String type){
		
		int sug_price = 50;//default
		List<String>top_tier = new ArrayList<String>(); //top tier cities. Base price is $200 
		List<String>second_tier = new ArrayList<String>(); //second tier cities. Base price is $100
		
		top_tier.add("Los Angeles");
		top_tier.add("New York");
		top_tier.add("Cancun");
		top_tier.add("San Francisco");
		top_tier.add("Rome");
		top_tier.add("Paris");
		top_tier.add("Boston");
		top_tier.add("Dubai");
		
		second_tier.add("Toronto");
		second_tier.add("Barcelona");
		second_tier.add("Las Vegas");
		second_tier.add("Vancouver");
		second_tier.add("Chicago");
		
		
		if(top_tier.contains(city)){
			sug_price = 200;
		}
		if(second_tier.contains(city)){
			sug_price = 100;
		}
		
		if(type == "room"){
			sug_price+=10;
		}else if(type == "apartment"){
			sug_price+=40;
		}else{//full house
			sug_price +=80;
		}
		
		System.out.println("\n");
		System.out.println("***************************");
		System.out.println("***************************");
		System.out.println("***************************");
		System.out.println("Based On your city and listing type\n");
		System.out.println("We suggest a base price of: ");
		System.out.println("$" + sug_price);
		
	}
	
	//Helper for addListing. This is part of the host toolkit and adds amenities and shows revenue expected increases.
	private void addAmenities(String laddr){
		String lid;
		System.out.println("These are the amenities we currently support:\n");
		listAmenities();
		
		lid = sqlMngr.getLidFromAddr(laddr);//lid of the listing we're adding amenities to
		
		String input = "";
		int choice = -1;
		do {
			System.out.println("Select an amenity: ");
			input = sc.nextLine();
			try{
				choice = Integer.parseInt(input);
				switch (choice) {
				
				case 1:
					System.out.println("Adding wifi gets you an expected $40 revenue increase!");
					sqlMngr.addListingAmenity(lid, "1");
					break;
				case 2:
					System.out.println("Adding laundry facilities gets you an expected $5 revenue increase!");
					sqlMngr.addListingAmenity(lid, "2");
					break;
				case 3:
					System.out.println("Adding kitchen facilities gets you an expected $40 revenue increase!");
					sqlMngr.addListingAmenity(lid, "3");
					break;
				case 4:
					System.out.println("Adding air conditioning gets you an expected $20 revenue increase!");
					sqlMngr.addListingAmenity(lid, "4");
					break;
				case 5:
					System.out.println("Adding wheelchair access gets you an expected $10 increase!");
					sqlMngr.addListingAmenity(lid, "5");
					break;
				case 6:
					System.out.println("Adding a gym gets you an expected $30 increase!");
					sqlMngr.addListingAmenity(lid, "6");
					break;
				case 7:
					System.out.println("Adding heating gets you an expected $20 increase!");
					sqlMngr.addListingAmenity(lid, "7");
					break;
				case 8:
					System.out.println("Adding a workspace gets you an expected $10 increase!");
					sqlMngr.addListingAmenity(lid, "8");
					break;
				case 9:
					System.out.println("Adding 24-hour chekin gets you an expected $40 increase!");
					sqlMngr.addListingAmenity(lid, "9");
					break;
				case 10:
					System.out.println("Adding free parking gets you an expected $20 increase!");
					sqlMngr.addListingAmenity(lid, "10");
					break;
				case 11:
					System.out.println("Adding a pool gets you an expected $10 increase!");
					sqlMngr.addListingAmenity(lid, "11");
					break;
				case 12:
					System.out.println("Adding a tv gets you an expected $15 increase!");
					sqlMngr.addListingAmenity(lid, "12");
					break;
				case 13:
					System.out.println("Adding a smoke detector gets you an expected $5 increase!");
					sqlMngr.addListingAmenity(lid, "12");
					break;
				default:
					break;
				
				}
			}catch(NumberFormatException e){
				input = "-1";
			}
			
		}while(input.compareTo("0") != 0);
		
		
	}
	
	private void deleteListing(String sin){
		
	}
	
	private void viewHostListings(String sin){
		
	}
	
	private void viewBookings(String sin){
		
	}
	
	private void cancelBooking(String sin){
		
	}
	
	private void addAvailableDates(String sin){
		
	}
	
	private void removeAvailableDates(String sin){
		
	}
	
	private void commentAndRateRenter(String sin){
		
	}

	private void viewListings(String sin){
		
	}

	private void bookListing(String sin){
		System.out.println("Enter the listing id of the listing you want to book: ");
		String lid = sc.nextLine();
		String more = "Y";
		
		while (more == "Y") {
			System.out.println("Enter the date you want to book: ");
			String date = sc.nextLine();
			sqlMngr.bookListing(sin, lid, date);
			System.out.println("Do you want to book another date for this listing (Y/N)?: ");
			more = sc.nextLine();
		}
	}

	private void viewBookedListings(String sin){
		System.out.println("Here are you booked listings: ");
		
		sqlMngr.viewBookedListings(sin);
	}

	private void cancelBookedListing(String sin){
		System.out.println("Enter the listing id of the listing you want to cancel: ");
		String lid = sc.nextLine();
		System.out.println("Enter the date that you would like to cancel: ");
		String date = sc.nextLine();		
		
		sqlMngr.cancelBookedListing(sin, lid, date);
	}

	private void commentAndRateListing(String sin){
		System.out.println("Enter the listing id of the listing you want to rate/comment: ");
		String lid = sc.nextLine();
		System.out.println("Enter the rating you wish to give(1-5): ");
		String rating = sc.nextLine();
		System.out.println("Enter your comment: ");
		String comment = sc.nextLine();
		
		sqlMngr.commentAndRateListing(sin, lid, comment, rating);
	}

}
