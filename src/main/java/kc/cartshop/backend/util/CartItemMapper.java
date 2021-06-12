package kc.cartshop.backend.util;

import kc.cartshop.backend.domain.cart.CartItem.CartItem;
import kc.cartshop.backend.domain.item.Item;
import kc.cartshop.data.output.CartItemOutput;

public class CartItemMapper {
    public static CartItemOutput map(CartItem cartItem) {
        Item item = cartItem.getItem();
        return CartItemOutput.builder()
                .itemId(item.getId())
                .name(item.getName())
                .unitPrice(item.getPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
