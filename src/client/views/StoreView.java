package client.views;

import client.controllers.CartController;
import client.controllers.ProductController;
import client.controllers.UserController;
import common.services.CartService;
import common.services.ProductService;
import common.services.UserService;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class StoreView {

    private static UserController userController;
    private static ProductController productController;
    private static CartController cartController;
    private static String sessionUserID;
    private static Scanner sc;

    public static void main(String[] args) {

        System.out.println("*** Connecting to Server ***");
        UserService stubUser = null;
        ProductService stubProduct = null;
        CartService stubCart = null;
        try {
            stubUser = (UserService) Naming.lookup("store-user");
            stubProduct = (ProductService) Naming.lookup("store-product");
            stubCart = (CartService) Naming.lookup("store-cart");

            System.out.println("*** Connection Successful");

            userController = new UserController(stubUser);
            productController = new ProductController(stubProduct);
            cartController = new CartController(stubCart, stubProduct);

            sc = new Scanner(System.in);

            System.out.println("********* WELCOME *********");
            System.out.println("""
                    1. Register
                    2. Login
                    """);
            int regLog = Integer.parseInt(sc.nextLine());
            if (regLog == 1) {
                registrationPage();
            } else {
                loginPage();
            }
            if (sessionUserID != null) {
                System.out.println("Welcome " + userController.getUserName(sessionUserID));
                startDashboard();
            } else {
                System.out.println("Registration/Login unsuccessful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registrationPage() throws RemoteException {
        System.out.println("********* REGISTRATION *********");
        System.out.println("Enter Name....");
        String name = sc.nextLine();
        System.out.println("Enter Email....");
        String email = sc.nextLine();
        System.out.println("Enter Password....");
        String password = sc.nextLine();
        System.out.println("Registering user....");
        sessionUserID = userController.registerNewCustomer(name, email, password);
        System.out.println("Registration Successful");
    }

    private static void loginPage() throws RemoteException {
        System.out.println("********* LOGIN *********");
        System.out.println("Enter Email....");
        String email = sc.nextLine();
        System.out.println("Enter Password....");
        String password = sc.nextLine();
        System.out.println("Authenticating user....");
        sessionUserID = userController.login(email, password);
        System.out.println("Login Successful....");
    }

    private static void startDashboard() throws Exception {
        System.out.println("********* DASHBOARD *********");
        if (userController.isUserAdmin(sessionUserID)) {
            int action = -1;
            while (action != 4) {
                System.out.println("********* ACTIONS *********");
                System.out.println("""
                    1. View Products
                    2. View Customers
                    3. Add new Admin
                    4. Logout
                    """);
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
        }
        else {
            int action = -1;
            while (action != 3) {
                productController.viewAllProducts();
                System.out.println("********* ACTIONS *********");
                System.out.println("""
                        1. Add a product to cart
                        2. View Cart
                        3. Logout
                        """);
                action = Integer.parseInt(sc.nextLine());
                switch (action) {
                    case 1: {
                        System.out.println("********* ADD PRODUCT TO CART *********");
                        System.out.println("Enter product ID....");
                        String pid = sc.nextLine();
                        System.out.println("Enter quantity....");
                        int qty = Integer.parseInt(sc.nextLine());
                        cartController.addItemToCart(sessionUserID, pid, qty);
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

    private static void customerCartPage() throws Exception {
        int action = -1;
        while (action != 4) {
            cartController.viewAllCartItems(sessionUserID);
            System.out.println("********* ACTIONS *********");
            System.out.println("""
                        1. Update cart item quantity
                        2. Remove a cart item
                        3. Buy Now
                        4. Go back
                        """);
            action = Integer.parseInt(sc.nextLine());
            switch (action) {
                case 1: {
                    System.out.println("********* UPDATE PRODUCT IN CART *********");
                    System.out.println("Enter cart item ID to update....");
                    String cid = sc.nextLine();
                    System.out.println("Enter quantity....");
                    int qty = Integer.parseInt(sc.nextLine());
                    cartController.updateItemQuantityInCart(sessionUserID, cid, qty);
                    break;
                }
                case 2: {
                    System.out.println("********* REMOVE PRODUCT IN CART *********");
                    System.out.println("Enter cart item ID to update....");
                    String cid = sc.nextLine();
                    cartController.removeItemFromCart(sessionUserID, cid);
                    break;
                }
                case 3: {
                    System.out.println("********* ORDER *********");
                    System.out.println("Placing order...");
                    cartController.purchase(sessionUserID);
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
        System.out.println("Enter Name....");
        String name = sc.nextLine();
        System.out.println("Enter Email....");
        String email = sc.nextLine();
        System.out.println("Enter Password....");
        String password = sc.nextLine();
        System.out.println("Registering admin....");
        userController.addNewAdmin(name, email, password);
        System.out.println("Admin Registration Successful");
    }

    private static void adminCustomersPage() throws RemoteException {
        int action = -1;
        while (action != 3) {
            userController.viewAllCustomers();
            System.out.println("********* ACTIONS *********");
            System.out.println("""
                    1. Add a new customer
                    2. Remove a customer
                    3. Go back
                    """);
            action = Integer.parseInt(sc.nextLine());
            switch (action) {
                case 1: {
                    System.out.println("********* ADD NEW CUSTOMER *********");
                    System.out.println("Enter name....");
                    String name = sc.nextLine();
                    System.out.println("Enter email....");
                    String email = sc.nextLine();
                    System.out.println("Enter password....");
                    String pass = sc.nextLine();
                    System.out.println("Adding item to database....");
                    userController.addNewCustomer(name, email, pass);
                    System.out.println("Item added successfully....");
                    break;
                }
                case 2: {
                    System.out.println("********* REMOVE CUSTOMER *********");
                    System.out.println("Enter customer ID");
                    String cid = sc.nextLine();
                    userController.removeCustomer(cid);
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

    private static void adminProductsPage() throws Exception {
        int action = -1;
        while (action != 6) {
            productController.viewAllProducts();
            System.out.println("********* ACTIONS *********");
            System.out.println("""
                    1. Add a new product
                    2. Remove a product
                    3. Change description of a product
                    4. Change price of a product
                    5. Change stock quantity of a product
                    6. Go back
                    """);
            action = Integer.parseInt(sc.nextLine());
            switch (action) {
                case 1: {
                    System.out.println("********* ADD NEW ITEM *********");
                    System.out.println("Enter name....");
                    String name = sc.nextLine();
                    System.out.println("Enter description....");
                    String desc = sc.nextLine();
                    System.out.println("Enter price....");
                    double price = Double.parseDouble(sc.nextLine());
                    System.out.println("Enter quantity....");
                    int qty = Integer.parseInt(sc.nextLine());
                    System.out.println("Adding item to database....");
                    productController.addNewProduct(name, desc, price, qty);
                    System.out.println("Item added successfully....");
                    break;
                }
                case 2: {
                    System.out.println("********* REMOVE ITEM *********");
                    System.out.println("Enter productID");
                    String pid = sc.nextLine();
                    productController.removeProduct(pid);
                    break;
                }
                case 3: {
                    System.out.println("********* UPDATE ITEM DESCRIPTION *********");
                    System.out.println("Enter productID you want to update");
                    String pid = sc.nextLine();
                    System.out.println("Enter new description for the product");
                    String newDesc = sc.nextLine();
                    productController.updateItemDescription(pid, newDesc);
                    break;
                }
                case 4: {
                    System.out.println("********* UPDATE ITEM PRICE *********");
                    System.out.println("Enter productID you want to update");
                    String pid = sc.nextLine();
                    System.out.println("Enter new price for the product");
                    double newPrice = Double.parseDouble(sc.nextLine());
                    productController.updateItemPrice(pid, newPrice);
                    break;
                }
                case 5: {
                    System.out.println("********* UPDATE ITEM QUANTITY *********");
                    System.out.println("Enter productID you want to update");
                    String pid = sc.nextLine();
                    System.out.println("Enter new quantity for the product");
                    int newQty = Integer.parseInt(sc.nextLine());
                    productController.updateItemQuantity(pid, newQty);
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
