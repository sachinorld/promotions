package com.promo.model;

public class Item {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}
	protected String sku;
	protected int quantity = 1;
	protected double price;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Item(String sku, double p) {
		super();
		this.sku = sku;
		this.price = p;
	}
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Item(String s, int q, double p) {
		super();
		this.sku = s;
		this.quantity = q;
		this.price = p;
	}
	
	
}
