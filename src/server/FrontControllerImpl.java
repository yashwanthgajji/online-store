package server;

import common.FrontController;
import common.Requests;
import server.controllers.CartController;
import server.controllers.ProductController;
import server.controllers.UserController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FrontControllerImpl extends UnicastRemoteObject implements FrontController {

    private final UserController userController;
    private final ProductController productController;
    private final CartController cartController;

    protected FrontControllerImpl() throws RemoteException {
        userController = new UserController();
        productController = new ProductController();
        cartController = new CartController();
    }

    @Override
    public String handleRequest(Requests request, String[] args) throws RemoteException {
        String res = "Unknown Request";
        switch (request) {
            case Get_UserName -> res = userController.getUserName(args[0]);
            case Register_New_Customer -> res = userController.registerNewCustomer(args[0], args[1], args[2]);
            case IsUserAdmin -> res = String.valueOf(userController.isUserAdmin(args[0]));
            case Add_New_Admin -> {
                userController.addNewAdmin(args[0], args[1], args[2]);
                res = null;
            }
            case View_All_Customers -> res = userController.viewAllCustomers();
            case Add_New_Customer -> {
                userController.addNewCustomer(args[0], args[1], args[2]);
                res = null;
            }
            case Remove_Customer -> {
                userController.removeCustomer(args[0]);
                res = null;
            }
            case View_All_Products -> res = productController.viewAllProducts();
            case Add_New_Product -> productController.addNewProduct(
                    args[0],
                    args[1],
                    Double.parseDouble(args[2]),
                    Integer.parseInt(args[3])
            );
            case Remove_Product -> productController.removeProduct(args[0]);
            case Update_Item_Description -> productController.updateItemDescription(args[0], args[1]);
            case Update_Item_Price -> productController.updateItemPrice(args[0], Double.parseDouble(args[1]));
            case Update_Item_Quantity -> productController.updateItemQuantity(args[0], Integer.parseInt(args[1]));
            case View_All_CartItems -> {
                if (!userController.isUserAdmin(args[0])) {
                    res = cartController.viewAllCartItems(args[0]);
                }
            }
            case Add_Item_To_Cart -> {
                if (!userController.isUserAdmin(args[0])) {
                    cartController.addItemToCart(args[0], args[1], Integer.parseInt(args[2]));
                }
            }
            case Update_Item_Quantity_In_Cart -> {
                if (!userController.isUserAdmin(args[0])) {
                    cartController.updateItemQuantityInCart(args[0], args[1], Integer.parseInt(args[2]));
                }
            }
            case Remove_Item_From_Cart -> {
                if (!userController.isUserAdmin(args[0])) {
                    cartController.removeItemFromCart(args[0], args[1]);
                }
            }
            case Purchase -> {
                if (!userController.isUserAdmin(args[0])) {
                    res = cartController.purchase(args[0]);
                }
            }
        }
        return res;
    }
}
