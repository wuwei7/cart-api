package me.dio.cart.resource;

import lombok.RequiredArgsConstructor;
import me.dio.cart.model.Item;
import me.dio.cart.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ifood-dev-week/carts")
@RequiredArgsConstructor
public class CartResource {
    
    private final CartService cartService;
    
    @PostMapping
    public Item addItemToCart(@RequestBody ItemDto itemDto) {
        return cartService.addItemToCart(itemDto);
    }
}
