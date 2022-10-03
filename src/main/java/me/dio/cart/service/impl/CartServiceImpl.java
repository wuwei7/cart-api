package me.dio.cart.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.cart.enumeration.PaymentMethod;
import me.dio.cart.model.Cart;
import me.dio.cart.model.Item;
import me.dio.cart.model.Restaurant;
import me.dio.cart.repository.CartRepository;
import me.dio.cart.repository.ItemRepository;
import me.dio.cart.repository.ProductRepository;
import me.dio.cart.resource.dto.ItemDto;
import me.dio.cart.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    @Override
    public Item addItemToCart(ItemDto itemDto) {
        Cart cart = seeCart(itemDto.getCartId());
    
        if (cart.isClosed()) {
            throw new RuntimeException("This cart is closed.");
        }
        
        Item itemToBeAdded = Item.builder()
                .amount(itemDto.getAmount())
                .cart(cart)
                .product(productRepository.findById(itemDto.getProductId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("This product does not exist.");
                        }
                ))
                .build();
    
        List<Item> cartItems = cart.getItems();
        if (cartItems.isEmpty()) {
            cartItems.add(itemToBeAdded);
        } else {
            Restaurant currentRestaurant = cartItems.get(0).getProduct().getRestaurant();
            Restaurant restaurantOfItemToBeAdded = itemToBeAdded.getProduct().getRestaurant();
            if (currentRestaurant.equals(restaurantOfItemToBeAdded)) {
                cartItems.add(itemToBeAdded);
            } else {
                throw new RuntimeException("You can't add products from different restaurants. Please close or empty the cart first.");
            }
        }
        
        List<Double> priceOfItems = new ArrayList<>();
        
        for (Item cartItem: cartItems) {
            double itemGrandTotal = cartItem.getProduct().getUnitValue() * cartItem.getAmount();
            priceOfItems.add(itemGrandTotal);
        }
        
        Double cartGrandTotal = priceOfItems.stream()
                .mapToDouble(grandTotalOfEachItem -> grandTotalOfEachItem)
                .sum();
        
        cart.setGrandTotal(cartGrandTotal);
        
        cartRepository.save(cart);
        
        return itemRepository.save(itemToBeAdded);
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
