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
	void testCartOrdering() {
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
		int CART_QUANTITY = 5;
		Promotion promo = new Promotion("test1", PROMO_PRICE);
		promo.addItem(new Item(C.getSku(), C.getPrice()), 2); // Add C to promotion

		// Add to cart 5 C item
		try {
			cartService.addToCart(userCart, C, CART_QUANTITY);
		} catch (Exception e) {
			fail("CartService.addToCart could not add item to cart " + e.getMessage());
		}
		// apply promotion
		try {
			cartService.executePromotions(userCart, promo);
		} catch (Exception e) {
			fail("CartService.executePromotions could not apply promotion " + e.getMessage());
		}
		/* (CART_QUANTITY * regular price) is not equal to final cart price because of
		 * promotion. 
		 */
		Assert.assertTrue((CART_QUANTITY * C.getPrice()) != userCart.getCartItems().values().stream()
				.mapToDouble(i -> i.getCartPrice()).sum());
	}

	/**
	 * combination promotion i.e. C+D promotion 1C+2D in cart = 200Rs+2*200Rs = 600Rs. 
	 * 1C+1D in promotion of->1C+1D=300Rs 
	 * Then after applying promotion, total price = (1C+1D)+(1D)=300+200=500Rs
	 */
	@Test
	void testExecutePromotionsOnCombinedItems() {
		double PROMO_PRICE = 300.0;
		double D_REGULAR_PRICE = D.getPrice();
		Promotion combinedPromo = new Promotion("test1", PROMO_PRICE);
		combinedPromo.addItem(new Item(C.getSku(), C.getPrice()), 1); // Add C to promotion
		combinedPromo.addItem(new Item(D.getSku(), D.getPrice()), 1); // Add D to promotion

		// Add to cart 1 C item and 2 D items
		Cart userCart = new Cart();
		try {
			cartService.addToCart(userCart, C, 1);
			cartService.addToCart(userCart, D, 2);
		} catch (Exception e) {
			fail("CartService.addToCart could not add item to cart " + e.getMessage());
		}
		// apply promotion
		try {
			cartService.executePromotions(userCart, combinedPromo);
		} catch (Exception e) {
			fail("CartService.executePromotions could not apply promotion " + e.getMessage());
		}
		Assert.assertTrue(userCart.getCartItems().values().stream().mapToDouble(i -> i.getCartPrice())
				.sum() == (PROMO_PRICE + D_REGULAR_PRICE));
	}

	/**
	 * re-applies promotion on a cart item which already has a promotion applied.
	 */
	@Test
	void testReApplyPromotionOnSameItem() {
		// add 2D to cart
		Cart userCart = new Cart();
		try {
			cartService.addToCart(userCart, D, 2);
		} catch (Exception e) {
			fail("CartService.addToCart could not add item to cart " + e.getMessage());
		}
		// apply promotion
		Promotion promo = new Promotion("test2D", 350.0);
		promo.addItem(new Item(D.getSku(), D.getPrice()), 2);// Add D to promotion
		try {
			cartService.executePromotions(userCart, promo);
		} catch (Exception e) {
			fail("CartService.executePromotions could not apply promotion " + e.getMessage());
		}
		Assert.assertTrue(350.0 == userCart.getCartItems().get(D.getSku()).getCartPrice());

		// add C to cart
		try {
			cartService.addToCart(userCart, C, 1);
		} catch (Exception e) {
			fail("CartService.addToCart could not add item to cart " + e.getMessage());
		}
		// apply promotion for C+D combined
		Promotion promoCombined = new Promotion("test1C1D", 350.0);
		promoCombined.addItem(new Item(C.getSku(), C.getPrice()), 1);// Add C to promotion
		promoCombined.addItem(new Item(D.getSku(), D.getPrice()), 1);// Add D to promotion
		try {
			cartService.executePromotions(userCart, promoCombined);
		} catch (Exception e) {
			fail("CartService.executePromotions could not apply promotion " + e.getMessage());
		}
		Assert.assertTrue(200.0 == userCart.getCartItems().get(C.getSku()).getCartPrice());
	}

}
