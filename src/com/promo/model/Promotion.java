package com.promo.model;

import java.util.ArrayList;
import java.util.List;

public class Promotion {

	private final String promoCode;
	private final List<Item> items = new ArrayList<>();
	private final double promoPrice;
	
	public String getPromoCode() {
		return promoCode;
	}
	/**
	 * supply the item and quantity of items in the promotion.
	 * @param item
	 * @param quantity
	 */
	public void addItem(Item item, int quantity) {
		item.setQuantity(quantity);
		this.items.add(item);
	}

	public List<Item> getItems() {
		return items;
	}

	public double getPromoPrice() {
		return promoPrice;
	}

	public boolean isCombinedPromotion() {
		return getItems().size() > 1;
	}

	/**
	 * supply promotion code and price for this promotion.
	 * @param promoCode
	 * @param promoPrice
	 */
	public Promotion(String promoCode, double promoPrice) {
		super();
		this.promoCode = promoCode;
		this.promoPrice = promoPrice;
	}
	
}
