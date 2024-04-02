package serviceImpls;

import models.CartItem;
import repos.CartRepo;
import services.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo = CartRepo.getCartRepoInstance();

    @Override
    public void addItemToCart(UUID userID, CartItem cartItem) {
        cartRepo.addNewCartItem(userID, cartItem);
    }

    @Override
    public void updateItemQuantityInCart(UUID userID, UUID cartItemID, int quantity) {
        cartRepo.updateCartItemQuantity(userID, cartItemID, quantity);
    }

    @Override
    public void removeItemFromCart(UUID userID, UUID cartItemID) {
        cartRepo.deleteCartItem(userID, cartItemID);
    }

    @Override
    public List<CartItem> getAllUserCartItems(UUID userID) {
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
    public CartItem getCartItem(UUID userID, UUID cartItemID) {
        return cartRepo.getCartItem(userID, cartItemID);
    }
}
