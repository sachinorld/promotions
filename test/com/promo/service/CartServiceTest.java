package com.promo.service;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.promo.model.Cart;
import com.promo.model.Item;
import com.promo.model.Promotion;
import com.promo.service.CartService;

/* Load cart with items
 * Load promotions map with promo codes
 * +ve
 * 1. read all items in cart map. 
 * 2. fetch promo codes for each item from active promo map
 * 3. 
 * 
 * T-size is not zero
 */
public class CartServiceTest {

	private static Item A;
	private static Item B;
	private static Item C;
	private static Item D;
	private static CartService cartService;

	@BeforeAll
	public static void before() {
		A = new Item("P_Trmmr_P100", 100.0);
		B = new Item("A_Chclte_A200", 200.0);
		C = new Item("C_ToyCar_C200", 200.0);
		D = new Item("D_ToyDog_D200", 200.0);
		cartService = new CartService();
	}

	@Test
	void testAddToCart() {
		Cart userCart = new Cart();
		try {
			cartService.addToCart(userCart, A, 1);
			Assert.assertEquals(1, userCart.getCartItems().size());
			cartService.addToCart(userCart, B, 1);
			Assert.assertEquals(2, userCart.getCartItems().size());
		} catch (Exception e) {
			fail("CartService.addToCart could not add item to cart " + e.getMessage());
		}
	}

	@Test
	void testCartOrder() {
		Cart userCart = new Cart();
		try {
			cartService.addToCart(userCart, A, 1);
			cartService.addToCart(userCart, B, 1);
			Assert.assertEquals(2, userCart.getCartItems().size());
			Item i = userCart.getCartItems().values().stream().findFirst().get();
			Assert.assertTrue("First item is always the item added first.", i.getSku().equals(A.getSku()));
		} catch (Exception e) {
			fail("CartService.addToCart could not add item to cart " + e.getMessage());
		}
	}

	@Test
	void testExecutePromotions() {
		Cart userCart = new Cart();
		double PROMO_PRICE = 300.0;
		Promotion promo = new Promotion("test1", PROMO_PRICE);
		promo.addItem(C, 2);

		// Add to cart 5 C item
		try {
			cartService.addToCart(userCart, C, 5);
		} catch (Exception e) {
			fail("CartService.addToCart could not add item to cart " + e.getMessage());
		}
		// apply promotion
		try {
			cartService.executePromotions(userCart, promo);
		} catch (Exception e) {
			fail("CartService.executePromotions could not apply promotion " + e.getMessage());
		}
		Assert.assertTrue(
				userCart.getCartItems().values().stream().mapToDouble(i -> i.getCartPrice()).sum() == PROMO_PRICE);
	}

}
