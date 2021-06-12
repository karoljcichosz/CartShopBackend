package kc.cartshop.backend.domain.cart.CartItem;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
class CartItemKey implements Serializable {

    @Column(name = "cart_customer_id")
    UUID cartCustomerId;

    @Column(name = "item_id")
    UUID itemId;

}