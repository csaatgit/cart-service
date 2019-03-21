package com.order.management.cartservice;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
@Repository
public interface CartDao extends CrudRepository<Cart, String>{
		
	public List<Cart> findByuserId(String userId);
	
	public Optional<Cart> findByProductId(String productId);
	
	@Modifying
	@Transactional
	@Query("update Cart set qty = :qty where productId = :product and userId = :user")
	public void updateCart( @Param("qty") int qty, @Param("user") String userId, @Param("product") String productId );
	@Transactional
	@Modifying
	@Query("delete from Cart where productId = :product and userId = :user")
	public void deleteFromCart(@Param("user") String userId, @Param("product") String productId );
	;
	@Query(value="FROM Cart where userId=:user and productId=:product")
	public Cart findCartByUserIdorProductId(@Param("user") String userId, @Param("product") String productId);
	
	@Query(value="FROM Cart where userId=:user")
	public List<Cart> findCart(@Param("user") String userId);
	
//	public void order(List products);
}