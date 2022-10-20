package com.prog.entity;

public class Item 
{
	private String name;
	private int quantity;
	private double total;
	
	public Item(String name, int quantity, double total) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.total = total;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
