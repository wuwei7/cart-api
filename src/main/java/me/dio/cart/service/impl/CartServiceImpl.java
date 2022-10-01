package me.dio.cart.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.cart.enumeration.PaymentMethod;
import me.dio.cart.model.Cart;
import me.dio.cart.model.Item;
import me.dio.cart.repository.CartRepository;
import me.dio.cart.resource.dto.ItemDto;
import me.dio.cart.service.CartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    @Override
    public Item addItemToCart(ItemDto itemDto) {
        return null;
    }
    
    @Override
    public Cart seeCart(Long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("This cart does not exist!");
                }
        );
    }
    
    @Override
    public Cart closeCart(Long id, int paymentMethodNumber) {
        Cart cart = seeCart(id);
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Add items to cart.");
        }
        
        PaymentMethod paymentMethod = paymentMethodNumber == 0 ? PaymentMethod.CASH : PaymentMethod.TERMINAL;
        
        cart.setPaymentMethod(paymentMethod);
        cart.setClosed(true);
        return cartRepository.save(cart);
    }
}
