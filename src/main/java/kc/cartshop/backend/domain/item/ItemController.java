package kc.cartshop.backend.domain.item;

import kc.cartshop.backend.util.ItemMapper;
import kc.cartshop.data.input.ItemInput;
import kc.cartshop.data.output.ItemOutput;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ExposesResourceFor(Item.class)
@RequestMapping("/item")
public class ItemController {

    final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostMapping()
    public ResponseEntity<ItemOutput> addItem(@RequestBody ItemInput itemInput) {
        Item item = ItemMapper.map(itemInput);
        itemRepository.save(item);
        return new ResponseEntity<>(ItemMapper.map(item), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemOutput>> getItems(){
        return new ResponseEntity<>(itemRepository.findAll().stream().map(i->ItemMapper.map(i)).collect(Collectors.toList()), HttpStatus.OK);
    }
}
