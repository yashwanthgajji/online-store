package client.controllers;

import common.models.CartItem;
import common.models.Product;
import common.services.CartService;
import common.services.ProductService;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    public void addItemToCart(String userID, String productID, int qty) throws RemoteException {
        Product product = productService.getProduct(UUID.fromString(productID));
        if (product.getQuantityAvailable() >=  qty) {
            CartItem cartItem = new CartItem(UUID.fromString(productID), qty);
            cartService.addItemToCart(UUID.fromString(userID), cartItem);
        } else {
            System.out.println("Try less quantity. Stock not available");
        }
    }

    public void updateItemQuantityInCart(String userID, String cartItemID, int qty) throws RemoteException {
        UUID uid = UUID.fromString(userID);
        UUID cid = UUID.fromString(cartItemID);
        CartItem cartItem = cartService.getCartItem(uid, cid);
        Product product = productService.getProduct(cartItem.getProductID());
        if (product.getQuantityAvailable() >=  qty) {
            cartService.updateItemQuantityInCart(uid, cid, qty);
        } else {
            System.out.println("Try less quantity. Stock not available");
        }
    }

    public void removeItemFromCart(String userID, String cartItemID) {
        cartService.removeItemFromCart(UUID.fromString(userID), UUID.fromString(cartItemID));
    }

    public void viewAllCartItems(String userID) throws RemoteException {
        List<CartItem> cartItems = cartService.getAllUserCartItems(UUID.fromString(userID));
        System.out.println("********* CART *********");
        StringBuilder sb = new StringBuilder();
        sb.append("S.NO")
                .append("\t").append("CartItem ID")
                .append("\t").append("Product Name")
                .append("\t").append("Description")
                .append("\t").append("Price")
                .append("\t").append("Quantity Selected")
                .append("\n");
        int i = 1;
        for (CartItem cartItem : cartItems) {
            Product product = productService.getProduct(cartItem.getProductID());
            sb.append(i)
                    .append("\t").append(cartItem.getCartItemID())
                    .append("\t").append(product.getName())
                    .append("\t").append(product.getDescription())
                    .append("\t").append(product.getPrice())
                    .append("\t").append(cartItem.getQuantityInCart())
                    .append("\n");
            i++;
        }
        System.out.println(sb);
    }

    public void purchase(String userID) throws RemoteException {
        UUID uid = UUID.fromString(userID);
        List<CartItem> cartItems = cartService.getAllUserCartItems(uid);
        double totalPurchaseCost = 0;
        System.out.println("********* RECEIPT *********");
        System.out.println("Your order contains...");
        StringBuilder sb = new StringBuilder();
        sb.append("S.NO")
                .append("\t").append("Product Name")
                .append("\t").append("Description")
                .append("\t").append("Price")
                .append("\t").append("Quantity Ordered")
                .append("\t").append("Cost")
                .append("\n");
        int i = 1;
        for (CartItem cartItem : cartItems) {
            Product product = productService.getProduct(cartItem.getProductID());
            double cost = product.getPrice() * cartItem.getQuantityInCart();
            totalPurchaseCost += cost;
            productService.updateItemQuantity(
                    product.getProductID(),
                    product.getQuantityAvailable() - cartItem.getQuantityInCart()
            );
            cartService.removeItemFromCart(uid, cartItem.getCartItemID());
            sb.append(i)
                    .append("\t").append(product.getName())
                    .append("\t").append(product.getDescription())
                    .append("\t").append(product.getPrice())
                    .append("\t").append(cartItem.getQuantityInCart())
                    .append("\t").append(cost)
                    .append("\n");
            i++;
        }
        System.out.println(sb);
        System.out.println("Total: " + totalPurchaseCost);
    }
}
