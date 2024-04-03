package server.repos;

import common.models.CartItem;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CartRepo {
//    private static CartRepo cartRepoInstance = null;
//
//    public static CartRepo getCartRepoInstance() {
//        if (cartRepoInstance == null) {
//            cartRepoInstance = new CartRepo();
//        }
//        return cartRepoInstance;
//    }

    private ConcurrentHashMap<UUID, ConcurrentHashMap<UUID, CartItem>> cartItemsByUserIDs;

    public CartRepo() {
        cartItemsByUserIDs = new ConcurrentHashMap<>();
    }

    public void addNewCartItem(UUID userID, CartItem cartItem) {
        ConcurrentHashMap<UUID, CartItem> userCart;
        if (cartItemsByUserIDs.containsKey(userID)) {
            userCart = cartItemsByUserIDs.get(userID);
        } else {
            userCart = new ConcurrentHashMap<>();
        }
        userCart.put(cartItem.getCartItemID(), cartItem);
        cartItemsByUserIDs.put(userID, userCart);
    }

    public void updateCartItemQuantity(UUID userID, UUID cartItemID, int newQty) {
        ConcurrentHashMap<UUID, CartItem> userCart = cartItemsByUserIDs.get(userID);
        if (userCart != null) {
            CartItem cartItem = userCart.get(cartItemID);
            if (cartItem != null) {
                cartItem.setQuantityInCart(newQty);
                userCart.put(cartItemID, cartItem);
                cartItemsByUserIDs.put(userID, userCart);
            }
        }
    }

    public void deleteCartItem(UUID userID, UUID cartItemID) {
        ConcurrentHashMap<UUID, CartItem> userCart = cartItemsByUserIDs.get(userID);
        if (userCart != null) {
            userCart.remove(cartItemID);
            cartItemsByUserIDs.put(userID, userCart);
        }
    }

    public ConcurrentHashMap<UUID, CartItem> getAllUserCartItems(UUID userID) {
        return cartItemsByUserIDs.get(userID);
    }

    public CartItem getCartItem(UUID userID, UUID cartItemID) {
        return cartItemsByUserIDs.get(userID).get(cartItemID);
    }
}
