package me.dio.cart.service;

import me.dio.cart.model.Cart;
import me.dio.cart.model.Item;
import me.dio.cart.resource.ItemDto;

public interface CartService {
    Item addItemToCart(ItemDto itemDto);
    Cart seeCart(Long id);
    Cart closeCart(Long id, int paymentMethod);
}
