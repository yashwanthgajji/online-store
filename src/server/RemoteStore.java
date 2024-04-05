package server;

import common.ServerDetails;
import server.serviceImpls.CartServiceImpl;
import server.serviceImpls.ProductServiceImpl;
import server.serviceImpls.UserServiceImpl;
import common.services.CartService;
import common.services.ProductService;
import common.services.UserService;

import java.rmi.Naming;

public class RemoteStore {
    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        try {
            UserService userService = new UserServiceImpl();
            ProductService productService = new ProductServiceImpl();
            CartService cartService = new CartServiceImpl();

            Naming.rebind(ServerDetails.SERVER_URL + ServerDetails.USER_STUB_NAME, userService);
            Naming.rebind(ServerDetails.SERVER_URL + ServerDetails.PRODUCT_STUB_NAME, productService);
            Naming.rebind(ServerDetails.SERVER_URL + ServerDetails.CART_STUB_NAME, cartService);
            System.out.println("************** Server ready for online store **************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
