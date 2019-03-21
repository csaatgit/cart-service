package com.order.management.cartservice;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CartController {
	@Autowired
	CartDao cartDao;
	@Autowired
	ProductProxy productProxy;
	@PostMapping("/add-to-cart/{id}/{qty}/{user}")
	public void addToCart(@PathVariable String id,@PathVariable Integer qty, @PathVariable String user){
		//check for product in stock
		Cart cart = cartDao.findCartByUserIdorProductId(user, id);
		if (cart != null) {
			cartDao.updateCart(cart.getQty()+qty, user, id);
		} else {
		 cartDao.save(new Cart(id, qty, user));
		}
	}
	@DeleteMapping("/remove-from-cart/{id}/{user}")
	public void removeFromCart(@PathVariable String user, @PathVariable String id){
		cartDao.deleteFromCart(user, id);
	}
	
	@GetMapping("/get-cart/{userId}")
	public List getCart(@PathVariable String userId){
		return cartDao.findByuserId(userId);
	}
	@PostMapping("/order/user")
	public void order(@RequestBody List<Cart> cart){
		for(Cart item : cart){
			productProxy.updateProduct(item.getProductId(), item.getQty());
		}
		
		//use feign to communicate to Payment Gateway to do the payment and respond back
//		cartDao.order(cart);
	}
}
