package common.services;

import common.models.CartItem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface CartService extends Remote {
    public void addItemToCart(UUID userID, CartItem cartItem) throws RemoteException;
    public void updateItemQuantityInCart(UUID userID, UUID cartItemID, int quantity) throws RemoteException;
    public void removeItemFromCart(UUID userID, UUID cartItemID) throws RemoteException;
    public List<CartItem> getAllUserCartItems(UUID userID) throws RemoteException;
    public CartItem getCartItem(UUID userID, UUID cartItemID) throws RemoteException;
}