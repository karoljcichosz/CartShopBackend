package kc.cartshop.backend.util;

import kc.cartshop.backend.domain.cart.Cart;
import kc.cartshop.backend.domain.item.ItemRepository;
import kc.cartshop.data.input.CartInput;
import kc.cartshop.data.output.CartOutput;

import java.time.ZonedDateTime;
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

    public static Cart map(CartInput cartInput, Long id, ItemRepository itemRepository) {
        Cart cart=new Cart();
        cart.setCustomerId(id);
        cart.setItems(cartInput.getItems().stream().map(cii-> CartItemMapper.map(cii,cart,itemRepository)).collect(Collectors.toList()));
        cart.setLastModifiedTime(ZonedDateTime.now());
        return cart;
    }
}
