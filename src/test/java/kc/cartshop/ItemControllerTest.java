package kc.cartshop;

import kc.cartshop.backend.domain.item.Item;
import kc.cartshop.backend.domain.item.ItemController;
import kc.cartshop.backend.domain.item.ItemRepository;
import kc.cartshop.data.input.ItemInput;
import kc.cartshop.data.output.ItemOutput;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class ItemControllerTest {
    @InjectMocks
    ItemController itemController;

    @Mock
    ItemRepository itemRepository;
//    @Mock
//    Item item;

    @Test
    @DisplayName("Test add valid item")
    public void testAddItem() throws JSONException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ItemInput itemInput = ItemInput.builder().name("item").price(2.0).quantity(100).build();
        //when(itemRepository.save(any(Item.class))).thenReturn(new Item());
        ResponseEntity<ItemOutput> responseEntity = itemController.addItem(itemInput);
        assert (responseEntity.getStatusCodeValue() == 201);
    }

}
