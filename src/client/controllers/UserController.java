package client.controllers;

import common.models.User;
import common.services.UserService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public String getUserName(String userID) throws RemoteException {
        return userService.getUserName(userID);
    }

    public String registerNewCustomer(String name, String email, String password) throws RemoteException {
        return userService.registerNewCustomer(name, email, password);
    }

    public String login(String email, String password) throws RemoteException {
        return userService.login(email, password);
    }

    public boolean isUserAdmin(String userID) throws RemoteException {
        return userService.isUserAdmin(userID);
    }

    public void addNewAdmin(String name, String email, String password) throws RemoteException {
        userService.addNewAdmin(name, email, password);
    }

    public void viewAllCustomers() throws RemoteException {
        System.out.println(userService.getAllCustomers());
    }

    public void addNewCustomer(String name, String email, String password) throws RemoteException {
        userService.addNewCustomer(name, email, password);
    }


    public void removeCustomer(String userID) throws RemoteException {
        userService.removeCustomer(userID);
    }
}
