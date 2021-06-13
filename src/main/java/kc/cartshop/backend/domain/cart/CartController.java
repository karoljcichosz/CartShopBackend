package kc.cartshop.backend.domain.cart;

import kc.cartshop.backend.domain.cart.CartItem.CartItem;
import kc.cartshop.backend.domain.item.Item;
import kc.cartshop.backend.domain.item.ItemQuantityNotAvailableException;
import kc.cartshop.backend.domain.item.ItemRepository;
import kc.cartshop.backend.util.CartMapper;
import kc.cartshop.data.input.CartInput;
import kc.cartshop.data.input.CartItemInput;
import kc.cartshop.data.output.CartOutput;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@ExposesResourceFor(Cart.class)
@RequestMapping("/carts")
public class CartController {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartController(ItemRepository itemRepository, CartRepository cartService) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartService;
    }

    @GetMapping()
    ResponseEntity<List<CartOutput>> getCarts() {
        try {
            List<CartOutput> cartOutputs = cartRepository.findAll().stream()
                    .map(CartMapper::map)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(cartOutputs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<CartOutput> getCart(@PathVariable Long id) {
        try {
            Cart cart = cartRepository.getCartByCustomerId(id);
            return new ResponseEntity<>(CartMapper.map(cart), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    ResponseEntity<String> createCart(@PathVariable Long id) {
        Cart cart = new Cart();
        cart.setCustomerId(id);
        cart.setLastModifiedTime(ZonedDateTime.now());
        cartRepository.save(cart);
        return new ResponseEntity<>(cart.getCustomerId().toString(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<CartOutput> updateCart(@PathVariable Long id, @RequestBody CartInput cartInput) {
        try {
            Cart newCart=CartMapper.map(cartInput,id,itemRepository);
            cartRepository.save(newCart);
            return new ResponseEntity<>(CartMapper.map(newCart), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}/item")
    ResponseEntity updateItem(@PathVariable Long id, @RequestBody CartItemInput cartItemInput) {
        //TODO: wydzielic do serwisu te rzeczy
        Optional<Cart> oCart = cartRepository.findById(id);
        if (oCart.isPresent()) {
            Cart cart = oCart.get();
            //TODO: moze jak sie uda to equalsem
            Optional<CartItem> oCartItem = cart.getItems().stream().filter(ci -> ci.getItem().getId() == cartItemInput.getItemId()).findFirst();
            if (oCartItem.isPresent()) {
                //TODO: moze jakos do metody to powsadzac (1)
                CartItem cartItem = oCartItem.get();
                if (cartItemInput.getQuantity() > 0) {
                    Optional<Item> oItem = itemRepository.findById(cartItem.getItem().getId());
                    if (oItem.isPresent() && oItem.get().getQuantity() >= cartItemInput.getQuantity())
                        cartItem.setQuantity(cartItemInput.getQuantity());
                    else
                        throw new ItemQuantityNotAvailableException();
                } else
                    cart.removeItemFromCart(cartItem);

            } else if (cartItemInput.getQuantity() > 0) {
                //TODO: moze jakos do metody to powsadzac (2)
                Optional<Item> oItem = itemRepository.findById(cartItemInput.getItemId());
                if (oItem.isPresent() && oItem.get().getQuantity() >= cartItemInput.getQuantity()) {
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setItem(oItem.get());
                    cartItem.setQuantity(cartItemInput.getQuantity());
                    cart.addItemToCart(cartItem);
                } else {
                    if (oItem.isPresent())
                        throw new ItemQuantityNotAvailableException();
                    else throw new NullPointerException();
                }

            }
            cartRepository.save(cart);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}/item/{itemId}")
    ResponseEntity deleteItem(@PathVariable Long id, @PathVariable Long itemId) {
        try {
            Cart cart = cartRepository.getById(id);
            Optional<CartItem> oCartItem = cart.getItems().stream().filter(ci -> ci.getItem().getId() == itemId).findFirst();
            if (oCartItem.isPresent()) {
                cart.removeItemFromCart(oCartItem.get());
                cartRepository.save(cart);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/cartWithItem/{id}")
    ResponseEntity<CartOutput> createCartWithItem(@PathVariable Long id) {
        Cart cart = new Cart();
        cart.setCustomerId(id);
        cart.setLastModifiedTime(ZonedDateTime.now());
        cartRepository.save(cart);
        Item item = new Item();
        item.setName("X");
        item.setPrice(2.0);
        item.setQuantity(200);
        itemRepository.save(item);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(10);
        cartItem.setItem(item);
        cartItem.setCart(cart);
        cart.addItemToCart(cartItem);
//        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
        return new ResponseEntity<>(CartMapper.map(cart), HttpStatus.OK);
    }


}
