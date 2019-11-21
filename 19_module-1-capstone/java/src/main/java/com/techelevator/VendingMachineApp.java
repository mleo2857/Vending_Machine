package com.techelevator;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.techelevator.view.Menu;   // Bring in Menu class provided

public class VendingMachineApp {
	/*************************************************************************************************************************
	*  This is the application program to instantiate a Vending Machine as start it running
	*  
	*  DO NOT PUT ANY NEW CODE HERE!
	 * @throws IOException 
	***************************************************************************************************************************/
		
		public static void main(String[] args) throws IOException {
			Menu appMenu = new Menu(System.in, System.out);                // Instantiate a menu for Vending Machine to use
			VendingMachineCLI vendingCli = new VendingMachineCLI(appMenu); // Instantiate a Vending Machine CLI passing it the Menu object to use
			vendingCli.run();                                              // Tell the Vending MachineCLI to start running
		}
	}


