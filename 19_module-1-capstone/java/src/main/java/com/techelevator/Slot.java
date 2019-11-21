package com.techelevator;

import java.util.ArrayList;

public class Slot {
	

	private String location;
	private Product product;
	
	public Slot(String location, String name, double price, String type) {
		this.location = location;
		this.addProduct(name, price,type);
	}
	
	
	public void addProduct(String name, double price, String type) {
		Product newProduct = new Product(name, price, type);
		this.product = newProduct;
	}


	public String getLocation() {
		return location;
	}


	public Product getProduct() {
		return product;
	}
	
	
	
}
