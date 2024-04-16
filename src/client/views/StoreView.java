package client.views;

import common.FrontController;
import common.Requests;
import common.ServerDetails;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class StoreView {

    private static String sessionUserID;
    private static FrontController frontController;
    private static Scanner sc;

    public static void main(String[] args) throws Exception {

        System.out.println("*** Connecting to Server ***");
        try {
            frontController = (FrontController) Naming.lookup(ServerDetails.SERVER_URL + ServerDetails.YASH_STORE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("*** Connection Successful");

        sc = new Scanner(System.in);

        startView();
    }

    private static void startView() throws Exception {
        System.out.println("********* WELCOME *********");
        System.out.println("1. Register\n" +
                "2. Login\n");
        int regLog = Integer.parseInt(sc.nextLine());
        if (regLog == 1) {
            registrationPage();
        } else {
            loginPage();
        }
        if (sessionUserID != null) {
            String[] args = new String[1];
            args[0] = sessionUserID;
            String userName = frontController.handleRequest(Requests.Get_UserName, args);
            System.out.println("Welcome " + userName);
            startDashboard();
        } else {
            System.out.println("Registration/Login unsuccessful");
        }
    }

    private static void registrationPage() throws RemoteException {
        System.out.println("********* REGISTRATION *********");
        String[] regArgs = new String[3];
        System.out.println("Enter Name....");
        regArgs[0] = sc.nextLine();
        System.out.println("Enter Email....");
        regArgs[1] = sc.nextLine();
        System.out.println("Enter Password....");
        regArgs[2] = sc.nextLine();
        System.out.println("Registering user....");
        sessionUserID = frontController.handleRequest(Requests.Register_New_Customer, regArgs);
        System.out.println("Registration Successful");
    }

    private static void loginPage() throws RemoteException {
        System.out.println("********* LOGIN *********");
        String[] logArgs = new String[2];
        System.out.println("Enter Email....");
        logArgs[0] = sc.nextLine();
        System.out.println("Enter Password....");
        logArgs[1] = sc.nextLine();
        System.out.println("Authenticating user....");
        sessionUserID = frontController.handleRequest(Requests.Login, logArgs);
        System.out.println("Login Successful....");
    }

    private static void startDashboard() throws RemoteException {
        System.out.println("********* DASHBOARD *********");
        if (Boolean.parseBoolean(frontController.handleRequest(Requests.IsUserAdmin, new String[]{sessionUserID}))) {
            int action = -1;
            while (action != 4) {
                System.out.println("********* ACTIONS *********");
                System.out.println("1. View Products\n" +
                        "2. View Customers\n" +
                        "3. Add new Admin\n" +
                        "4. Logout\n");
                action = Integer.parseInt(sc.nextLine());
                switch (action) {
                    case 1: {
                        adminProductsPage();
                        break;
                    }
                    case 2: {
                        adminCustomersPage();
                        break;
                    }
                    case 3: {
                        registerAdminPage();
                        break;
                    }
                    case 4: {
                        sessionUserID = null;
                        System.out.println("********* LOGOUT *********");
                        break;
                    }
                    default: {
                        System.out.println("Select a valid action");
                        break;
                    }
                }
            }
        } else {
            int action = -1;
            while (action != 3) {
                System.out.println(frontController.handleRequest(Requests.View_All_Products, new String[0]));
                System.out.println("********* ACTIONS *********");
                System.out.println("1. Add a product to cart\n" +
                        "2. View Cart\n" +
                        "3. Logout\n");
                action = Integer.parseInt(sc.nextLine());
                switch (action) {
                    case 1: {
                        System.out.println("********* ADD PRODUCT TO CART *********");
                        System.out.println("Enter product ID....");
                        String pid = sc.nextLine();
                        System.out.println("Enter quantity....");
                        String qty = sc.nextLine();
                        frontController.handleRequest(Requests.Add_Item_To_Cart, new String[]{sessionUserID, pid, qty});
                        break;
                    }
                    case 2: {
                        customerCartPage();
                        break;
                    }
                    case 3: {
                        sessionUserID = null;
                        System.out.println("********* LOGOUT *********");
                        break;
                    }
                    default: {
                        System.out.println("Select a valid action");
                        break;
                    }
                }
            }
        }
    }

    private static void customerCartPage() throws RemoteException {
        int action = -1;
        while (action != 4) {
            System.out.println(frontController.handleRequest(
                    Requests.View_All_CartItems,
                    new String[]{sessionUserID}
            ));
            System.out.println("********* ACTIONS *********");
            System.out.println("1. Update cart item quantity\n" +
                    "2. Remove a cart item\n" +
                    "3. Buy Now\n" +
                    "4. Go back\n");
            action = Integer.parseInt(sc.nextLine());
            switch (action) {
                case 1: {
                    System.out.println("********* UPDATE PRODUCT IN CART *********");
                    System.out.println("Enter cart item ID to update....");
                    String cid = sc.nextLine();
                    System.out.println("Enter quantity....");
                    String qty = sc.nextLine();
                    frontController.handleRequest(
                            Requests.Update_Item_Quantity_In_Cart,
                            new String[]{sessionUserID, cid, qty}
                    );
                    break;
                }
                case 2: {
                    System.out.println("********* REMOVE PRODUCT IN CART *********");
                    System.out.println("Enter cart item ID to update....");
                    String cid = sc.nextLine();
                    frontController.handleRequest(
                            Requests.Remove_Item_From_Cart,
                            new String[]{sessionUserID, cid}
                    );
                    break;
                }
                case 3: {
                    System.out.println("********* ORDER *********");
                    System.out.println("Placing order...");
                    System.out.println(frontController.handleRequest(Requests.Purchase, new String[]{sessionUserID}));
                    System.out.println("********* ORDER PLACED SUCCESSFULLY *********");
                    break;
                }
                case 4: {
                    System.out.println("********* BACK *********");
                    break;
                }
                default: {
                    System.out.println("Select a valid action");
                    break;
                }
            }
        }
    }

    private static void registerAdminPage() throws RemoteException {
        System.out.println("********* ADMIN REGISTRATION *********");
        String[] regArgs = new String[3];
        System.out.println("Enter Name....");
        regArgs[0] = sc.nextLine();
        System.out.println("Enter Email....");
        regArgs[1] = sc.nextLine();
        System.out.println("Enter Password....");
        regArgs[2] = sc.nextLine();
        System.out.println("Registering admin....");
        frontController.handleRequest(Requests.Add_New_Admin, regArgs);
        System.out.println("Admin Registration Successful");
    }

    private static void adminCustomersPage() throws RemoteException {
        int action = -1;
        while (action != 3) {
            System.out.println(frontController.handleRequest(Requests.View_All_Customers, new String[0]));
            System.out.println("********* ACTIONS *********");
            System.out.println("1. Add a new customer\n" +
                    "2. Remove a customer\n" +
                    "3. Go back\n");
            action = Integer.parseInt(sc.nextLine());
            switch (action) {
                case 1: {
                    System.out.println("********* ADD NEW CUSTOMER *********");
                    String[] custArgs = new String[3];
                    System.out.println("Enter name....");
                    custArgs[0] = sc.nextLine();
                    System.out.println("Enter email....");
                    custArgs[1] = sc.nextLine();
                    System.out.println("Enter password....");
                    custArgs[2] = sc.nextLine();
                    System.out.println("Adding customer to database....");
                    frontController.handleRequest(Requests.Add_New_Customer, custArgs);
                    System.out.println("Customer added successfully....");
                    break;
                }
                case 2: {
                    System.out.println("********* REMOVE CUSTOMER *********");
                    System.out.println("Enter customer ID");
                    String cid = sc.nextLine();
                    frontController.handleRequest(Requests.Remove_Customer, new String[]{cid});
                    break;
                }
                case 3: {
                    System.out.println("********* BACK *********");
                    break;
                }
                default: {
                    System.out.println("Select a valid action");
                    break;
                }
            }
        }
    }

    private static void adminProductsPage() throws RemoteException {
        int action = -1;
        while (action != 6) {
            System.out.println(frontController.handleRequest(Requests.View_All_Products, new String[0]));
            System.out.println("********* ACTIONS *********");
            System.out.println("1. Add a new product\n" +
                    "2. Remove a product\n" +
                    "3. Change description of a product\n" +
                    "4. Change price of a product\n" +
                    "5. Change stock quantity of a product\n" +
                    "6. Go back\n");
            action = Integer.parseInt(sc.nextLine());
            switch (action) {
                case 1: {
                    System.out.println("********* ADD NEW ITEM *********");
                    String[] args = new String[4];
                    System.out.println("Enter name....");
                    args[0] = sc.nextLine();
                    System.out.println("Enter description....");
                    args[1] = sc.nextLine();
                    System.out.println("Enter price....");
                    args[2] = sc.nextLine();
                    System.out.println("Enter quantity....");
                    args[3] = sc.nextLine();
                    System.out.println("Adding item to database....");
                    frontController.handleRequest(Requests.Add_New_Product, args);
                    System.out.println("Item added successfully....");
                    break;
                }
                case 2: {
                    System.out.println("********* REMOVE ITEM *********");
                    System.out.println("Enter productID");
                    String pid = sc.nextLine();
                    frontController.handleRequest(Requests.Remove_Product, new String[]{pid});
                    break;
                }
                case 3: {
                    System.out.println("********* UPDATE ITEM DESCRIPTION *********");
                    System.out.println("Enter productID you want to update");
                    String pid = sc.nextLine();
                    System.out.println("Enter new description for the product");
                    String newDesc = sc.nextLine();
                    frontController.handleRequest(Requests.Update_Item_Description, new String[]{pid, newDesc});
                    break;
                }
                case 4: {
                    System.out.println("********* UPDATE ITEM PRICE *********");
                    System.out.println("Enter productID you want to update");
                    String pid = sc.nextLine();
                    System.out.println("Enter new price for the product");
                    String newPrice = sc.nextLine();
                    frontController.handleRequest(Requests.Update_Item_Price, new String[]{pid, newPrice});
                    break;
                }
                case 5: {
                    System.out.println("********* UPDATE ITEM QUANTITY *********");
                    System.out.println("Enter productID you want to update");
                    String pid = sc.nextLine();
                    System.out.println("Enter new quantity for the product");
                    String newQty = sc.nextLine();
                    frontController.handleRequest(Requests.Update_Item_Quantity, new String[]{pid, newQty});
                    break;
                }
                case 6: {
                    System.out.println("********* BACK *********");
                    break;
                }
                default: {
                    System.out.println("Select a valid action");
                    break;
                }
            }
        }
    }
}
