package kc.cartshop.backend.util;

import kc.cartshop.backend.domain.cart.Cart;
import kc.cartshop.data.output.CartOutput;

import java.util.stream.Collectors;

public class CartMapper {
    public static CartOutput map(Cart cart) {
        return CartOutput.builder()
                .customerId(cart.getCustomerId().toString())
                .price(cart.getPrice())
                .lastModifiedTime(cart.getLastModifiedTime())
                .items(cart.getItems()
                        .stream()
                        .map(CartItemMapper::map)
                        .collect(Collectors.toList())).
                        build();
    }
}
