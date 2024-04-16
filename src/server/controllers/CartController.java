package server.controllers;

import server.models.CartItem;
import server.models.Product;
import server.serviceImpls.CartServiceImpl;
import server.serviceImpls.ProductServiceImpl;
import server.services.CartService;
import server.services.ProductService;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController() {
        this.cartService = new CartServiceImpl();
        this.productService = new ProductServiceImpl();
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

    public void removeItemFromCart(String userID, String cartItemID) throws RemoteException {
        cartService.removeItemFromCart(UUID.fromString(userID), UUID.fromString(cartItemID));
    }

    public String viewAllCartItems(String userID) throws RemoteException {
        List<CartItem> cartItems = cartService.getAllUserCartItems(UUID.fromString(userID));
        StringBuilder sb = new StringBuilder();
        sb.append("********* CART *********\n");
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
        return sb.toString();
    }

    public String purchase(String userID) throws RemoteException {
        UUID uid = UUID.fromString(userID);
        List<CartItem> cartItems = cartService.getAllUserCartItems(uid);
        double totalPurchaseCost = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("********* RECEIPT *********\n");
        sb.append("Your order contains...\n");
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
        sb.append("Total: ").append(totalPurchaseCost).append("\n");
        return sb.toString();
    }
}
