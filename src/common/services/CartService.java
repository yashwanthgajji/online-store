package common.services;

import common.models.CartItem;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.UUID;

public interface CartService extends Remote {
    public void addItemToCart(String userID, String productID, int qty) throws Exception;
    public void updateItemQuantityInCart(UUID userID, UUID cartItemID, int quantity) throws Exception;
    public void removeItemFromCart(UUID userID, UUID cartItemID) throws Exception;
    public ArrayList<CartItem> getAllUserCartItems(UUID userID) throws Exception;
    public CartItem getCartItem(UUID userID, UUID cartItemID) throws Exception;
}