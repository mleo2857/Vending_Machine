package com.techelevator;

public class Product {
	
	private String name;
	private double price;
	private String type;
	private int quantity;
	
	public Product(String name, double price, String type) {
		this.name = name;
		this.price = price;
		this.type = type;
		this.quantity = 5;
	}
	
	public void reduceQuantity() {
		if (this.quantity > 0) {
			this.quantity--;
		}
		else {
			System.out.println("Sorry but this item is sold out..");
		}
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}

	public int getQuantity() {
		return quantity;
	}
	
	

}
