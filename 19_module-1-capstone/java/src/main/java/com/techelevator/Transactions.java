package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Transactions {
	
	private Date dateTime;
	private String typeOfTransaction;
	private double initialMoney;
	private double moneyAfterTransaction;
	
	public Transactions(String logType, double initialMoney, double moneyAfterTransaction) throws IOException {
		dateTime = new Date();
		typeOfTransaction = logType;
		this.initialMoney = initialMoney;
		this.moneyAfterTransaction = moneyAfterTransaction;
		printToFile();
	}
	
	public void printToFile() throws IOException {
		
		FileWriter fileWriter = new FileWriter("Log.txt", true);
		PrintWriter writer = new PrintWriter(fileWriter);
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY hh:mm:ss aa");
		String logLine = dateFormat.format(dateTime) + " " + typeOfTransaction + " " + String.format("%.2f",initialMoney) + " " + String.format("%.2f",moneyAfterTransaction);
		writer.println(logLine);
		writer.close();
		
	}
	
}
