package repos;

import models.CartItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CartRepo {
    private static CartRepo cartRepoInstance = null;

    public static CartRepo getCartRepoInstance() {
        if (cartRepoInstance == null) {
            cartRepoInstance = new CartRepo();
        }
        return cartRepoInstance;
    }

    private final Map<UUID, Map<UUID, CartItem>> cartItemsByUserIDs;

    private CartRepo() {
        cartItemsByUserIDs = new HashMap<>();
    }

    public void addNewCartItem(UUID userID, CartItem cartItem) {
        Map<UUID, CartItem> userCart;
        if (cartItemsByUserIDs.containsKey(userID)) {
            userCart = cartItemsByUserIDs.get(userID);
        } else {
            userCart = new HashMap<>();
        }
        userCart.put(cartItem.getCartItemID(), cartItem);
        cartItemsByUserIDs.put(userID, userCart);
    }

    public void updateCartItemQuantity(UUID userID, UUID cartItemID, int newQty) {
        Map<UUID, CartItem> userCart = cartItemsByUserIDs.get(userID);
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
        Map<UUID, CartItem> userCart = cartItemsByUserIDs.get(userID);
        if (userCart != null) {
            userCart.remove(cartItemID);
            cartItemsByUserIDs.put(userID, userCart);
        }
    }

    public Map<UUID, CartItem> getAllUserCartItems(UUID userID) {
        return cartItemsByUserIDs.get(userID);
    }

    public CartItem getCartItem(UUID userID, UUID cartItemID) {
        return cartItemsByUserIDs.get(userID).get(cartItemID);
    }
}
