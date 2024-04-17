package server;

import common.FrontController;
import common.Requests;
import server.auth.AuthorizationService;
import server.auth.HTTPMethod;
import server.auth.ServicePoint;
import server.auth.UserRole;
import server.controllers.CartController;
import server.controllers.ProductController;
import server.controllers.UserController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FrontControllerImpl extends UnicastRemoteObject implements FrontController {

    private final UserController userController;
    private final ProductController productController;
    private final CartController cartController;
    private final AuthorizationService authorizationService;

    protected FrontControllerImpl() throws RemoteException {
        userController = new UserController();
        productController = new ProductController();
        cartController = new CartController();
        authorizationService = new AuthorizationService();
    }

    @Override
    public String handleRequest(Requests request, String[] args) throws RemoteException {
        String res = "Unknown Request";
        switch (request) {
            //User Requests
            case Get_UserName -> res = userController.getUserName(args[0]);
            case Register_New_Customer -> res = userController.registerNewCustomer(args[0], args[1], args[2]);
            case Login -> res = userController.login(args[0], args[1]);
            case IsUserAdmin -> res = String.valueOf(userController.getUserRole(args[0]) == UserRole.ADMIN);
            case Add_New_Admin -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.USER,
                        HTTPMethod.PUT
                )) {
                    userController.addNewAdmin(args[1], args[2], args[3]);
                    res = null;
                } else {
                    res = "Unauthorized Request";
                }
            }
            case View_All_Customers -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.USER,
                        HTTPMethod.GET
                )) {
                    res = userController.viewAllCustomers();
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Add_New_Customer -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.USER,
                        HTTPMethod.PUT
                )) {
                    userController.addNewCustomer(args[1], args[2], args[3]);
                    res = null;
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Remove_Customer -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.USER,
                        HTTPMethod.DELETE
                )) {
                    userController.removeCustomer(args[1]);
                    res = null;
                } else {
                    res = "Unauthorized Request";
                }
            }
            //Product Requests
            case View_All_Products -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.PRODUCT,
                        HTTPMethod.GET
                )) {
                    res = productController.viewAllProducts();
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Add_New_Product -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.PRODUCT,
                        HTTPMethod.PUT
                )) {
                    productController.addNewProduct(
                            args[1],
                            args[2],
                            Double.parseDouble(args[3]),
                            Integer.parseInt(args[4])
                    );
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Remove_Product -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.PRODUCT,
                        HTTPMethod.DELETE
                )) {
                    productController.removeProduct(args[1]);
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Update_Item_Description -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.PRODUCT,
                        HTTPMethod.PUT
                )) {
                    productController.updateItemDescription(args[1], args[2]);
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Update_Item_Price -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.PRODUCT,
                        HTTPMethod.PUT
                )) {
                    productController.updateItemPrice(args[1], Double.parseDouble(args[2]));
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Update_Item_Quantity -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.PRODUCT,
                        HTTPMethod.PUT
                )) {
                    productController.updateItemQuantity(args[1], Integer.parseInt(args[2]));
                } else {
                    res = "Unauthorized Request";
                }
            }
            //Cart Requests
            case View_All_CartItems -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.CART,
                        HTTPMethod.GET
                )) {
                    res = cartController.viewAllCartItems(args[0]);
                }
            }
            case Add_Item_To_Cart -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.CART,
                        HTTPMethod.PUT
                )) {
                    cartController.addItemToCart(args[0], args[1], Integer.parseInt(args[2]));
                }
            }
            case Update_Item_Quantity_In_Cart -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.CART,
                        HTTPMethod.PUT
                )) {
                    cartController.updateItemQuantityInCart(args[0], args[1], Integer.parseInt(args[2]));
                }
            }
            case Remove_Item_From_Cart -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.CART,
                        HTTPMethod.DELETE
                )) {
                    cartController.removeItemFromCart(args[0], args[1]);
                }
            }
            case Purchase -> {
                if (authorizationService.isAuthorized(
                        userController.getUserRole(args[0]),
                        ServicePoint.CART,
                        HTTPMethod.PUT
                )) {
                    res = cartController.purchase(args[0]);
                }
            }
        }
        return res;
    }
}
