package kc.cartshop.backend.domain.cart;

import kc.cartshop.backend.domain.cart.CartItem.CartItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;


@Data
@RequiredArgsConstructor
@Entity
public class Cart implements Serializable {


    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();
    private ZonedDateTime lastModifiedTime;

    public Collection<CartItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    public void addItemToCart(CartItem cartItem) {
        if (cartItem.isAvailable()) {
            this.items.add(cartItem);
            updateLastModifiedTime();
        }
    }

    public void removeItemFromCart(CartItem cartItem) {
        this.items.remove(cartItem);
    }

    public double getPrice() {
        double price = 0.0;
        for (CartItem ci : items) {
            price = price + ci.getPrice();
        }
        return price;
    }

    private void updateLastModifiedTime() {
        this.lastModifiedTime = ZonedDateTime.now();
    }
}
