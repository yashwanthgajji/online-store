package common.models;

import java.io.Serializable;
import java.util.UUID;

public class CartItem implements Serializable {
    private UUID cartItemID;
    private UUID productID;
    private int quantityInCart;

    public CartItem(UUID productID, int quantityInCart) {
        this.cartItemID = UUID.randomUUID();
        this.productID = productID;
        this.quantityInCart = quantityInCart;
    }

    public UUID getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(UUID cartItemID) {
        this.cartItemID = cartItemID;
    }

    public UUID getProductID() {
        return productID;
    }

    public void setProductID(UUID productID) {
        this.productID = productID;
    }

    public int getQuantityInCart() {
        return quantityInCart;
    }

    public void setQuantityInCart(int quantityInCart) {
        this.quantityInCart = quantityInCart;
    }
}
