# promotions
Applying promotions/promo codes on the cart of the user.
The promotion can be for mutiple number of one item (ex: Two 100Rs Chocolate for 180Rs) or on a mixture of items (ex: one pen of 15Rs and one notepad of 30rs for 40Rs)

## Run
Test cases written in 'test' source folder will test the funtions with Junit tests.

## Feature information
There are 2 features in the project
### 1. AddToCart
cartService.addToCart(cart, item, quantity) - This will add 'item' to 'cart' 'quantity' number of times. Return updated cart object
cart - a new Cart object with no attributes to be passed
item - a new Item(sku_in_string, price_in_double). sku represents the item
ex: addToCart(sachinCart, mask, 5) will add 5 masks of a particular sku to sachin's cart

### 2. ExecutePromotions
cartService.executePromotions(cart, promotion) - Thus will apply promotion to the cart items. Return updated cart object
cart - a new Cart object with no attributes to be passed
promotion - new Promotion(promoCode_in_string, promoPrice_in_double)
            Later add the Items and quantity in the promotion upon which the promotion has to be applied.
ex: diwaliPromo = new Promotion("diwaliPromo", 1000.0)
    diwaliPromo.addItem(FireCracker_A1, 2);
executePromotions(sachinCart, diwaliPromo) - This will apply 'diwaliPromo' promotion on the cart containing a 2 or more firecracker items and reduce its price.
