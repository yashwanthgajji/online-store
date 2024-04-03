package client.controllers;

import common.models.User;
import common.services.UserService;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public String getUserName(String userID) throws RemoteException {
        return userService.getUserName(UUID.fromString(userID));
    }

    public String registerNewCustomer(String name, String email, String password) throws RemoteException {
        User user = new User(name, email, password);
        userService.registerNewCustomer(user);
        return user.getUserID().toString();
    }

    public String login(String email, String password) throws RemoteException {
        return userService.login(email, password).toString();
    }

    public boolean isUserAdmin(String userID) throws RemoteException {
        return userService.isUserAdmin(UUID.fromString(userID));
    }

    public void addNewAdmin(String name, String email, String password) throws RemoteException {
        User admin = new User(name, email, password);
        userService.addNewAdmin(admin);
    }

    public void viewAllCustomers() throws RemoteException {
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

    public void addNewCustomer(String name, String email, String password) throws RemoteException {
        User customer = new User(name, email, password);
        userService.addNewCustomer(customer);
    }


    public void removeCustomer(String userID) throws RemoteException {
        userService.removeCustomer(UUID.fromString(userID));
    }
}
