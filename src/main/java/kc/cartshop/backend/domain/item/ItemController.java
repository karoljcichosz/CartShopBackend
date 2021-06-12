package kc.cartshop.backend.domain.item;

import kc.cartshop.backend.util.ItemMapper;
import kc.cartshop.data.input.ItemInput;
import kc.cartshop.data.output.ItemOutput;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Item.class)
@RequestMapping("/item")
public class ItemController {

    final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostMapping()
    ResponseEntity<ItemOutput> createItem(@RequestBody ItemInput itemInput){
        Item item= ItemMapper.map(itemInput);
        itemRepository.save(item);
        return new ResponseEntity<>(ItemMapper.map(item), HttpStatus.OK);
    }
}
