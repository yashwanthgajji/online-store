package server.services;

import server.models.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartService {
    public void addItemToCart(UUID userID, CartItem cartItem);
    public void updateItemQuantityInCart(UUID userID, UUID cartItemID, int quantity);
    public void removeItemFromCart(UUID userID, UUID cartItemID);
    public List<CartItem> getAllUserCartItems(UUID userID);
    public CartItem getCartItem(UUID userID, UUID cartItemID);
}