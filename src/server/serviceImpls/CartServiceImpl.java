package server.serviceImpls;

import common.models.CartItem;
import server.repos.CartRepo;
import common.services.CartService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartServiceImpl extends UnicastRemoteObject implements CartService {

    private final CartRepo cartRepo = CartRepo.getCartRepoInstance();

    public CartServiceImpl() throws RemoteException {
    }

    @Override
    public void addItemToCart(UUID userID, CartItem cartItem) throws RemoteException {
        cartRepo.addNewCartItem(userID, cartItem);
    }

    @Override
    public void updateItemQuantityInCart(UUID userID, UUID cartItemID, int quantity) throws RemoteException {
        cartRepo.updateCartItemQuantity(userID, cartItemID, quantity);
    }

    @Override
    public void removeItemFromCart(UUID userID, UUID cartItemID) throws RemoteException {
        cartRepo.deleteCartItem(userID, cartItemID);
    }

    @Override
    public List<CartItem> getAllUserCartItems(UUID userID) throws RemoteException {
        List<CartItem> allCartItems = new ArrayList<>();
        Map<UUID, CartItem> cartItemMap = cartRepo.getAllUserCartItems(userID);
        if (cartItemMap != null) {
            for (Map.Entry<UUID, CartItem> cartItemEntry : cartItemMap.entrySet()) {
                allCartItems.add(cartItemEntry.getValue());
            }
        }
        return allCartItems;
    }

    @Override
    public CartItem getCartItem(UUID userID, UUID cartItemID) throws RemoteException {
        return cartRepo.getCartItem(userID, cartItemID);
    }
}
