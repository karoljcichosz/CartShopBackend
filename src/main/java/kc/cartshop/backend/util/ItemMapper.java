package kc.cartshop.backend.util;

import kc.cartshop.backend.domain.item.Item;
import kc.cartshop.data.input.ItemInput;
import kc.cartshop.data.output.ItemOutput;

public class ItemMapper {
    public static Item map(ItemInput itemInput) {
        return new Item(itemInput.getName(), itemInput.getQuantity(), itemInput.getPrice());
    }

    public static ItemOutput map(Item item) {
        return ItemOutput.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }
}