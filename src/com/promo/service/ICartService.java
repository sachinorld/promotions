package com.promo.service;

import com.promo.model.Cart;
import com.promo.model.Item;
import com.promo.model.Promotion;

public interface ICartService extends BaseService {

	public boolean addToCart(Cart cart, Item item, int quantity) throws Exception;
	public Cart executePromotions(Cart cart, Promotion promotion) throws Exception;
}
