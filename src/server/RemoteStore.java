package server;

import server.serviceImpls.CartServiceImpl;
import server.serviceImpls.ProductServiceImpl;
import server.serviceImpls.UserServiceImpl;
import common.services.CartService;
import common.services.ProductService;
import common.services.UserService;

import java.rmi.Naming;

public class RemoteStore {
    public static void main(String[] args) {
        try {
            UserService userService = new UserServiceImpl();
            ProductService productService = new ProductServiceImpl();
            CartService cartService = new CartServiceImpl();

            Naming.rebind("store-user", userService);
            Naming.rebind("store-product", productService);
            Naming.rebind("store-cart", cartService);
            System.out.println("************** Server ready for online store **************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
