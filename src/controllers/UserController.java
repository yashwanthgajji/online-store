package controllers;

import models.User;
import serviceImpls.UserServiceImpl;
import services.UserService;

import java.util.List;
import java.util.UUID;

public class UserController {

    private final UserService userService;

    public UserController() {
        userService = new UserServiceImpl();
    }

    public String getUserName(String userID) {
        return userService.getUserName(UUID.fromString(userID));
    }

    public String registerNewCustomer(String name, String email, String password) {
        User user = new User(name, email, password);
        userService.registerNewCustomer(user);
        return user.getUserID().toString();
    }

    public String login(String email, String password) {
        return userService.login(email, password).toString();
    }

    public boolean isUserAdmin(String userID) {
        return userService.isUserAdmin(UUID.fromString(userID));
    }

    public void addNewAdmin(String name, String email, String password) {
        User admin = new User(name, email, password);
        userService.addNewAdmin(admin);
    }

    public void viewAllCustomers() {
        List<User> customers = userService.getAllCustomers();
        System.out.println("********* CUSTOMERS *********");
        StringBuilder sb = new StringBuilder();
        sb.append("S.NO")
                .append("\t").append("Customer ID")
                .append("\t").append("Customer Name")
                .append("\t").append("Email")
                .append("\n");
        int i=1;
        for (User customer : customers) {
            sb.append(i)
                    .append("\t").append(customer.getUserID())
                    .append("\t").append(customer.getName())
                    .append("\t").append(customer.getEmail())
                    .append("\n");
            i++;
        }
        System.out.println(sb);
    }

    public void addNewCustomer(String name, String email, String password) {
        User customer = new User(name, email, password);
        userService.addNewCustomer(customer);
    }


    public void removeCustomer(String userID) {
        userService.removeCustomer(UUID.fromString(userID));
    }
}
