package server.serviceImpls;

import common.models.CartItem;
import server.repos.CartRepo;
import common.services.CartService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CartServiceImpl extends UnicastRemoteObject implements CartService {

    private CartRepo cartRepo = new CartRepo();

    public CartServiceImpl() throws RemoteException {
    }

    @Override
    public void addItemToCart(String userID, String productID, int qty) throws Exception {
        CartItem cartItem = new CartItem(UUID.fromString(productID), qty);
        cartRepo.addNewCartItem(UUID.fromString(userID), cartItem);
    }

    @Override
    public void updateItemQuantityInCart(UUID userID, UUID cartItemID, int quantity) throws Exception {
        cartRepo.updateCartItemQuantity(userID, cartItemID, quantity);
    }

    @Override
    public void removeItemFromCart(UUID userID, UUID cartItemID) throws Exception {
        cartRepo.deleteCartItem(userID, cartItemID);
    }

    @Override
    public ArrayList<CartItem> getAllUserCartItems(UUID userID) throws Exception {
        ArrayList<CartItem> allCartItems = new ArrayList<>();
        ConcurrentHashMap<UUID, CartItem> cartItemMap = cartRepo.getAllUserCartItems(userID);
        if (cartItemMap != null) {
            for (ConcurrentHashMap.Entry<UUID, CartItem> cartItemEntry : cartItemMap.entrySet()) {
                allCartItems.add(cartItemEntry.getValue());
            }
        }
        return allCartItems;
    }

    @Override
    public CartItem getCartItem(UUID userID, UUID cartItemID) throws Exception {
        return cartRepo.getCartItem(userID, cartItemID);
    }
}
