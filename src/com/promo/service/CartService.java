package com.promo.service;

import com.promo.model.Cart;
import com.promo.model.CartItem;
import com.promo.model.Item;
import com.promo.model.Promotion;

public class CartService implements ICartService {

	@Override
	public boolean addToCart(Cart cart, Item item, int quantity) {
		return false;
	}

	@Override
	public Cart executePromotions(Cart cart, Promotion promotion) {
		return new Cart();
	}

	private boolean promoApplied(CartItem cartItem) {
		return false;
	}

	

}
