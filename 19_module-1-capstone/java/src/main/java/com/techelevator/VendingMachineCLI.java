package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String SALES_REPORT          		   = "Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT,
													    SALES_REPORT
													    };
	
	private static final String MENU_2_OPTION_FEED_MONEY = "Feed Money";
	private static final String MENU_2_OPTION_SELECT_PRODUCT      = "Select Product";
	private static final String MENU_2_OPTION_FINISH_TRANSACTION          = "Finish Transaction";
	private static final String[] MENU_2_OPTIONS = { MENU_2_OPTION_FEED_MONEY,
													MENU_2_OPTION_SELECT_PRODUCT,
													MENU_2_OPTION_FINISH_TRANSACTION
													    };
	
	private static final String TEN_DOLLARS = "$10.00";
	private static final String FIVE_DOLLARS = "$5.00";
	private static final String TWO_DOLLARS  = "$2.00";
	private static final String ONE_DOLLAR  = "$1.00";
	private static final String EXIT_FEED_MONEY  = "Exit";
	private static final String[] FEED_MONEY_OPTIONS = { TEN_DOLLARS,
														FIVE_DOLLARS,
														TWO_DOLLARS,
														ONE_DOLLAR,
														EXIT_FEED_MONEY
													    };
	
	
	private Menu vendingMenu;              // Menu object to be used by an instance of this class
	private static Menu vendingMenu2;
	private static Menu feedMoneyMenu;
	private static double moneyProvided = 0;
	
	public VendingMachineCLI(Menu menu) {  // Constructor - user will pas a menu for this class to use
		this.vendingMenu = menu;           // Make the Menu the user object passed, our Menu
		this.vendingMenu2 = menu;
		this.feedMoneyMenu = menu;
	}
	/**************************************************************************************************************************
	*  VendingMachineCLI main processing loop
	*  
	*  Display the main menu and process option chosen
	 * @throws FileNotFoundException 
	***************************************************************************************************************************/
	
	private static ArrayList<Slot> slots = new ArrayList<Slot>();
	
	public void run() throws IOException {
		File oldLogFile = new File("Log.txt");
		if (oldLogFile.exists()) {
			oldLogFile.delete();
		}
		File logFile = new File(System.getProperty("user.dir"), "Log.txt");
		logFile.createNewFile();
		File aFile = new File("vendingmachine.csv");
		Scanner aScanner = new Scanner(aFile);
		
		
		while (aScanner.hasNextLine()) {
			String aLine = aScanner.nextLine();
			String attributes[] = aLine.split("\\|");
			String location = attributes[0];
			String name = attributes[1];
			double price = Double.parseDouble(attributes[2]);
			String type = attributes[3];
			Slot newSlot = new Slot(location, name, price, type);
			slots.add(newSlot);
		}

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayItems();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseItems();          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
				
				case SALES_REPORT:
					showSalesReport();
					break;
			}	
		}
		return;                               // End method and return to caller
	}
/********************************************************************************************************
 * Methods used to perform processing
 ********************************************************************************************************/
	public static void displayItems() {      // static attribute used as method is not associated with specific object instance
		// Code to display items in Vending Machine
		String format = "%1$-20s%2$-20s%3$-20s%4$-20s\n";
		System.out.format(format, "Slot Location", "Product Name", "Price", "Quantity");
		System.out.println();
		for (Slot slot : slots) {
			System.out.format(format, slot.getLocation(),slot.getProduct().getName(),slot.getProduct().getPrice(),slot.getProduct().getQuantity());
		}
	}
	
	public static void purchaseItems() throws IOException {	 // static attribute used as method is not associated with specific object instance
		// Code to purchase items from Vending Machine
		boolean shouldProcess = true;
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
					System.out.print("Current Money Provided: $");
					System.out.printf("%.2f", moneyProvided);
					System.out.println();
					String choice = (String)vendingMenu2.getChoiceFromOptions(MENU_2_OPTIONS);  // Display menu and get choice
					switch(choice) {                  // Process based on user menu choice
					
					case MENU_2_OPTION_FEED_MONEY:
						feedMoney();           
						break;                    // Exit switch statement
				
					case MENU_2_OPTION_SELECT_PRODUCT:
						selectProduct();  
						break;                    // Exit switch statement
				
					case MENU_2_OPTION_FINISH_TRANSACTION:
						finishTransaction();    
						shouldProcess = false;    // Set variable to end loop
						break;                    // Exit switch statement
					}
					
				}
		
	}
	
	public static void endMethodProcessing() { // static attribute used as method is not associated with specific object instance
		// Any processing that needs to be done before method ends
	}
	
	public static void showSalesReport() throws IOException {
		String fileName = new SimpleDateFormat("MM-dd-YYYY hh:mm:ss aa 'SalesReport.txt'").format(new Date());
		File salesReportLogFile = new File(System.getProperty("user.dir"), fileName);
		salesReportLogFile.createNewFile();
		FileWriter fileWriter = new FileWriter(fileName, true);
		PrintWriter writer = new PrintWriter(fileWriter);
		double totalSales = 0;
		String format = "%1$-20s%2$-20s\n";
		for (Slot slot : slots) {
			String itemName = slot.getProduct().getName();
			int itemQuantity = slot.getProduct().getQuantity();
			writer.format(format, itemName, itemQuantity);
			totalSales += (5 - itemQuantity) * slot.getProduct().getPrice();
		}
		writer.println();
		writer.print("Total Sales: $");
		writer.printf("%.2f",totalSales);
		writer.close();
	}
	
	public static void feedMoney() throws IOException {
		boolean shouldProcess = true;
		while(shouldProcess) {                // Loop until user indicates they want to exit
			System.out.println("Current Money Provided: $" + moneyProvided);
			String choice = (String)feedMoneyMenu.getChoiceFromOptions(FEED_MONEY_OPTIONS);  // Display menu and get choice
			switch(choice) {                  // Process based on user menu choice
			
				case TEN_DOLLARS:
					moneyProvided += 10;           // invoke method to display items in Vending Machine
					new Transactions("FEED MONEY", 10.00, moneyProvided);
					break;                    // Exit switch statement
			
				case FIVE_DOLLARS:
					moneyProvided += 5;          // invoke method to purchase items from Vending Machine
					new Transactions("FEED MONEY", 5.00, moneyProvided);
					break;                    // Exit switch statement
					
				case TWO_DOLLARS:
					moneyProvided += 2;          // invoke method to purchase items from Vending Machine
					new Transactions("FEED MONEY", 2.00, moneyProvided);
					break;                    // Exit switch statement
			
				case ONE_DOLLAR:
					moneyProvided += 1;    // Invoke method to perform end of method processing
					new Transactions("FEED MONEY", 1.00, moneyProvided);
					break;                    // Exit switch statement
					
				case EXIT_FEED_MONEY:
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
			}
			
		}
	}
	
	public static void selectProduct() throws IOException {
		Scanner aScanner = new Scanner(System.in);
		displayItems();
		System.out.println();
		System.out.println("Please select a valid item number");
		String slotRequested = aScanner.nextLine();
		
		double price = 0.00;
		String names = "";
		String type = "";
		String response = "";
		for (Slot slot : slots) {
			if (slot.getLocation().equals(slotRequested)) {
				names = slot.getProduct().getName();
				price = slot.getProduct().getPrice();
				if (moneyProvided < price) {
					System.out.println("Not enough money..");
					return;
				}
				if (slot.getProduct().getQuantity() == 0) {
					System.out.println("Sorry but this item is sold out..");
					return;
				}
				slot.getProduct().reduceQuantity();
				type = slot.getProduct().getType();
			}
		}
		
		switch (type) {
		case "Chip": 
			response = "Crunch Crunch, Yum!";
			break;
		
		case "Candy": 
			response = "Munch Munch, Yum!";
			break;
			
		case "Drink": 
			response = "Glug Glug, Yum!";
			break;
			
		case "Gum": 
			response = "Chew Chew, Yum!";
			break;
		}
		
		if (names.equals("")) {
			System.out.println("product does not exist..");
			return;
		}
		System.out.print(names + " ");
		System.out.printf("%.2f", price);
		System.out.println();
		System.out.println(response);
		moneyProvided -= price;
		new Transactions(names + " " + slotRequested,moneyProvided + price ,moneyProvided);

	}
	
	
	public static void finishTransaction() throws IOException {
		int remainingMoney = (int)(moneyProvided * 100);
		int numberOfQuarters = remainingMoney / 25; 
		remainingMoney = remainingMoney % 25;
		int numberOfDimes = remainingMoney / 10;
		remainingMoney = remainingMoney % 10;
		int numberOfNickels = remainingMoney / 5;
		System.out.println("Your change back is: "+ numberOfQuarters + " Quarter(s), " + numberOfDimes + " Dime(s), and " + numberOfNickels + " Nickel(s)");
		new Transactions("GIVE CHANGE", moneyProvided, 0.00);
		moneyProvided = 0.00;
	}
	
	
}
