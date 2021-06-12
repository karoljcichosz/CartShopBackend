package kc.cartshop.backend.domain.cart.CartItem;

import kc.cartshop.backend.domain.cart.Cart;
import kc.cartshop.backend.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem implements Serializable {

    @EmbeddedId
    CartItemKey cartItemKey = new CartItemKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartCustomerId")
    @JoinColumn(name = "cart_customer_id")
    private Cart cart;

    @ManyToOne()
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;

    public boolean isAvailable() {
        return item.getQuantity() >= quantity;
    }

    public double getPrice() {
        return quantity * item.getPrice();
    }

    @Override
    public String toString() {
        return MessageFormat.format("[{0} : {1}]", cart.getCustomerId(), item.getId());
    }
}
