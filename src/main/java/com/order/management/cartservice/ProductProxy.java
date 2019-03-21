package com.order.management.cartservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="product-service", url="localhost:8000")
public interface ProductProxy {
	@PutMapping("/update-product/{id}/{qty}")
	public void updateProduct(@PathVariable("id") String id, @PathVariable("qty") Integer qty);
}
