package com.promo.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

	private Map<String, CartItem> cartItems;
	
	public Cart() {
		cartItems = new LinkedHashMap<>();
	}
	
	public Map<String, CartItem> getCartItems() {
		return this.cartItems;
	}
	public double getTotalPrice() {
		return cartItems.values().stream().mapToDouble(cI -> cI.getCartPrice()).sum();
	}
}
