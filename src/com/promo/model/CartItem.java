package com.promo.model;

public class CartItem extends Item {

	private double cartPrice;
	private boolean isPromoApplied;
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartItem(Item item) {
		this.sku = item.sku;
		this.quantity = item.quantity;
		this.price = item.getPrice();
		this.cartPrice = item.quantity * item.getPrice();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}
	public double getCartPrice() {
		return cartPrice;
	}
	public void setCartPrice(double cartPrice) {
		this.cartPrice = cartPrice;
	}
	public boolean isPromoApplied() {
		return isPromoApplied;
	}
	public void setPromoApplied(boolean isPromoApplied) {
		this.isPromoApplied = isPromoApplied;
	}
}
