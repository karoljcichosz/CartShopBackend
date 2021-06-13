package kc.cartshop.backend.util;

import kc.cartshop.backend.domain.cart.Cart;
import kc.cartshop.backend.domain.cart.CartItem.CartItem;
import kc.cartshop.backend.domain.item.ItemRepository;
import kc.cartshop.data.input.CartItemInput;
import kc.cartshop.data.output.CartItemOutput;

public class CartItemMapper {

    public static CartItemOutput map(CartItem cartItem) {
        return CartItemOutput.builder()
                .item(ItemMapper.map(cartItem.getItem()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static CartItem map(CartItemInput cartItemInput, Cart cart, ItemRepository itemRepository) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemInput.getQuantity());
        cartItem.setCart(cart);
        cartItem.setItem(itemRepository.getById(cartItemInput.getItemId()));
        return cartItem;
    }
}
