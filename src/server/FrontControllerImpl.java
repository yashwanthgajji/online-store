package server;

import common.FrontController;
import common.Requests;
import server.auth.AuthorizationService;
import server.auth.HTTPMethod;
import server.auth.ServicePoint;
import server.auth.UserRole;
import server.controllers.ControllerFactory;
import server.controllers.ControllerType;
import server.controllers.MainController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FrontControllerImpl extends UnicastRemoteObject implements FrontController {

    private final MainController authenticationController;
    private final MainController userController;
    private final MainController productController;
    private final MainController cartController;
    private final AuthorizationService authorizationService;

    protected FrontControllerImpl() throws RemoteException {
        authenticationController = ControllerFactory.createController(ControllerType.AuthenticationController);
        userController = ControllerFactory.createController(ControllerType.UserController);
        productController = ControllerFactory.createController(ControllerType.ProductController);
        cartController = ControllerFactory.createController(ControllerType.CartController);
        authorizationService = new AuthorizationService();
    }

    @Override
    public String handleRequest(Requests request, String[] args) throws RemoteException {
        String res = "Unknown Request";
        switch (request) {
            //User Requests
            case Get_UserName -> res = (String) userController.handleRequest(Requests.Get_UserName, args);
            case Register_New_Customer -> res = (String) authenticationController.handleRequest(Requests.Register_New_Customer, args);
            case Login -> res = (String) authenticationController.handleRequest(Requests.Login, args);
            case IsUserAdmin -> {
                UserRole userRole = (UserRole) userController.handleRequest(Requests.Get_User_Role, args);
                res = String.valueOf(userRole == UserRole.ADMIN);
            }
            case Add_New_Admin -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.USER,
                        HTTPMethod.PUT
                )) {
                    userController.handleRequest(Requests.Add_New_Admin, new String[]{args[1], args[2], args[3]});
                    res = null;
                } else {
                    res = "Unauthorized Request";
                }
            }
            case View_All_Customers -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.USER,
                        HTTPMethod.GET
                )) {
                    res = (String) userController.handleRequest(Requests.View_All_Customers, new String[0]);
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Add_New_Customer -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.USER,
                        HTTPMethod.PUT
                )) {
                    userController.handleRequest(Requests.Add_New_Customer, new String[]{args[1], args[2], args[3]});
                    res = null;
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Remove_Customer -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.USER,
                        HTTPMethod.DELETE
                )) {
                    userController.handleRequest(Requests.Remove_Customer, new String[]{args[1]});
                    res = null;
                } else {
                    res = "Unauthorized Request";
                }
            }
            //Product Requests
            case View_All_Products -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.PRODUCT,
                        HTTPMethod.GET
                )) {
                    res = (String) productController.handleRequest(Requests.View_All_Products, new String[0]);
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Add_New_Product -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.PRODUCT,
                        HTTPMethod.PUT
                )) {
                    productController.handleRequest(
                            Requests.Add_New_Product,
                            new String[]{args[1], args[2], args[3], args[4]}
                    );
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Remove_Product -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.PRODUCT,
                        HTTPMethod.DELETE
                )) {
                    productController.handleRequest(Requests.Remove_Product, new String[]{args[1]});
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Update_Item_Description -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.PRODUCT,
                        HTTPMethod.PUT
                )) {
                    productController.handleRequest(Requests.Update_Item_Description, new String[]{args[1], args[2]});
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Update_Item_Price -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.PRODUCT,
                        HTTPMethod.PUT
                )) {
                    productController.handleRequest(Requests.Update_Item_Price, new String[]{args[1], args[2]});
                } else {
                    res = "Unauthorized Request";
                }
            }
            case Update_Item_Quantity -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.PRODUCT,
                        HTTPMethod.PUT
                )) {
                    productController.handleRequest(Requests.Update_Item_Quantity, new String[]{args[1], args[2]});
                } else {
                    res = "Unauthorized Request";
                }
            }
            //Cart Requests
            case View_All_CartItems -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.CART,
                        HTTPMethod.GET
                )) {
                    res = (String) cartController.handleRequest(Requests.View_All_CartItems, args);
                }
            }
            case Add_Item_To_Cart -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.CART,
                        HTTPMethod.PUT
                )) {
                    res = (String) cartController.handleRequest(Requests.Add_Item_To_Cart, args);
                }
            }
            case Update_Item_Quantity_In_Cart -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.CART,
                        HTTPMethod.PUT
                )) {
                    res = (String) cartController.handleRequest(Requests.Update_Item_Quantity_In_Cart ,args);
                }
            }
            case Remove_Item_From_Cart -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.CART,
                        HTTPMethod.DELETE
                )) {
                    cartController.handleRequest(Requests.Remove_Item_From_Cart, args);
                }
            }
            case Purchase -> {
                if (authorizationService.isAuthorized(
                        (UserRole) userController.handleRequest(Requests.Get_User_Role, args),
                        ServicePoint.CART,
                        HTTPMethod.PUT
                )) {
                    res = (String) cartController.handleRequest(Requests.Purchase, args);
                }
            }
        }
        return res;
    }
}
