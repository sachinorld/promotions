package com.promo.service;

import com.promo.model.Cart;
import com.promo.model.Item;
import com.promo.model.Promotion;

public interface ICartService extends BaseService {

	/**
	 * Add items to cart. item becomes cartItem
	 * 
	 * @param cart
	 * @param item
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	public boolean addToCart(Cart cart, Item item, int quantity) throws Exception;
	/**
	 * apply promotion on the cart. 
	 * reduces the overall cart price based on promotion
	 * 
	 * @param cart
	 * @param promotion
	 * @return
	 * @throws Exception
	 */
	public Cart executePromotions(Cart cart, Promotion promotion) throws Exception;
}
