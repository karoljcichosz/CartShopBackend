package kc.cartshop.backend.domain.cart;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getCartsByItem(String name) {
        return cartRepository.getCartsByItemName(name);
    }

    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
