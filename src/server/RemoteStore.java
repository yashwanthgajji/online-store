package server;

import common.FrontController;
import common.ServerDetails;
import server.serviceImpls.CartServiceImpl;
import server.serviceImpls.ProductServiceImpl;
import server.serviceImpls.UserServiceImpl;
import server.services.CartService;
import server.services.ProductService;
import server.services.UserService;

import java.rmi.Naming;

public class RemoteStore {
    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        try {
            FrontController frontController = new FrontControllerImpl();

            Naming.rebind(ServerDetails.SERVER_URL + ServerDetails.YASH_STORE, frontController);
            System.out.println("************** Server ready for online store **************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
