package server.controllers;

import common.Requests;
import server.models.CartItem;
import server.models.Product;
import server.serviceImpls.CartServiceImpl;
import server.serviceImpls.ProductServiceImpl;
import server.services.CartService;
import server.services.ProductService;

import java.util.List;
import java.util.UUID;

public class CartController implements MainController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController() {
        this.cartService = new CartServiceImpl();
        this.productService = new ProductServiceImpl();
    }

    @Override
    public Object handleRequest(Requests request, String[] args) {
        switch (request) {
            case Purchase: {
                UUID uid = UUID.fromString(args[0]);
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
            case View_All_CartItems: {
                List<CartItem> cartItems = cartService.getAllUserCartItems(UUID.fromString(args[0]));
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
            case Add_Item_To_Cart: {
                Product product = productService.getProduct(UUID.fromString(args[1]));
                if (product.getQuantityAvailable() >=  Integer.parseInt(args[2])) {
                    CartItem cartItem = new CartItem(UUID.fromString(args[1]), Integer.parseInt(args[2]));
                    cartService.addItemToCart(UUID.fromString(args[0]), cartItem);
                    return "Item added to cart!!!";
                } else {
                    return "Try less quantity. Stock not available";
                }
            }
            case Update_Item_Quantity_In_Cart: {
                UUID uid = UUID.fromString(args[0]);
                UUID cid = UUID.fromString(args[1]);
                CartItem cartItem = cartService.getCartItem(uid, cid);
                Product product = productService.getProduct(cartItem.getProductID());
                if (product.getQuantityAvailable() >=  Integer.parseInt(args[2])) {
                    cartService.updateItemQuantityInCart(uid, cid, Integer.parseInt(args[2]));
                    return "Updated Item Quantity in cart...";
                } else {
                    return "Try less quantity. Stock not available";
                }
            }
            case Remove_Item_From_Cart: {
                cartService.removeItemFromCart(UUID.fromString(args[0]), UUID.fromString(args[1]));
                break;
            }
        }
        return null;
    }
}
