package com.promo.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.promo.model.Cart;
import com.promo.model.CartItem;
import com.promo.model.Item;
import com.promo.model.Promotion;

public class CartService implements ICartService {

	@Override
	public boolean addToCart(Cart cart, Item item, int quantity) throws Exception {
		CartItem cartItem;
		try {
			System.out.println("CartService.addToCart - " + item.getSku());
			item.setQuantity(quantity);
			cartItem = new CartItem(item);
		} catch (Exception e) {
			throw new Exception("Unable to add to cart. Please verify the Item");
		}
		if (cart.getCartItems().containsKey(item.getSku())) {
			cartItem = cart.getCartItems().get(item.getSku());
			cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
			cartItem.setCartPrice(cartItem.getQuantity() * item.getPrice());
		}
		cart.getCartItems().put(item.getSku(), cartItem);
		return SUCCESS;
	}

	@Override
	public Cart executePromotions(Cart cart, Promotion promotion) throws Exception {
		System.out.println("CartService.executePromotions - " + promotion.getPromoCode());
		Map<String, CartItem> cartItems = cart.getCartItems();

		List<String> skuList = promotion.getItems().stream().map(Item::getSku).collect(Collectors.toList());
		boolean cartContains = cartItems.keySet().containsAll(skuList);
		
		if (promotion.getItems().size() < 1 || !cartContains) {
			return cart;
		}
		if (!promotion.isCombinedPromotion()) {
			// ex: cart has 5'A each for 20Rs
			//     promo has 2'A for 30Rs
			//     total price = 5*20=100Rs. Promo price = 60+20=80Rs
			Item promoItem = promotion.getItems().get(0);
			CartItem cartItem = cartItems.get(promoItem.getSku());
			if (!promoApplied(cartItem)) {
				updateCartItem(promotion, cartItem, promoItem);
			}
		} else {
			// ex: cart has 1'C=25Rs and 1'D=30Rs
			//     promo has 1'C+1'D=45Rs
			//     total price=55Rs. promoprice - 1'C=0, 1'D=45Rs
			int size = promotion.getItems().size(); // 2
			int promoAppliedCartSize = checkPromoAppliedCartItems(promotion, cartItems);
			if (promoAppliedCartSize > 0) {
				return cart;
			}
			for (int i = 0; i < size; i++) {
				
				CartItem cartItem = cartItems.get(promotion.getItems().get(i).getSku());
				cartItem.setCartPrice(0); // C.price=0
				if (i == size - 1) { // i=1
					updateCartItem(promotion, cartItem, promotion.getItems().get(i));
				}
				cartItem.setPromoApplied(true);
			}
		}
		return cart;
	}

	private int checkPromoAppliedCartItems(Promotion promotion, Map<String, CartItem> cartItems) {
		List<CartItem> tempCartItems = new LinkedList<>();
		for (Item i: promotion.getItems()) {
			tempCartItems.add(cartItems.get(i.getSku()));
		}
		int promoAppliedCartSize = tempCartItems.stream().filter(CartItem::isPromoApplied).collect(Collectors.toList()).size();
		return promoAppliedCartSize;
	}

	private boolean promoApplied(CartItem cartItem) {
		return cartItem.isPromoApplied();
	}

	private void updateCartItem(Promotion promotion, CartItem cartItem, Item promoItem) {
		int promoQuantity = promoItem.getQuantity();//2
		int cartQuantity = cartItem.getQuantity();//5
		int applyTimes = promoQuantity == 1 ? 1 : cartQuantity / promoQuantity;// 5/2=2
		int remainingQuantity = cartQuantity - applyTimes * promoQuantity;// 5-(2*2)=1
		
		double regularPrice = cartItem.getPrice();// 20
		cartItem.setCartPrice((remainingQuantity * regularPrice) + (applyTimes * promotion.getPromoPrice()));
		cartItem.setPromoApplied(true);
	}

}
