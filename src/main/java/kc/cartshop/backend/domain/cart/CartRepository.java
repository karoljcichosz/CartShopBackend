package kc.cartshop.backend.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c join c.items i where i.item.name=?1")
    List<Cart> getCartsByItemName(String name);

    Cart getCartByCustomerId(Long customerId);
}
